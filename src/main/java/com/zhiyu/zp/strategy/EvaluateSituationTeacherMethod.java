package com.zhiyu.zp.strategy;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.ClassTeacherRelEntity;
import com.zhiyu.baseplatform.enumcode.FocusState;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.IClassTeacherRelService;
import com.zhiyu.zp.dto.response.app.info.EvaluateSituationBaseDto;
import com.zhiyu.zp.dto.response.app.info.TeacherEvaluateSituationDto;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.enumcode.EvaluateCompletedStatus;
import com.zhiyu.zp.enumcode.EvaluateMethod;
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
@Component(value="evaluateSituationTeacherMethod")
public class EvaluateSituationTeacherMethod extends EvaluateSituationMethodStrategy{

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;
	
	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;
	
	@Autowired
	private ClassStudentRelationHandler classStudentRelationHandler;
	
	@Autowired
	private IZpEvaluateDetailService zpEvaluateDetailService;
	
	@Autowired
	private IZpEvaluateBagService zpEvaluateBagService;
	
	@Autowired
	private IClassTeacherRelService classTeacherService;
	
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
		Long termId = zpEvaluateBagService.findById(bagId).getTermId();
		List<ClassStudentRelEntity> classStudents = classStudentRelationHandler.findTermClassStudents(termId, schoolId, classId);
		for(ZpEvaluateInfoEntity entity:entityList){
			TeacherEvaluateSituationDto dto = new TeacherEvaluateSituationDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setEvaluateId(entity.getId());
			int evaluateNum = 0;
			for(ClassStudentRelEntity classStudent:classStudents){
				ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(classStudent.getStudentId().longValue(), schoolId, classId, entity.getId());
				if(result!=null){
					List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), EvaluateMethod.TEACHER,firstLeafDegree.getId());
					if(detailList!=null && !detailList.isEmpty()){
						evaluateNum++;
					}
				}
			}
			if(evaluateNum==classStudents.size()){
				dto.setPercent(100);
				dto.setEvaluateCompletedStatus(EvaluateCompletedStatus.COMPLETED);
			}else{
				dto.setPercent(evaluateNum*100/classStudents.size());
				dto.setEvaluateCompletedStatus(EvaluateCompletedStatus.UNCOMPLETED);
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public Long getClassId(Long schoolId, Long operateId, Long bagId){
		Long termId = zpEvaluateBagService.findById(bagId).getTermId();
		List<ClassTeacherRelEntity> rels = classTeacherService.findListByConditionWithNoPage(" and teacherId=? and termId=? and curfocus=?", new Object[]{operateId.intValue(),termId.intValue(),FocusState.FOCUSED.getValue()}, null);
		if(rels==null || rels.isEmpty()){
			try {
				throw new Exception("班级设置有误，找不到该教师所在的班级信息");
			} catch (Exception e) {
				return null;
			}
		}
		return rels.get(0).getClassId().longValue();
	}

}
