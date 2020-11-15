package com.zhiyu.zp.service.business;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.zp.adapter.ZpEvaluateDegreeInfoAdapter;
import com.zhiyu.zp.bean.EvaluateDetailsCountBean;
import com.zhiyu.zp.bean.ZpEvaluateWeightSettingResValueComparatorBean;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeUpdateInfoDto;
import com.zhiyu.zp.dto.response.web.degree.WebDegreeViewDetailResponseDto;
import com.zhiyu.zp.dto.response.web.timesetting.BaseStarInfoDto;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.HasChildren;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateInfoService;
import com.zhiyu.zp.service.IZpEvaluateResultService;
import com.zhiyu.zp.service.IZpEvaluateWeightSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class EvaluateDegreeService {

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;

	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;
	
	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;

	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;

	@Autowired
	private IZpEvaluateWeightSettingService zpEvaluateWeightSettingService;

	@Autowired
	private EvaluateWeightService evaluateWeightService;

	@Autowired
	private EvaluateDetailService evaluateDetailService;

	private ZpEvaluateDegreeInfoAdapter zpEvaluateDegreeInfoAdapter = new ZpEvaluateDegreeInfoAdapter();

	/**
	 * 获取学校某个具体学期中的常规次评的大维度信息列表
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<BaseStarInfoDto> getRegularTimeEvaluateMajorDegrees(Long schoolId, Long termId)
			throws EvaluateBagSettingException {
		Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(schoolId, termId);
		List<ZpEvaluateBagEntity> bagList = zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, searchTermId);
		List<BaseStarInfoDto> degreeEntityList = Lists.newArrayList();
		for(ZpEvaluateBagEntity bag:bagList){
			String degreesStrInThatTerm = bag.getTopDegrees();
			String[] degreeIds = degreesStrInThatTerm.split(",");
			for (String degreeId : degreeIds) {
				ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(Long.valueOf(degreeId));
				BaseStarInfoDto info = new BaseStarInfoDto();
				if (zpEvaluateBusinessService.checkIfTimeEvaluateStart(schoolId,bag.getId())) {
					info.setCanSet(false);
				}else{
					info.setCanSet(true);
				}
				info.setDegreeId(degree.getId());
				info.setDegreeName(degree.getDname());
				info.setBagId(bag.getId());
				info.setBagName(bag.getBagName());
				degreeEntityList.add(info);
			}
		}
		return degreeEntityList;
	}
	/**
	 * 获取学校某个具体学期中的常规次评的大维度信息列表
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<ZpEvaluateDegreeInfoEntity> getRegularTimeEvaluateMajorDegreeList(Long schoolId, Long termId
			, Long bagId)
			throws EvaluateBagSettingException {
		Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(schoolId, termId);
		List<ZpEvaluateBagEntity> bagList = zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, searchTermId);
		List<ZpEvaluateDegreeInfoEntity> degreeEntityList = Lists.newArrayList();
		for(ZpEvaluateBagEntity bag:bagList){
			if(bagId==bag.getId()){
				String degreesStrInThatTerm = bag.getTopDegrees();
				String[] degreeIds = degreesStrInThatTerm.split(",");
				for (String degreeId : degreeIds) {
					ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(Long.valueOf(degreeId));
					degreeEntityList.add(degree);
				}
			}
		}
		return degreeEntityList;
	}
	/**
	 * 获取次评维度信息的树形结构列表
	 * 
	 * @param schoolId
	 * @param classId
	 * @param topDegreeEntityList
	 * @param studentId
	 * @param evaluateId
	 * @param methods
	 * @return
	 */
	public List<BaseEvaluateDegreeDto> getRegularTimeEvaluateDegreeTreeInfos(Long schoolId,
			List<ZpEvaluateDegreeInfoEntity> topDegreeEntityList, Long studentId, List<EvaluateMethod> methods) {
		List<BaseEvaluateDegreeDto> dtos = Lists.newArrayList();
		for (ZpEvaluateDegreeInfoEntity degree : topDegreeEntityList) {
			BaseEvaluateDegreeDto dto = new BaseEvaluateDegreeDto();
			dto.setDegreeId(degree.getId());
			dto.setDegreeName(degree.getDname());
			if (degree.getHasChildren().equals(HasChildren.DONT_HAVE_CHILD)) {// 无孩子的维度
				dto.setMaxNum(degree.getMaxNum());
			} else {
				List<ZpEvaluateDegreeInfoEntity> childDegrees = zpEvaluateDegreeInfoService.findByPid(degree.getId());
				if (!childDegrees.isEmpty()) {
					dto.setItems(getRegularTimeEvaluateDegreeTreeInfos(schoolId, childDegrees, studentId, methods));
				}
			}
			dtos.add(dto);
		}
		return dtos;
	}

	/**
	 * 处理维度树，加上对应维度的评价信息
	 * 
	 * @param schoolId
	 * @param classId
	 * @param dtos
	 * @param studentId
	 * @param evaluateId
	 * @param methods
	 */
	public void processRegularTimeEvaluateDegreeInfosWithDetails(Long schoolId, Long classId,
			List<BaseEvaluateDegreeDto> dtos, Long studentId, Long evaluateId, List<EvaluateMethod> methods) {
		ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(studentId,
				schoolId, classId, evaluateId);
		if (dtos.isEmpty() || result == null) {
			return;
		}
		Map<EvaluateMethod, Integer> weightMap = Maps.newTreeMap();
		weightMap.put(EvaluateMethod.SELF, 1);
		weightMap.put(EvaluateMethod.PARENT, 2);
		weightMap.put(EvaluateMethod.TEACHER, 7);
		for (BaseEvaluateDegreeDto dto : dtos) {
			if (dto.getItems().isEmpty()) {// 无孩子的维度
				EvaluateDetailsCountBean detailCountBean = evaluateDetailService.getTotalDetailValue(result.getId(),
						dto.getDegreeId(), methods);
				dto.setTimeEvaluateResultMaps(detailCountBean.getDetailValueMap());
				dto.setOperaterIds(detailCountBean.getDetailIdMap());
				// 遍历方式结果
				int totalNum = 0;
				for (EvaluateMethod em : methods) {
					totalNum += weightMap.get(em) * detailCountBean.getDetailValueMap().get(em);
				}
//				totalNum = totalNum/10;//比重值
				dto.setCurrentTotalStartNum(totalNum);
				dto.setDegreeEvaluateTotalNum(totalNum/10);//保留大整数用于后续统计计算，可保证小数值累计
				//dto.setDegreeEvaluateTotalNum(detailCountBean.getTotalMethodsValue());
			} else {
				processRegularTimeEvaluateDegreeInfosWithDetails(schoolId, classId, dto.getItems(), studentId,
						evaluateId, methods);
				// 处理次评的总评数(各子维度的最大值的总和)
				Integer countMaxNum = 0;// 用于获取比重计算的被除数
				for (BaseEvaluateDegreeDto item : dto.getItems()) {
					dto.setDegreeEvaluateTotalNum(dto.getDegreeEvaluateTotalNum() + item.getCurrentTotalStartNum());
					countMaxNum += item.getMaxNum();
				}
				dto.setDegreeEvaluateTotalNum(dto.getDegreeEvaluateTotalNum()/10);//转成对应正常星数值
				// 比较权重，获取到权重比之后的数值
				ZpEvaluateInfoEntity evaluateInfo = zpEvaluateInfoService.findById(evaluateId);
				Integer weightValue = (dto.getDegreeEvaluateTotalNum() * 100) / (countMaxNum);// 比重分值计算算法
				dto.setDegreeEvaluateTotalNum(evaluateWeightService.getTimeEvaluateDegreeResValue(
						evaluateInfo.getEvaluateBagId(), dto.getDegreeId(), weightValue));
				// 获取当前维度在权重处理后所能达到的最大值
				List<ZpEvaluateWeightSettingEntity> weightEntityList = zpEvaluateWeightSettingService
						.findRegularTimeEvaluateWeightSetting(evaluateInfo.getEvaluateBagId(), dto.getDegreeId());
				// 按最大值排序
				if (!weightEntityList.isEmpty()) {
					Collections.sort(weightEntityList, new ZpEvaluateWeightSettingResValueComparatorBean());
					dto.setMaxNum(weightEntityList.get(0).getResValue());
				}else{
					dto.setMaxNum(3);//默认最大的星数是3
				}
			}
		}
	}

	/**
	 * 保存维度
	 * 
	 * @param schoolId
	 * @param degreeType
	 * @param degreePicId
	 * @param degreeName
	 * @param degreeDesc
	 * @param trunks
	 */
	@Transactional
	public void saveDegree(Long schoolId, int degreeType, Long degreePicId, String degreeName, String degreeDesc,
			Integer versionId,List<BaseDegreeUpdateInfoDto> trunks) {
		List<ZpEvaluateDegreeInfoEntity> degreeList = Lists.newArrayList();
		// 保存主维度
		ZpEvaluateDegreeInfoEntity majorDegree = zpEvaluateDegreeInfoAdapter.getEntityFromLeafDegreeInfo(schoolId,
				degreeType, degreePicId, degreeName, degreeDesc,versionId);
		try {
			majorDegree.setDataState(DataState.NORMAL);
			majorDegree.setUseable(Useable.UNUSEABLE);
			int has_child = 0;
			if (!trunks.isEmpty()) {
				has_child = 1;
			}
			majorDegree.setPid(0L);
			majorDegree.setHasChildren(HasChildren.getType(has_child));
			zpEvaluateDegreeInfoService.save(majorDegree);
			degreeList.add(majorDegree);
			saveDegreeRecursive(schoolId, degreeType, majorDegree.getId(),versionId, trunks);
		} catch (Exception e) {

		}
	}

	/**
	 * 查询具体某一个维度的明细（树形结果展示）
	 * 
	 * @param schoolId
	 * @param degreeId
	 * @return
	 */
	public WebDegreeViewDetailResponseDto viewDegreeDetail(Long schoolId, Long degreeId) {
		// 获取主维度信息
		ZpEvaluateDegreeInfoEntity degreeEntity = zpEvaluateDegreeInfoService.findById(degreeId);
		WebDegreeViewDetailResponseDto dto = new WebDegreeViewDetailResponseDto();
		BeanUtils.copyProperties(zpEvaluateDegreeInfoAdapter.getBaseValidInfoFromEntity(degreeEntity), dto);
		dto.setDegreePicId(degreeEntity.getDegreePicId());
		dto.setSchoolId(schoolId);
		dto.setItems(addChildDegreeInfoRecursive(degreeId));
		return dto;
	}

	/**
	 * 逐级更新
	 * 
	 * @param degreeId
	 * @param degreeName
	 * @param degreeDesc
	 * @param maxNum
	 * @param status
	 * @param items
	 */
	@Transactional
	public void updateDegreeRecursive(Long schoolId, int degreeType, Long pid,Integer versionId, Long degreeId, String degreeName,
			String degreeDesc, int maxNum, int status, List<BaseDegreeUpdateInfoDto> items) {
		if (status == 1) { // 删除操作
			deleteDegreeLogicallyRecursive(degreeId);
		} else { // 更新
			if (degreeId != null) {// 更新
				ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(degreeId);
				degree.setDname(degreeName);
				degree.setDescription(degreeDesc);
				degree.setMaxNum(maxNum);
				degree.setVersionId(versionId);
				if (items != null && !items.isEmpty()) {
					degree.setHasChildren(HasChildren.HAVE_CHILD);
					zpEvaluateDegreeInfoService.update(degree);
					for (BaseDegreeUpdateInfoDto updateDto : items) {// 这里注意，degreeId相对items来说是pid
						updateDegreeRecursive(schoolId, degreeType, degreeId, versionId,updateDto.getDegreeId(),
								updateDto.getDegreeName(), updateDto.getDegreeDesc(), updateDto.getMaxNum(),
								updateDto.getStatus(), updateDto.getItems());
					}
				}else{
					degree.setHasChildren(HasChildren.DONT_HAVE_CHILD);
					zpEvaluateDegreeInfoService.update(degree);
				}
			} else {// 保存新增的维度
				if (!items.isEmpty()) {
					ZpEvaluateDegreeInfoEntity newEntity = new ZpEvaluateDegreeInfoEntity();
					newEntity.setPid(pid);
					newEntity.setHasChildren(HasChildren.HAVE_CHILD);
					newEntity.setDataState(DataState.NORMAL);
					newEntity.setDegreeType(DegreeType.getType(degreeType));
					newEntity.setDescription(degreeDesc);
					newEntity.setDname(degreeName);
					newEntity.setSchoolId(schoolId);
					newEntity.setUseable(Useable.UNUSEABLE);
					newEntity.setMaxNum(maxNum);
					newEntity.setVersionId(versionId);
					zpEvaluateDegreeInfoService.save(newEntity);
					saveDegreeRecursive(schoolId, degreeType, newEntity.getId(),versionId, items);
				} else {
					ZpEvaluateDegreeInfoEntity newEntity = new ZpEvaluateDegreeInfoEntity();
					newEntity.setPid(pid);
					newEntity.setHasChildren(HasChildren.DONT_HAVE_CHILD);
					newEntity.setDataState(DataState.NORMAL);
					newEntity.setDegreeType(DegreeType.getType(degreeType));
					newEntity.setDescription(degreeDesc);
					newEntity.setDname(degreeName);
					newEntity.setSchoolId(schoolId);
					newEntity.setUseable(Useable.UNUSEABLE);
					newEntity.setMaxNum(maxNum);
					newEntity.setVersionId(versionId);
					zpEvaluateDegreeInfoService.save(newEntity);
				}

			}
		}
	}

	/**
	 * 递归存储维度
	 * 
	 * @param schoolId
	 * @param degreeType
	 * @param pid
	 * @param trunks
	 */
	protected void saveDegreeRecursive(Long schoolId, int degreeType, Long pid,Integer versionId, List<BaseDegreeUpdateInfoDto> trunks) {
		for (BaseDegreeUpdateInfoDto trunk : trunks) {
			ZpEvaluateDegreeInfoEntity trunkEntity = zpEvaluateDegreeInfoAdapter.getEntityFromLeafDegreeInfo(schoolId,
					degreeType, null, trunk.getDegreeName(), trunk.getDegreeDesc(),versionId);
			trunkEntity.setPid(pid);
			trunkEntity.setDataState(DataState.NORMAL);
			trunkEntity.setUseable(Useable.UNUSEABLE);
			int has_child = 0;
			if (!trunk.getItems().isEmpty()) {
				has_child = 1;
			}
			trunkEntity.setHasChildren(HasChildren.getType(has_child));
			trunkEntity.setMaxNum(trunk.getMaxNum());
			zpEvaluateDegreeInfoService.save(trunkEntity);
			if (has_child != 0) {
				saveDegreeRecursive(schoolId, degreeType, trunkEntity.getId(), versionId,trunk.getItems());
			}
		}
	}

	/**
	 * 递归删除（逻辑删除）
	 * 
	 * @param pid
	 */
	protected void deleteDegreeLogicallyRecursive(Long pid) {
		ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(pid);
		if (degree.getHasChildren().equals(HasChildren.HAVE_CHILD)) {
			List<ZpEvaluateDegreeInfoEntity> childDegrees = zpEvaluateDegreeInfoService.findByPid(pid);
			for (ZpEvaluateDegreeInfoEntity child : childDegrees) {
				deleteDegreeLogicallyRecursive(child.getId());
			}
		}
		degree.setDataState(DataState.DELETED);
		zpEvaluateDegreeInfoService.update(degree);
	}

	/**
	 * 查看-遍历获取子维度信息列表
	 * 
	 * @param pid
	 * @return
	 */
	protected List<BaseDegreeUpdateInfoDto> addChildDegreeInfoRecursive(Long pid) {
		List<ZpEvaluateDegreeInfoEntity> childDegrees = zpEvaluateDegreeInfoService.findByPid(pid);
		List<BaseDegreeUpdateInfoDto> items = Lists.newArrayList();
		for (ZpEvaluateDegreeInfoEntity child : childDegrees) {
			BaseDegreeUpdateInfoDto updateDto = new BaseDegreeUpdateInfoDto();
			updateDto.setDegreeDesc(child.getDescription());
			updateDto.setDegreeId(child.getId());
			updateDto.setDegreeName(child.getDname());
			updateDto.setMaxNum(child.getMaxNum());
			updateDto.setVersionId(child.getVersionId());
			if (child.getHasChildren().equals(HasChildren.DONT_HAVE_CHILD)) {

			} else {
				updateDto.getItems().addAll(addChildDegreeInfoRecursive(child.getId()));
			}
			items.add(updateDto);
		}
		return items;
	}

}
