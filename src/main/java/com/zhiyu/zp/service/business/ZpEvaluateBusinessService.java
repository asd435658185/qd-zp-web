package com.zhiyu.zp.service.business;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IClassStudentRelService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.IStudentBaseInfoService;
import com.zhiyu.baseplatform.service.SchoolTermHandler;
import com.zhiyu.zp.adapter.ZpStudentBaseInfoAdapter;
import com.zhiyu.zp.dto.common.DegreeValuePair;
import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;
import com.zhiyu.zp.dto.response.app.comment.EvaluateCommentStudentInfoDto;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.web.level.EventualLevelResultDto;
import com.zhiyu.zp.dto.response.web.level.EventualLevelResultDto.LevelResult;
import com.zhiyu.zp.dto.response.web.level.EventualLevelResultUpdateDto;
import com.zhiyu.zp.dto.response.web.timesetting.BaseUpdateDto;
import com.zhiyu.zp.dto.response.web.timesetting.WeightUpdateDto;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreePicInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;
import com.zhiyu.zp.entity.ZpLevelSettingEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.EvaluateLevelType;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateDegreePicInfoService;
import com.zhiyu.zp.service.IZpEvaluateDetailService;
import com.zhiyu.zp.service.IZpEvaluateInfoService;
import com.zhiyu.zp.service.IZpEvaluateResultService;
import com.zhiyu.zp.service.IZpEvaluateWeightSettingService;
import com.zhiyu.zp.service.IZpLevelSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateBusinessService {

	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;

	@Autowired
	private IZpEvaluateDetailService zpEvaluateDetailService;

	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;

	@Autowired
	private IZpEvaluateDegreePicInfoService zpEvaluateDegreePicInfoService;

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;
	
	@Autowired
	private EvaluateWeightService evaluateWeightService;

	@Autowired
	private IClassStudentRelService classStudentRelService;

	@Autowired
	private IStudentBaseInfoService studentBaseInfoService;

	@Autowired
	private EvaluateResultService evaluateResultService;

	@Autowired
	private EvaluateDetailService evaluateDetailService;

	@Autowired
	private EvaluateDegreeService evaluateDegreeService;
	
	@Autowired
	private IZpEvaluateWeightSettingService zpEvaluateWeightSettingService;
	
	@Autowired
	private IZpLevelSettingService zpLevelSettingService;
	
	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;
	
	@Autowired
	private SchoolTermHandler termHandler;
	/**
	 * 保存次评的评估明细
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 * @param items
	 * @param method
	 */
	@Transactional
	public void saveTimeEvaluateDetail(Long studentId, Long schoolId, Long classId, Long evaluateId,
			List<DegreeValuePair> items, EvaluateMethod method, Long operaterId) {
		ZpEvaluateResultEntity entity = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(studentId,
				schoolId, classId, evaluateId);
		// 生成结果
		if (entity == null) {
			entity = evaluateResultService.preGenerateEvaluateTimeResult(studentId, schoolId, classId, evaluateId);
			zpEvaluateResultService.save(entity);
		}
		// 生成明细
		evaluateDetailService.saveDetailsByBatch(studentId, entity.getId(), classId, evaluateId, items, method,
				operaterId);
		// 汇总多个方式的评估
		evaluateResultService.saveEvaluateGatherResult(entity.getId(), evaluateId, schoolId, classId, studentId);
	}

	/**
	 * 获取班级完成/未完成评价的学生列表
	 * 
	 * @param schoolId
	 * @param termId
	 *            若该参数为空，则表示学期id为当前学校的当前学期id
	 * @param classId
	 * @param evaluateStatus
	 * @return
	 */
	public List<ZpStudentBaseInfoDto> getClassStudentEvaluateList(Long schoolId, Long termId, Long classId,
			Long evaluateId, EvaluateStatus evaluateStatus) {
		List<ZpStudentBaseInfoDto> dtos = Lists.newArrayList();
		Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(schoolId, termId);
		List<ClassStudentRelEntity> classStudents = classStudentRelService.findByCondition(
				" and classId=? and termId=? ", new Object[] { classId.intValue(), searchTermId.intValue() }, null);
		ZpStudentBaseInfoAdapter zpStudentBaseInfoAdapter = new ZpStudentBaseInfoAdapter();
		for (ClassStudentRelEntity classStudentRel : classStudents) {
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(classStudentRel.getStudentId());
			boolean isEvaluated = false;// 是否已评价的标识
			ZpEvaluateResultEntity result = zpEvaluateResultService
					.findStudentRegularNormalTimeEvaluate(stu.getId().longValue(), schoolId, classId, evaluateId);
			if (result != null) {
				List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService
						.findRegularResultDetailList(result.getId(), EvaluateMethod.TEACHER, null);
				if (detailList != null && !detailList.isEmpty()) {
					isEvaluated = true;
				}
			}
			// 筛选出要返回的数据
			if ((evaluateStatus.equals(EvaluateStatus.NOT_EVALUATE) && !isEvaluated)
					|| (evaluateStatus.equals(EvaluateStatus.EVALUATED) && isEvaluated)) {
				dtos.add(zpStudentBaseInfoAdapter.getDtoFromEntity(stu));
			}

		}
		return dtos;
	}

	/**
	 * 获取教师评语中的学生列表信息(APP)
	 * 
	 * @param schoolId
	 * @param termId
	 * @param classId
	 * @param evaluateId
	 * @return
	 */
	public List<EvaluateCommentStudentInfoDto> getClassStudentEvaluateCommentList(Long schoolId, Long termId,
			Long classId, Long bagId) {
		List<EvaluateCommentStudentInfoDto> dtos = Lists.newArrayList();
		Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(schoolId, termId);
		List<ClassStudentRelEntity> classStudents = classStudentRelService.findByCondition(
				" and classId=? and termId=? ", new Object[] { classId.intValue(), searchTermId.intValue() }, null);
		ZpStudentBaseInfoAdapter zpStudentBaseInfoAdapter = new ZpStudentBaseInfoAdapter();
		for (ClassStudentRelEntity classStudentRel : classStudents) {
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(classStudentRel.getStudentId());
			ZpStudentBaseInfoDto stuDto = zpStudentBaseInfoAdapter
					.getDtoFromEntity(stu);
			EvaluateCommentStudentInfoDto dto = new EvaluateCommentStudentInfoDto();
			BeanUtils.copyProperties(stuDto, dto);
			ZpEvaluateResultEntity result = zpEvaluateResultService
					.findStudentEvaluateBagResult(stu.getId().longValue(), schoolId, classId, bagId);
			if (result != null) {
				dto.setEvaluateStatus(EvaluateStatus.EVALUATED);
			}
			dtos.add(dto);
		}
		return dtos;
	}

	/**
	 * 获取教师评语中的学生列表信息(WEB)
	 * 
	 * @param schoolId
	 * @param termId
	 * @param classId
	 * @param evaluateId
	 * @return
	 * @throws EvaluateBagSettingException 
	 */
	public List<EvaluateCommentStudentInfoDto> getClassStudentEvaluateWebCommentList(Long schoolId, Long termId,
			Long classId, Long bagId) throws EvaluateBagSettingException {
		List<EvaluateCommentStudentInfoDto> dtos = Lists.newArrayList();
		Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(schoolId, termId);
		List<ClassStudentRelEntity> classStudents = classStudentRelService.findByCondition(
				" and classId=? and termId=? ", new Object[] { classId.intValue(), searchTermId.intValue() }, null);
		ZpStudentBaseInfoAdapter zpStudentBaseInfoAdapter = new ZpStudentBaseInfoAdapter();
		//获取当前学期下报告册下的次评信息列表
		List<ZpEvaluateInfoEntity> evaluates = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		for (ClassStudentRelEntity classStudentRel : classStudents) {
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(classStudentRel.getStudentId());
			ZpStudentBaseInfoDto stuDto = zpStudentBaseInfoAdapter
					.getDtoFromEntity(stu);
			EvaluateCommentStudentInfoDto dto = new EvaluateCommentStudentInfoDto();
			BeanUtils.copyProperties(stuDto, dto);
			//判断对应的次评是否已经有结果
			dto.setEvaluateStatus(EvaluateStatus.NOT_EVALUATE);
			boolean isAllEvaluated = true;
			for(ZpEvaluateInfoEntity evaluate:evaluates){
				if(!evaluateResultService.checkIfStudentEvaluateStart(schoolId, dto.getStudentId(),classId,evaluate.getId(),EvaluateType.TIME_EVALUATE,false)){
					isAllEvaluated = false;
					break;
				}
			}
			dto.setEvaluateStatus(isAllEvaluated?EvaluateStatus.EVALUATED:EvaluateStatus.NOT_EVALUATE);
			//查询学生评语
			ZpEvaluateResultEntity result = zpEvaluateResultService
					.findStudentEvaluateBagResult(stu.getId().longValue(), schoolId, classId, bagId);
			if (result != null) {
				dto.setPrize(result.getPrize());
				dto.setFeedback(result.getFeedback());
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	/**
	 * 获取学生常规的各个次评的维度展示内容信息的全部信息
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<EvaluateDegreeDto> getRegularTimeEvaluateContent(Long schoolId, Long termId, Long studentId,
			List<EvaluateMethod> methods) throws EvaluateBagSettingException {
		List<EvaluateDegreeDto> items = Lists.newArrayList();
		Integer classId=0;
		ClassStudentRelEntity cs=new ClassStudentRelEntity();
		cs.setStudentId(studentId.intValue());
		if(termId==null){
			TermBaseInfoEntity term = termHandler.getCurrentTermInfoBySchoolId(schoolId.intValue());
			termId = term.getId().longValue();
		}
		cs.setTermId(termId.intValue());
		List<ClassStudentRelEntity> csList=classStudentRelService.findByEntity(cs);
		if(csList!=null&&!csList.isEmpty()){
			classId=csList.get(0).getClassId();
		}
		//获取该班级下的总评id
				ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
				GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
				List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
				Long bagId=0L;
				for(ZpEvaluateBagEntity bag:bagList){
					String gradeStrInThatTerm = bag.getGradeIds();
					String[] gradeIds = gradeStrInThatTerm.split(",");
					for (String gradeId : gradeIds) {
						if(grade.getId().intValue()==Integer.valueOf(gradeId)){
							bagId=bag.getId();
							break;
						}
					}
				}
		// 获取对应学期内评价包涉及到的主常规维度
		List<ZpEvaluateDegreeInfoEntity> degrees = evaluateDegreeService.getRegularTimeEvaluateMajorDegreeList(schoolId,termId,bagId);
		// 处理主维度评价列表树结果
		List<BaseEvaluateDegreeDto> dtos = evaluateDegreeService.getRegularTimeEvaluateDegreeTreeInfos(schoolId,
				degrees, studentId, methods);
		// 给主维度加上图片信息
		for (BaseEvaluateDegreeDto degreeDto : dtos) {
			EvaluateDegreeDto item = new EvaluateDegreeDto();
			BeanUtils.copyProperties(degreeDto, item);
			ZpEvaluateDegreeInfoEntity degreeInfo = zpEvaluateDegreeInfoService.findById(degreeDto.getDegreeId());
			ZpEvaluateDegreePicInfoEntity picDegreeInfo = zpEvaluateDegreePicInfoService
					.findById(degreeInfo.getDegreePicId().longValue());
			if (picDegreeInfo != null) {
				item.setDegreeColor(picDegreeInfo.getPicColor());
				item.setDegreePicUrl(picDegreeInfo.getPicUrl());
				item.setDegreePicId(degreeInfo.getDegreePicId());
			}
			items.add(item);
		}
		return items;
	}

	/**
	 * 获取常规的具体某一次次评的维度评价结果的内容信息
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<EvaluateDegreeDto> getRegularTimeEvaluateResult(Long schoolId, Long termId, Long classId,
			Long studentId, Long evaluateId, List<EvaluateMethod> methods) throws EvaluateBagSettingException {
		List<EvaluateDegreeDto> items = Lists.newArrayList();
		// 获取对应学期内评价包涉及到的主常规维度
		//获取该班级下的总评id
		ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
		GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
		List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
		Long bagId=0L;
		for(ZpEvaluateBagEntity bag:bagList){
			String gradeStrInThatTerm = bag.getGradeIds();
			String[] gradeIds = gradeStrInThatTerm.split(",");
			for (String gradeId : gradeIds) {
				if(grade.getId().intValue()==Integer.valueOf(gradeId)){
					bagId=bag.getId();
					break;
				}
			}
		}
		List<ZpEvaluateDegreeInfoEntity> degrees = evaluateDegreeService.getRegularTimeEvaluateMajorDegreeList(schoolId,
				termId,bagId);
		// 处理主维度评价列表树结果
		List<BaseEvaluateDegreeDto> dtos = evaluateDegreeService.getRegularTimeEvaluateDegreeTreeInfos(schoolId,
				degrees, studentId, methods);
		// 处理，加上评价结果信息
		evaluateDegreeService.processRegularTimeEvaluateDegreeInfosWithDetails(schoolId, classId, dtos, studentId,
				evaluateId, methods);
		// 给主维度加上图片信息
		for (BaseEvaluateDegreeDto degreeDto : dtos) {
			EvaluateDegreeDto item = new EvaluateDegreeDto();
			BeanUtils.copyProperties(degreeDto, item);
			ZpEvaluateDegreeInfoEntity degreeInfo = zpEvaluateDegreeInfoService.findById(degreeDto.getDegreeId());
			ZpEvaluateDegreePicInfoEntity picDegreeInfo = zpEvaluateDegreePicInfoService
					.findById(degreeInfo.getDegreePicId());
			if (picDegreeInfo != null) {
				item.setDegreeColor(picDegreeInfo.getPicColor());
				item.setDegreePicUrl(picDegreeInfo.getPicUrl());
				item.setDegreePicId(degreeInfo.getDegreePicId());
			}
			items.add(item);
		}
		return items;
	}

	/**
	 * 累计二级维度值
	 * 
	 * @param dtos
	 * @param secondLevelValueMap
	 */
	protected void accumulateSecondDegreeValue(List<BaseEvaluateDegreeDto> dtos,
			Map<Long, Integer> secondLevelValueMap) {
		for (BaseEvaluateDegreeDto dto : dtos) {// 一级列表
			List<BaseEvaluateDegreeDto> secondList = dto.getItems();
			for (BaseEvaluateDegreeDto second : secondList) {
				if (!secondLevelValueMap.containsKey(second.getDegreeId())) {
					secondLevelValueMap.put(second.getDegreeId(), second.getDegreeEvaluateTotalNum());
				} else {
					secondLevelValueMap.put(second.getDegreeId(),
							secondLevelValueMap.get(second.getDegreeId()) + second.getDegreeEvaluateTotalNum());
				}
			}
		}
	}

	/**
	 * 累计二级维度值
	 * @param schoolId
	 * @param termId
	 * @param classId
	 * @param studentId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	protected List<BaseEvaluateDegreeDto> accumulateEvaluateResult(Long schoolId, Long termId, Long classId,
			Long studentId, List<EvaluateMethod> methods) throws EvaluateBagSettingException {
				//获取该班级下的总评id
				ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
				GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
				List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
				Long bagId=0L;
				for(ZpEvaluateBagEntity bag:bagList){
					String gradeStrInThatTerm = bag.getGradeIds();
					String[] gradeIds = gradeStrInThatTerm.split(",");
					for (String gradeId : gradeIds) {
						if(grade.getId().intValue()==Integer.valueOf(gradeId)){
							bagId=bag.getId();
							break;
						}
					}
				}
		// 获取对应学期内评价包涉及到的主常规维度
		List<ZpEvaluateDegreeInfoEntity> degrees = evaluateDegreeService.getRegularTimeEvaluateMajorDegreeList(schoolId,
				termId,bagId);
		// 获取次评列表
		List<ZpEvaluateInfoEntity> evaluateList = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		Map<Long, List<BaseEvaluateDegreeDto>> resultMap = Maps.newConcurrentMap();// 用于暂存第一条次评的结果列表
		Map<Long, Integer> secondLevelValueMap = Maps.newConcurrentMap();// 二级维度map
		Long key = 1L;
		for (ZpEvaluateInfoEntity evaluate : evaluateList) {
			// 处理主维度评价列表树结果
			List<BaseEvaluateDegreeDto> dtos = evaluateDegreeService.getRegularTimeEvaluateDegreeTreeInfos(schoolId,
					degrees, studentId, methods);
			// 处理，加上评价结果信息
			evaluateDegreeService.processRegularTimeEvaluateDegreeInfosWithDetails(schoolId, classId, dtos, studentId,
					evaluate.getId(), methods);
			if (!resultMap.containsKey(key)) {
				resultMap.put(key, dtos);
				// 累计,二级的统计数据
				accumulateSecondDegreeValue(dtos, secondLevelValueMap);
			} else {
				// 累计,二级的统计数据
				accumulateSecondDegreeValue(dtos, secondLevelValueMap);
			}
		}
		for (BaseEvaluateDegreeDto resultDto : resultMap.get(1L)) {
			List<BaseEvaluateDegreeDto> secondList = resultDto.getItems();
			for (BaseEvaluateDegreeDto second : secondList) {
				second.setDegreeEvaluateTotalNum(secondLevelValueMap.get(second.getDegreeId()));
			}
		}
		return resultMap.get(1L);
	}

	/**
	 * 获取常规的评价终极报表信息
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<EvaluateDegreeDto> getRegularEventualResult(Long schoolId, Long termId, Long classId, Long studentId,
			List<EvaluateMethod> methods) throws EvaluateBagSettingException {
		List<EvaluateDegreeDto> items = Lists.newArrayList();
		// 获取次评列表
		//获取该班级下的总评id
				ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
				GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
				List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
				Long bagId=0L;
				for(ZpEvaluateBagEntity bag:bagList){
					String gradeStrInThatTerm = bag.getGradeIds();
					String[] gradeIds = gradeStrInThatTerm.split(",");
					for (String gradeId : gradeIds) {
						if(grade.getId().intValue()==Integer.valueOf(gradeId)){
							bagId=bag.getId();
							break;
						}
					}
				}
		List<ZpEvaluateInfoEntity> evaluateList = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		List<BaseEvaluateDegreeDto> dtos = accumulateEvaluateResult(schoolId, termId, classId, studentId, methods);
		//处理第二级维度的相对权重值
		for(BaseEvaluateDegreeDto dto:dtos){
			for(BaseEvaluateDegreeDto second:dto.getItems()){
				//比重值=自身获得值/（当种评价最高值*次评数）
				Integer resValue = evaluateWeightService.getEventualEvaluateDegreeResValue(bagId, (second.getDegreeEvaluateTotalNum()*100)/(evaluateList.size()*second.getMaxNum()));
				second.setDegreeEvaluateTotalNum(resValue);
			}
		}
		// 给主维度加上图片信息
		for (BaseEvaluateDegreeDto degreeDto :dtos) {
			EvaluateDegreeDto item = new EvaluateDegreeDto();
			BeanUtils.copyProperties(degreeDto, item);
			ZpEvaluateDegreeInfoEntity degreeInfo = zpEvaluateDegreeInfoService.findById(degreeDto.getDegreeId());
			ZpEvaluateDegreePicInfoEntity picDegreeInfo = zpEvaluateDegreePicInfoService
					.findById(degreeInfo.getDegreePicId());
			if (picDegreeInfo != null) {
				item.setDegreeColor(picDegreeInfo.getPicColor());
				item.setDegreePicUrl(picDegreeInfo.getPicUrl());
				item.setDegreePicId(degreeInfo.getDegreePicId());
			}
			items.add(item);
		}
		return items;
	}

	/**
	 * 获取常规的评价终极报表信息(带有过程汇总数据，也就是次评统计数据)
	 * 
	 * @param schoolId
	 * @param termId
	 * @param methods
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<EvaluateDegreeDto> getRegularEventualResultWithTimeResult(Long schoolId, Long termId, Long classId, Long studentId,
			List<EvaluateMethod> methods) throws EvaluateBagSettingException {
		//获取最终结果
		List<EvaluateDegreeDto> items = getRegularEventualResult(schoolId, termId, classId, studentId, methods);
		
		//获取该班级下的总评id
		ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
		GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
		List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
		Long bagId=0L;
		for(ZpEvaluateBagEntity bag:bagList){
			String gradeStrInThatTerm = bag.getGradeIds();
			String[] gradeIds = gradeStrInThatTerm.split(",");
			for (String gradeId : gradeIds) {
				if(grade.getId().intValue()==Integer.valueOf(gradeId)){
					bagId=bag.getId();
					break;
				}
			}
		}
		List<ZpEvaluateInfoEntity> evaluateList = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		//将每次次评对应的二级维度统计信息汇总到总信息中
		for(ZpEvaluateInfoEntity evaluate:evaluateList){
			List<EvaluateDegreeDto> timeEvaluateDegreeInfoList = getRegularTimeEvaluateResult(schoolId, termId, classId, studentId, evaluate.getId(), methods);
			Map<Long, Integer> secondDegreeMap = groupBySecondDegreeId(timeEvaluateDegreeInfoList);
			for(EvaluateDegreeDto item:items){
				for(BaseEvaluateDegreeDto base:item.getItems()){
					if(secondDegreeMap.containsKey(base.getDegreeId())){
						base.getEveryTimeCountList().add(secondDegreeMap.get(base.getDegreeId()));
					}
				}
			}
		}
		return items;
	}

	/**
	 * 将列表转成成以degreeId为键，对应的degree总分数为值
	 * @param items
	 * @return
	 */
	private Map<Long, Integer> groupBySecondDegreeId(List<EvaluateDegreeDto> items){
		Map<Long, Integer> map = Maps.newConcurrentMap();
		for(EvaluateDegreeDto item:items){
			for(BaseEvaluateDegreeDto base:item.getItems()){
				if(!map.containsKey(base.getDegreeId())){
					map.put(base.getDegreeId(), base.getDegreeEvaluateTotalNum());
				}
			}
		}
		return map;
	}
	
	/**
	 * 检查学校下当前学期是否已经开始了次评结果
	 * @param schoolId
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public boolean checkIfTimeEvaluateStart(Long schoolId,Long bagId) throws EvaluateBagSettingException{
		boolean isExist = false;
		List<ZpEvaluateInfoEntity> evaluates = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		for(ZpEvaluateInfoEntity evaluate:evaluates){
			if(evaluateResultService.checkIfEvaluateStart(schoolId, evaluate.getId(),EvaluateType.TIME_EVALUATE,false)){
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	
	/**
	 * 检查学校下当前学期是否已经开始了总评结果
	 * @param schoolId
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public boolean checkIfGatherEvaluateStart(Long schoolId,Long termId,Long bagId) throws EvaluateBagSettingException{
		boolean isExist = false;
		List<ZpEvaluateInfoEntity> evaluates = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		for(ZpEvaluateInfoEntity evaluate:evaluates){
			if(evaluateResultService.checkIfEvaluateStart(schoolId, evaluate.getId(),EvaluateType.TOTAL_EVALUATED,false)){
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	
	/**
	 * 更新权重的维度设置
	 * @param views
	 * @param degreeType
	 * @param evaluateType
	 */
	public void updateWeightDegreeSetting(List<WeightUpdateDto> views,DegreeType degreeType,EvaluateType evaluateType){
		for(WeightUpdateDto view:views){
			for(BaseUpdateDto weigth:view.getItems()){
				ZpEvaluateWeightSettingEntity w = null;
				if(weigth.getId()==null){//新增
					w = new ZpEvaluateWeightSettingEntity();
					BeanUtils.copyProperties(weigth, w);
					w.setDataState(DataState.NORMAL);
					w.setDegreeType(degreeType);
					w.setEvaluateType(evaluateType);
					zpEvaluateWeightSettingService.save(w);
				}else{
					w = zpEvaluateWeightSettingService.findById(weigth.getId());
					if(weigth.getStatus()==0){//更新
						w.setMaxValue(weigth.getMaxValue());
						w.setMinValue(weigth.getMinValue());
						w.setResValue(weigth.getResValue());
					}else{//删除
						w.setDataState(DataState.DELETED);
					}
					zpEvaluateWeightSettingService.update(w);
				}
			}
		}
	}
	
	/**
	 * 更新等级设置
	 * @param views
	 * @param degreeType
	 * @param evaluateType
	 */
	public void updateEventualLevelSetting(EventualLevelResultUpdateDto updateDto){
		for(EventualLevelResultDto result:updateDto.getRows()){
			for(LevelResult level:result.getItems()){
				ZpLevelSettingEntity w = null;
				if(level.getId()==null){//新增
					w = new ZpLevelSettingEntity();
					w.setEvaluateLevelType(EvaluateLevelType.getType(level.getEvaluateLevelId().intValue()));
					w.setLevelId(result.getLevelId().longValue());
					w.setSchoolId(updateDto.getSchoolId());
					w.setMaxValue(level.getMaxValue());
					w.setMinValue(level.getMinValue());
					zpLevelSettingService.save(w);
				}else{
					w = zpLevelSettingService.findById(level.getId().longValue());
					if(level.getStatus()==0){//更新
						w.setMaxValue(level.getMaxValue());
						w.setMinValue(level.getMinValue());
						w.setEvaluateLevelType(EvaluateLevelType.getType(level.getEvaluateLevelId().intValue()));
						zpLevelSettingService.update(w);
					}else{//删除
						zpLevelSettingService.delete(level.getId());
					}
					
				}
			}
		}
	}
	
}
