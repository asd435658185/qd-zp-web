package com.zhiyu.zp.strategy;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.zp.dto.response.app.info.EvaluateSituationBaseDto;
import com.zhiyu.zp.dto.response.app.info.StudentSelfEvaluateSituationDto;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;
import com.zhiyu.zp.service.IZpEvaluateBagService;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateDetailService;
import com.zhiyu.zp.service.IZpEvaluateInfoService;
import com.zhiyu.zp.service.IZpEvaluateResultService;

/**
 * 
 * @author wdj
 *
 */
@Component(value="evaluateSituationParentMethod")
public class EvaluateSituationParentMethod extends EvaluateSituationMethodStrategy{

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;
	
	@Autowired
	private IZpEvaluateDetailService zpEvaluateDetailService;
	
	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;
	
	@Autowired
	private IZpEvaluateBagService zpEvaluateBagService;
	
	@Autowired
	private ClassStudentRelationHandler classStudentRelationHandler;
	
	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;
	
	@Override
	public List<? super EvaluateSituationBaseDto> getEvaluateSituations(Long schoolId, Long classId, Long operateId,Long bagId) {
		List<? super EvaluateSituationBaseDto> dtos = Lists.newArrayList();
		if(classId==null){
			classId = getClassId(schoolId, operateId, bagId);
		}
		if(classId==null){
			return dtos;
		}
		ZpEvaluateDegreeInfoEntity firstLeafDegree = zpEvaluateDegreeInfoService.findFirstLeafDegree(schoolId);
		List<ZpEvaluateInfoEntity> entityList = zpEvaluateInfoService.findRegularNormalUsableByBagId(bagId);
		for(ZpEvaluateInfoEntity entity:entityList){
			StudentSelfEvaluateSituationDto dto = new StudentSelfEvaluateSituationDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setEvaluateId(entity.getId());
			ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(operateId, schoolId, classId, entity.getId());
			if(result!=null){
				List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), EvaluateMethod.PARENT,firstLeafDegree.getId());
				if(detailList!=null && !detailList.isEmpty()){
					dto.setEvaluateStatus(EvaluateStatus.EVALUATED);
				}
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public Long getClassId(Long schoolId, Long operateId, Long bagId) {
		Long termId = zpEvaluateBagService.findById(bagId).getTermId();
		List<ClassStudentRelEntity> rels = classStudentRelationHandler.findTermStudentClass(termId, schoolId, operateId);
		if(rels==null || rels.isEmpty()){
			try {
				throw new Exception("班级设置有误，找不到该学生所在的班级信息");
			} catch (Exception e) {
				return null;
			}
		}
		return rels.get(0).getClassId().longValue();
	}

}
