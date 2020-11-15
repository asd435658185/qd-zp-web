package com.zhiyu.zp.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.SchoolTermHandler;
import com.zhiyu.zp.collection.DegreeEvaluateCollection;
import com.zhiyu.zp.dao.impl.DegreeCollectionDao;
import com.zhiyu.zp.dto.request.AppEvaluateCommentCommitRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateCommentRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoCommitRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoDegreeRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateInfoStudentRequestDto;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.factory.EvaluateSituationMethodFactory;
import com.zhiyu.zp.processor.CollectionIdProcessor;
import com.zhiyu.zp.processor.ResponseResultFormatProcessor;
import com.zhiyu.zp.service.IZpSchoolSettingService;
import com.zhiyu.zp.service.business.EvaluateResultService;
import com.zhiyu.zp.service.business.ZpEvaluateBagBusinessService;
import com.zhiyu.zp.service.business.ZpEvaluateBusinessService;

/**
 * 移动端（App）评估接口
 * 
 * @author wdj
 *
 */
@RequestMapping("/app/evaluate")
@Controller
public class AppEvaluateController {

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;

	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;
	
	@Autowired
	private EvaluateResultService evaluateResultService;
	
	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;
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
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), dto.getTermId());
			Long bagId=0L;
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(grade.getId().intValue()==Integer.valueOf(gradeId)){
						bagId=bag.getId();
						bagName=bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName", bagName);
			data.put("bagId", bagId);
			data.put("list", new EvaluateSituationMethodFactory().getInstance(dto.getMethod())
					.getEvaluateSituations(dto.getSchoolId(), dto.getClassId(), dto.getOperaterId(), bagId));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据获取成功", 0);
		} catch (EvaluateBagSettingException e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 维度评分提交
	 * 
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/info/commit")
	public Map<String, Object> evaluateInfoCommit(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoCommitRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			zpEvaluateBusinessService.saveTimeEvaluateDetail(dto.getStudentId(), dto.getSchoolId(), dto.getClassId(),
					dto.getEvaluateId(), dto.getItems(), dto.getMethod(), dto.getOperaterId());
			return ResponseResultFormatProcessor.resultWrapper(data, "数据提交成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取学生评价列表
	 * 
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/student/list")
	public Map<String, Object> evaluateInfoStudentList(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoStudentRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			data.put("list", zpEvaluateBusinessService.getClassStudentEvaluateList(dto.getSchoolId(), null,
					dto.getClassId(), dto.getEvaluateId(), dto.getEvaluateStatus()));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 获取评估维度展示信息
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/info/display")
	public Map<String, Object> evaluateInfoDisplay(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateInfoDegreeRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF,EvaluateMethod.PARENT,EvaluateMethod.TEACHER);
			data.put("list", zpEvaluateBusinessService.getRegularTimeEvaluateContent(dto.getSchoolId(), null,  dto.getStudentId(), methods));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 获取教师评语的学生列表
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/comment/student/list")
	public Map<String, Object> commentInfoStudentList(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateCommentRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), null);
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
			data.put("list", zpEvaluateBusinessService.getClassStudentEvaluateCommentList(dto.getSchoolId(), null, dto.getClassId(), bagId));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 评语提交
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/comment/commit")
	public Map<String, Object> evaluateCommentCommit(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateCommentCommitRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			evaluateResultService.saveEvaluateEventualResult(dto.getSchoolId(), dto.getClassId(), dto.getStudentId(), dto.getFeedback(), dto.getPrize());
			return ResponseResultFormatProcessor.resultWrapper(data, "数据提交成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	@Autowired
	private IZpSchoolSettingService zpSchoolSettingService;

	@Autowired
	private SchoolTermHandler termHandlerService;
	
	@Autowired
	private ClassStudentRelationHandler classStudentHandler;
	
	@Autowired
	private IClassBaseInfoService classBaseService;
	
	@Autowired
	private IGradeBaseInfoService gradeBaseService;
	
	@Autowired
	private DegreeCollectionDao degreeCollectionDao;
	/**
	 * 数据更新
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/degreeEvaluateResultTimerTask")
	public Map<String, Object> degreeEvaluateResultTimerTask(HttpServletRequest request,
			@Valid @RequestBody AppEvaluateCommentCommitRequestDto dto, BindingResult bindingResult) {
		        Integer s=4;
		        Long schoolId =s.longValue();
		        Integer t=15;
		       //获取全校班级
				Map<Integer, Integer> gradeClassNumMap = Maps.newConcurrentMap();
				Map<Integer, Integer> classStudentNumMap = Maps.newConcurrentMap();
				GradeBaseInfoEntity grade = new GradeBaseInfoEntity();
				grade.setSchoolId(4);
				List<GradeBaseInfoEntity> grades = gradeBaseService.findByEntity(grade);
				List<ClassBaseInfoEntity> clzList = Lists.newArrayList();
				ClassBaseInfoEntity clz = new ClassBaseInfoEntity();
				for(GradeBaseInfoEntity g:grades){
					clz.setGradeId(g.getId());
					clz.setTermId(t);
					List<ClassBaseInfoEntity> clzSubList = classBaseService.findByEntity(clz);
					if(clzSubList!=null && !clzSubList.isEmpty()){
						gradeClassNumMap.put(g.getId(), clzSubList.size());
						clzList.addAll(clzSubList);
					}
					
				}
				//遍历班级下学生，获取所有学生班级关系
				List<ClassStudentRelEntity> classStudentRels = Lists.newArrayList();
				for(ClassBaseInfoEntity c:clzList){
					List<ClassStudentRelEntity> relSubList = classStudentHandler.findTermClassStudents(t.longValue(),
							schoolId, c.getId().longValue());
					if(relSubList!=null && !relSubList.isEmpty()){
						classStudentRels.addAll(relSubList);
						classStudentNumMap.put(c.getId(), relSubList.size());
					}
					
				}
				//遍历当前学期下学生班级关系，获取学生综评结果
				for(ClassStudentRelEntity rel:classStudentRels){
					EventualReportDto evaluateResultDto;
					try {
						evaluateResultDto = evaluateResultService.getStudentEventualReport(rel.getStudentId().longValue(),
								rel.getClassId().longValue(), schoolId, t.longValue());
						Integer gradeId = classBaseService.findObjectById(rel.getClassId()).getGradeId();
						DegreeEvaluateCollection collection = new DegreeEvaluateCollection();
						DegreeEvaluateCollection classCollection = new DegreeEvaluateCollection();
						DegreeEvaluateCollection gradeCollection = new DegreeEvaluateCollection();
						collection = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(schoolId, rel.getClassId().longValue(), t, rel.getStudentId().longValue()));
						if(collection!=null){
							continue;
						}else{
							//汇总个人
							saveStudentPersonal(evaluateResultDto, collection,schoolId, rel.getClassId().longValue(), t, rel.getStudentId().longValue());
							//汇总对应班级各维度结果
							saveClassDegree(evaluateResultDto, classCollection, schoolId, rel.getClassId().longValue(), t);
							//汇总对应年级各维度结果
							saveGradeDegree(evaluateResultDto, gradeCollection, schoolId, gradeId.longValue(), t);
						}
					} catch (Exception e1) {
					}
					
				}
		return ResponseResultFormatProcessor.resultWrapper(null, "数据提交成功", 0);
	}
	private void saveStudentPersonal(EventualReportDto evaluateResultDto,DegreeEvaluateCollection collection,Long schoolId,Long classId,Integer termId,Long studentId){
		collection = new DegreeEvaluateCollection();
		collection.setId(CollectionIdProcessor.getCollectionId(schoolId, classId, termId, studentId));
		collection.setDegreeInfos(evaluateResultDto.getDegreeInfos());
		collection.setFeedback(evaluateResultDto.getFeedback());
		collection.setLevel(evaluateResultDto.getLevel());
		collection.setPrize(evaluateResultDto.getPrize());
		collection.setTotalSecondValue(evaluateResultDto.getTotalSecondValue());
		degreeCollectionDao.save(collection);
	}
	
	@SuppressWarnings("unchecked")
	private void saveClassDegree(EventualReportDto evaluateResultDto,DegreeEvaluateCollection classCollection,Long schoolId,Long classId,Integer termId){
		Map<Long, EvaluateDegreeDto> degreeBaseMap = Maps.newConcurrentMap();
		List<EvaluateDegreeDto> classDegreeInfos = Lists.newArrayList();
		for(EvaluateDegreeDto dto:evaluateResultDto.getDegreeInfos()){
			EvaluateDegreeDto cdto = new EvaluateDegreeDto();
			cdto.setDegreeId(dto.getDegreeId());
			cdto.setDegreeEvaluateTotalNum(getItemNumCount(dto));
			classDegreeInfos.add(cdto);
			degreeBaseMap.put(cdto.getDegreeId(), cdto);
		}
		classCollection = degreeCollectionDao.queryById(CollectionIdProcessor.getClassCollectionId(schoolId, classId, termId));
		if(classCollection==null){
			classCollection = new DegreeEvaluateCollection();
			classCollection.setId(CollectionIdProcessor.getClassCollectionId(schoolId, classId, termId));
			classCollection.setDegreeInfos(classDegreeInfos);
		}else{
			Iterator<EvaluateDegreeDto> it = (Iterator<EvaluateDegreeDto>) classCollection.getDegreeInfos().iterator();
			while(it.hasNext()){
				EvaluateDegreeDto value = it.next();
				EvaluateDegreeDto newValue = degreeBaseMap.get(value.getDegreeId());
				newValue.setDegreeEvaluateTotalNum(newValue.getDegreeEvaluateTotalNum()+value.getDegreeEvaluateTotalNum());
			}
			classCollection.setDegreeInfos(classDegreeInfos);
		}
		degreeCollectionDao.save(classCollection);
	}
	
	@SuppressWarnings("unchecked")
	private void saveGradeDegree(EventualReportDto evaluateResultDto,DegreeEvaluateCollection gradeCollection,Long schoolId,Long gradeId,Integer termId){
		Map<Long, EvaluateDegreeDto> degreeBaseMap = Maps.newConcurrentMap();
		List<EvaluateDegreeDto> classDegreeInfos = Lists.newArrayList();
		for(EvaluateDegreeDto dto:evaluateResultDto.getDegreeInfos()){
			EvaluateDegreeDto cdto = new EvaluateDegreeDto();
			cdto.setDegreeId(dto.getDegreeId());
			cdto.setDegreeEvaluateTotalNum(getItemNumCount(dto));
			classDegreeInfos.add(cdto);
			degreeBaseMap.put(cdto.getDegreeId(), cdto);
		}
		gradeCollection = degreeCollectionDao.queryById(CollectionIdProcessor.getGradeCollectionId(schoolId, gradeId, termId));
		if(gradeCollection==null){
			gradeCollection = new DegreeEvaluateCollection();
			gradeCollection.setId(CollectionIdProcessor.getGradeCollectionId(schoolId, gradeId, termId));
			gradeCollection.setDegreeInfos(classDegreeInfos);
		}else{
			Iterator<EvaluateDegreeDto> it = (Iterator<EvaluateDegreeDto>) gradeCollection.getDegreeInfos().iterator();
			while(it.hasNext()){
				EvaluateDegreeDto value = it.next();
				EvaluateDegreeDto newValue = degreeBaseMap.get(value.getDegreeId());
				newValue.setDegreeEvaluateTotalNum(newValue.getDegreeEvaluateTotalNum()+value.getDegreeEvaluateTotalNum());
			}
			gradeCollection.setDegreeInfos(classDegreeInfos);
		}
		degreeCollectionDao.save(gradeCollection);
	}
	
	private Integer getItemNumCount(EvaluateDegreeDto degree){
		Integer num = 0;
		for(BaseEvaluateDegreeDto dto:degree.getItems()){
			num += dto.getDegreeEvaluateTotalNum();
		}
		return num;
	}
}
