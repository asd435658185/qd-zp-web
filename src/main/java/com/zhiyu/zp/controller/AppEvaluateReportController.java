package com.zhiyu.zp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.adapter.ClassBaseInfoAdapter;
import com.zhiyu.baseplatform.dto.ClassBaseInfoDto;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IStudentBaseInfoService;
import com.zhiyu.zp.adapter.ZpStudentBaseInfoAdapter;
import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;
import com.zhiyu.zp.dto.request.AppEvaluateEventualRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoDegreeRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoEventualReportRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoRequestDto;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.dto.response.app.report.EventualStudentInfoDto;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.factory.EvaluateSituationMethodFactory;
import com.zhiyu.zp.processor.ResponseResultFormatProcessor;
import com.zhiyu.zp.service.business.EvaluateLevelService;
import com.zhiyu.zp.service.business.EvaluateResultService;
import com.zhiyu.zp.service.business.ZpEvaluateBagBusinessService;
import com.zhiyu.zp.service.business.ZpEvaluateBusinessService;
import com.zhiyu.zp.strategy.EvaluateSituationMethodStrategy;

/**
 * 移动端（App）评估报表相关接口
 * 
 * @author wdj
 *
 */
@RequestMapping("/app/report")
@Controller
public class AppEvaluateReportController {

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;

	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;
	
	@Autowired
	private EvaluateLevelService evaluateLevelService;
	
	@Autowired
	private EvaluateResultService evaluateResultService;
	
	@Autowired
	private ClassBaseInfoAdapter classBaseInfoAdapter;
	
	@Autowired
	private IClassBaseInfoService classBaseInfoService;
	
	@Autowired
	private ClassStudentRelationHandler classStudentRelationHandler;
	
	@Autowired
	private IStudentBaseInfoService studentBaseInfoService;
	
	/**
	 * 次评情况列表
	 * 
	 * @param request
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/info/list")
	public Map<String, Object> evaluateInfoList(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), null);
			Long bagId=0L;
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(classBase.getGradeId()==Integer.valueOf(gradeId)){
						bagId=bag.getId();
						bagName=bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName", bagName);
			data.put("bagId", bagId);
			
			EvaluateSituationMethodStrategy strategy = new EvaluateSituationMethodFactory().getInstance(dto.getMethod());
			data.put("list", strategy.getEvaluateSituations(dto.getSchoolId(), null, dto.getOperaterId(), bagId));
			Long classId = strategy.getClassId(dto.getSchoolId(), dto.getOperaterId(), bagId);
			if(classId==null){
				return ResponseResultFormatProcessor.resultWrapper(null, "班级信息设定有误，请确认一下", 1);
			}
			data.put("classId", classId);
			
			ClassBaseInfoDto clzInfo = classBaseInfoAdapter.getResponseDtoFromEntity(classBaseInfoService.findObjectById(classId.intValue()));
			StringBuffer className = new StringBuffer();
			className.append(clzInfo.getGradeName()).append(clzInfo.getName());
			data.put("className", className.toString());
			return ResponseResultFormatProcessor.resultWrapper(data, "数据获取成功", 0);
		} catch (EvaluateBagSettingException e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取学生终评结果列表
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/student/list")
	public Map<String, Object> evaluateStudentEventualReportDisplay(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateEventualRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<EventualStudentInfoDto> list = Lists.newArrayList();
			ZpStudentBaseInfoAdapter adapter = new ZpStudentBaseInfoAdapter();
			List<ClassStudentRelEntity> rels = classStudentRelationHandler.findTermClassStudents(dto.getTermId(), dto.getSchoolId(), dto.getClassId());
			for(ClassStudentRelEntity rel:rels){
				ZpStudentBaseInfoDto stuDto = adapter.getDtoFromEntity(studentBaseInfoService.findById(rel.getStudentId()));
				ZpEvaluateResultEntity entity = evaluateResultService.findEventualResult(rel.getStudentId().longValue(), dto.getSchoolId(), rel.getTermId().longValue(), dto.getClassId());
				EventualStudentInfoDto studentDto = new EventualStudentInfoDto();
				BeanUtils.copyProperties(stuDto, studentDto);
				if(entity!=null){
					studentDto.setEvaluateStatus(EvaluateStatus.EVALUATED);
				}
				list.add(studentDto);
			}
			data.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 获取评估报表信息
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/display")
	public Map<String, Object> evaluateReportDisplay(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoDegreeRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF,EvaluateMethod.PARENT,EvaluateMethod.TEACHER);
			data.put("list", zpEvaluateBusinessService.getRegularTimeEvaluateResult(dto.getSchoolId(), null, dto.getClassId(), dto.getStudentId(), dto.getEvaluateId(), methods));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 获取评估终极报表
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/eventual")
	public Map<String, Object> evaluateEventualReportDisplay(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoEventualReportRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			EventualReportDto evaluateResultDto = new EventualReportDto();
			//获取学期报告册名称
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), null);
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(classBase.getGradeId()==Integer.valueOf(gradeId)){
						bagName=bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName",bagName );
			//获取累计列表信息
			List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF,EvaluateMethod.PARENT,EvaluateMethod.TEACHER);
			List<EvaluateDegreeDto> list = zpEvaluateBusinessService.getRegularEventualResultWithTimeResult(dto.getSchoolId(), null, dto.getClassId(), dto.getStudentId(), methods);
			//汇总所有要素值（也就是二级维度）
			Integer allValue = 0;
			for(EvaluateDegreeDto oneLevel:list){
				for(BaseEvaluateDegreeDto twoLevel:oneLevel.getItems()){
					allValue+=twoLevel.getDegreeEvaluateTotalNum();
				}
			}
			evaluateResultDto.setTotalSecondValue(allValue);
			evaluateResultDto.setDegreeInfos(list);
			evaluateResultDto.setLevel(evaluateLevelService.getEventualResValue(dto.getSchoolId(), dto.getClassId(), allValue).getName());
			//获取评语、奖励等信息
			ZpEvaluateResultEntity otherInfo = evaluateResultService.findEventualResult(dto.getStudentId(), dto.getSchoolId(), dto.getTermId(), dto.getClassId());
			if(otherInfo!=null){
				evaluateResultDto.setPrize(otherInfo.getPrize());
				evaluateResultDto.setFeedback(otherInfo.getFeedback());
			}
			data.put("evaluateResult", evaluateResultDto);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
}
