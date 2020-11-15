package com.zhiyu.zp.service.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.dto.response.app.report.StudentTimeEvaluateStatus;
import com.zhiyu.zp.dto.response.web.evaluate.StudentResultStatusDto;
import com.zhiyu.zp.dto.response.web.evaluate.StudentResultStatusDto.ClassStudentDto;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.enumcode.ResultType;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateDetailService;
import com.zhiyu.zp.service.IZpEvaluateResultService;

/**
 * 评估结果服务类
 * 
 * @author wdj
 *
 */
@Service
public class EvaluateResultService {

	@Autowired
	private EvaluateDetailService evaluateDetailService;

	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;
	
	@Autowired
	private IZpEvaluateDetailService zpEvaluateDetailService;

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;
	
	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;

	@Autowired
	private EvaluateLevelService evaluateLevelService;
	
	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;

	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;
	/**
	 * 预保存学生次评结果信息
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 */
	protected ZpEvaluateResultEntity preGenerateEvaluateTimeResult(Long studentId, Long schoolId, Long classId,
			Long evaluateId) {
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TIME_EVALUATE);
		entity.setBagEvaluateFlag(false);
		return entity;
	}

	/**
	 * 预保存学生次评的总评结果信息
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 */
	protected ZpEvaluateResultEntity preGenerateEvaluateGatherResult(Long studentId, Long schoolId, Long classId,
			Long evaluateId) {
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TOTAL_EVALUATED);
		entity.setBagEvaluateFlag(false);
		return entity;
	}

	/**
	 * 预保存学生终评的结果信息
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param bagId
	 *            这时候这个是bagId
	 */
	protected ZpEvaluateResultEntity preGenerateEvaluateEventualResult(Long studentId, Long schoolId, Long classId,
			Long bagId) {
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(bagId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setBagEvaluateFlag(true);// 注意。这里是true
		return entity;
	}

	/**
	 * 汇总次评的多种方式评价信息成一条次评的总评记录
	 * 
	 * @param evaluateResultId
	 * @param evaluateId
	 * @param schoolId
	 * @param classId
	 * @param studentId
	 */
	public void saveEvaluateGatherResult(Long evaluateResultId, Long evaluateId, Long schoolId, Long classId,
			Long studentId) {
		// 先判断，明细表中一个学生的次评记录是否
		boolean expectGather = evaluateDetailService.isTimeEvaluateDetailEnd(evaluateResultId);

		if (expectGather) {// 汇总一个总评记录
			// 保存整体结果记录
			ZpEvaluateResultEntity result = preGenerateEvaluateGatherResult(studentId, schoolId, classId, evaluateId);
			zpEvaluateResultService.save(result);
		}
	}

	/**
	 * 保存学生终评记录信息
	 * @param bagId
	 * @param schoolId
	 * @param classId
	 * @param studentId
	 * @param feedback
	 * @param prize
	 * @throws EvaluateBagSettingException 
	 */
	public void saveEvaluateEventualResult(Long schoolId, Long classId, Long studentId,String feedback,String prize) throws EvaluateBagSettingException {
		//获取该班级下的总评id
		ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
		GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
		List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, null);
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
		ZpEvaluateResultEntity isHave=new ZpEvaluateResultEntity();
		isHave.setDataState(DataState.NORMAL);
		isHave.setClassId(classId);
		isHave.setSchoolId(schoolId);
		isHave.setStudentId(studentId);
		isHave.setEvaluateId(bagId);
		isHave.setResultType(ResultType.EVALUATE);
		isHave.setBagEvaluateFlag(true);
		List<ZpEvaluateResultEntity> eList=zpEvaluateResultService.findByEntity(isHave);
		if(eList!=null&&!eList.isEmpty()){
			ZpEvaluateResultEntity entity=eList.get(0);
			//更新整体终评信息结果记录
			entity.setFeedback(feedback);
			entity.setPrize(prize);
			zpEvaluateResultService.update(entity);
		}else{
			// 保存整体终评信息结果记录
			ZpEvaluateResultEntity result = preGenerateEvaluateEventualResult(studentId, schoolId, classId, bagId);
			result.setFeedback(feedback);
			result.setPrize(prize);
			zpEvaluateResultService.save(result);
		}
		
	}

	/**
	 * 查询终评结果记录
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param termId
	 * @param classId
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public ZpEvaluateResultEntity findEventualResult(Long studentId, Long schoolId, Long termId, Long classId)
			throws EvaluateBagSettingException {
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
		ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentEvaluateBagResult(studentId, schoolId,
				classId, bagId);
		return result;
	}
	
	/**
	 * 查询终评结果记录
	 * 
	 * @param studentId
	 * @param schoolId
	 * @param termId
	 * @param classId
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<ZpEvaluateResultEntity> findEventualResultList(Long schoolId, Long termId, Long classId)
			throws EvaluateBagSettingException {
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
		return zpEvaluateResultService.findStudentEvaluateBagResultList( schoolId,
				classId, bagId);
	}
	
	/**
	 * 检查是否对应的次评/总评已经开始产生结果(针对全校的)
	 * @param schoolId
	 * @param evaluateId
	 * @return
	 */
	public boolean checkIfEvaluateStart(Long schoolId,Long evaluateId,EvaluateType evaluateType,boolean bagFlag){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setSchoolId(schoolId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(evaluateType);
		entity.setBagEvaluateFlag(bagFlag);
		entity = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(null, schoolId, null, evaluateId);
		if(entity==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检查学生对应的次评总评是否已经开始产生结果(针对学生的)
	 * @param schoolId
	 * @param evaluateId
	 * @return
	 */
	public boolean checkIfStudentEvaluateStart(Long schoolId,Long studentId,Long classId,Long evaluateId,EvaluateType evaluateType,boolean bagFlag){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setSchoolId(schoolId);
		entity.setClassId(classId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setBagEvaluateFlag(bagFlag);
		entity = zpEvaluateResultService.findStudentRegularNormalGatherEvaluate(studentId, schoolId, classId, evaluateId);
		if(entity==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检查学生对应的次评总评已经开始产生结果的列表(针对学生的)
	 * @param schoolId
	 * @param evaluateId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentEvaluateGathered(Long schoolId,Long studentId,Long classId,Long evaluateId,EvaluateType evaluateType,boolean bagFlag){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setSchoolId(schoolId);
		entity.setClassId(classId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setBagEvaluateFlag(bagFlag);
		return zpEvaluateResultService.findStudentRegularNormalGatherEvaluateList(studentId, schoolId, classId, evaluateId);
	}
	
	/**
	 * 获取学生次评对应的方式的评价结果
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 * @param method
	 * @return
	 */
	public EvaluateStatus findStudentTimeEvaluateStatusResult(Long studentId, Long schoolId, Long classId, Long evaluateId,EvaluateMethod method){
		ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(studentId, schoolId, classId, evaluateId);
		if(result!=null){
			List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), method,null);
			if(detailList!=null && !detailList.isEmpty()){
				return EvaluateStatus.EVALUATED;
			}
		}
		return EvaluateStatus.NOT_EVALUATE;
	}
	/**
	 * 获取学生次评评价状态统计
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 * @param method
	 * @return
	 */
	public StudentResultStatusDto findStudentResultStatus(List<StudentBaseInfoEntity> csrList,Long schoolId, Long classId, Long evaluateId){
			StudentResultStatusDto resultDto=new StudentResultStatusDto();
			Integer classSelfSum=0;
			Integer classParentSum=0;
			Integer classTeacherSum=0;
			List<ClassStudentDto> classStudentList=Lists.newArrayList();
				for(StudentBaseInfoEntity s:csrList){
					ClassStudentDto classStudent=new ClassStudentDto();
					StudentBaseInfoEntity stu=s;
					classStudent.setStudentInfo(stu);
					ZpEvaluateResultEntity result = zpEvaluateResultService.findStudentRegularNormalTimeEvaluate(s.getId().longValue(), schoolId, classId, evaluateId);
					if(result!=null){
					   List<ZpEvaluateDetailEntity> selfDetailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), EvaluateMethod.SELF,null);
					   if(selfDetailList!=null&&!selfDetailList.isEmpty()){
						  classStudent.setSelfStatus(true);
						  classSelfSum=classSelfSum+1;
					   }else{
						  classStudent.setSelfStatus(false);
					   }
					   List<ZpEvaluateDetailEntity> parentDetailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), EvaluateMethod.PARENT,null);
					   if(parentDetailList!=null&&!parentDetailList.isEmpty()){
						 classStudent.setParentStatus(true);
						 classParentSum=classParentSum+1;
					   }else{
						 classStudent.setParentStatus(false);
					   }
					   List<ZpEvaluateDetailEntity> teacherDetailList = zpEvaluateDetailService.findRegularResultDetailList(result.getId(), EvaluateMethod.TEACHER,null);
					   if(teacherDetailList!=null&&!teacherDetailList.isEmpty()){
						 classStudent.setTeacherStatus(true);
						 classTeacherSum=classTeacherSum+1;
					   }else{
						 classStudent.setTeacherStatus(false);
					   }
					   classStudentList.add(classStudent);
				  }else{
					   classStudent.setSelfStatus(false); 
					   classStudent.setParentStatus(false);
					   classStudent.setTeacherStatus(false);
					   classStudentList.add(classStudent);
				  }
				}
			resultDto.setClassStudentList(classStudentList);
			resultDto.setClassSelfSum(classSelfSum);
			resultDto.setClassParentSum(classParentSum);
			resultDto.setClassTeacherSum(classTeacherSum);
		return resultDto;
	}
	/**
	 * 获取学生次评对应的各个方式的评价结果进度列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 * @param method
	 * @return
	 */
	public List<StudentTimeEvaluateStatus> findStudentTimeEvaluateStatusProgressResult(Long resultId, Long schoolId, Long classId, Long evaluateId){
		List<StudentTimeEvaluateStatus> statusList = Lists.newArrayList();
		for(EvaluateMethod method:EvaluateMethod.values()){
			StudentTimeEvaluateStatus status = new StudentTimeEvaluateStatus(method);
			status.setEvaluteStatus(EvaluateStatus.NOT_EVALUATE);
			statusList.add(status);
		}
		ZpEvaluateDegreeInfoEntity firstLeafDegree = zpEvaluateDegreeInfoService.findFirstLeafDegree(schoolId);
		if(resultId!=null){
			List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService.findRegularResultDetailList(resultId, null,firstLeafDegree.getId());
			if(detailList!=null && !detailList.isEmpty()){
				for(StudentTimeEvaluateStatus s:statusList){
					for(ZpEvaluateDetailEntity d:detailList){
						if(d.getEvaluateMethod().equals(s.getMethod())){
							s.setEvaluteStatus(EvaluateStatus.EVALUATED);
							break;
						}
					}
				}
			}
		}
		return statusList;
	}
	
	/**
	 * 获取学生最终报表信息-维度评价部分数据
	 * @param studentId
	 * @param classId
	 * @param schoolId
	 * @param termId
	 * @return
	 * @throws Exception
	 */
	public EventualReportDto getStudentEventualReport(Long studentId,Long classId,Long schoolId,Long termId) throws Exception{
		EventualReportDto evaluateResultDto = new EventualReportDto();
		List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF, EvaluateMethod.PARENT,
				EvaluateMethod.TEACHER);
		// 获取累计列表信息
		List<EvaluateDegreeDto> list = zpEvaluateBusinessService.getRegularEventualResultWithTimeResult(
				schoolId, termId, classId, studentId, methods);
		// 汇总所有要素值（也就是二级维度）
		Integer allValue = 0;
		for (EvaluateDegreeDto oneLevel : list) {
			for (BaseEvaluateDegreeDto twoLevel : oneLevel.getItems()) {
				allValue += twoLevel.getDegreeEvaluateTotalNum();
			}
		}
		evaluateResultDto.setTotalSecondValue(allValue);
		evaluateResultDto.setDegreeInfos(list);
		evaluateResultDto.setLevel(
				evaluateLevelService.getEventualResValue(schoolId, classId, allValue).getName());
		// 获取评语、奖励等信息
		ZpEvaluateResultEntity otherInfo = findEventualResult(studentId, schoolId,
				termId, classId);
		if (otherInfo != null) {
			evaluateResultDto.setPrize(otherInfo.getPrize());
			evaluateResultDto.setFeedback(otherInfo.getFeedback());
		}
		return evaluateResultDto;
	}
}
