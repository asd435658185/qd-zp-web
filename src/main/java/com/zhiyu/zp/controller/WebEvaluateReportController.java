package com.zhiyu.zp.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.SchoolBaseInfoEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TeacherBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.ISchoolBaseInfoService;
import com.zhiyu.baseplatform.service.IStudentBaseInfoService;
import com.zhiyu.baseplatform.service.ITeacherBaseInfoService;
import com.zhiyu.baseplatform.service.ITermBaseInfoService;
import com.zhiyu.baseplatform.service.SchoolTermHandler;
import com.zhiyu.zp.adapter.ZpStudentBaseInfoAdapter;
import com.zhiyu.zp.bean.BodyResultBean;
import com.zhiyu.zp.bean.JavaBeanPerson;
import com.zhiyu.zp.bean.MarkResultBean;
import com.zhiyu.zp.bean.OneLevelBean;
import com.zhiyu.zp.collection.DegreeEvaluateCollection;
import com.zhiyu.zp.dao.impl.DegreeCollectionDao;
import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;
import com.zhiyu.zp.dto.request.WebBodyStudentReportQryDto;
import com.zhiyu.zp.dto.request.WebEvaluateEventualRequestDto;
import com.zhiyu.zp.dto.request.WebMarkQryDto;
import com.zhiyu.zp.dto.request.WebReportQryRequestDto;
import com.zhiyu.zp.dto.request.WebTimeEvaluateStudentRequestDto;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.dto.response.app.report.EventualStudentInfoDto;
import com.zhiyu.zp.dto.response.app.report.StudentTimeEvaluateStatus;
import com.zhiyu.zp.dto.response.web.body.BodyResultContentDto;
import com.zhiyu.zp.dto.response.web.mark.MarkResultContentDto;
import com.zhiyu.zp.dto.response.web.report.ReportBagResultDto;
import com.zhiyu.zp.entity.ZpBodyItemEntity;
import com.zhiyu.zp.entity.ZpBodyResultEntity;
import com.zhiyu.zp.entity.ZpBodyResultLevelEntity;
import com.zhiyu.zp.entity.ZpBodyTemplateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.entity.ZpMarkResultEntity;
import com.zhiyu.zp.entity.ZpMarkSubjectEntity;
import com.zhiyu.zp.entity.ZpMarkTemplateInfoEntity;
import com.zhiyu.zp.entity.ZpTermBaseInfoEntity;
import com.zhiyu.zp.enumcode.BodyResultLevelType;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.enumcode.MarkType;
import com.zhiyu.zp.processor.CollectionIdProcessor;
import com.zhiyu.zp.processor.JasperDataSourceTransfer;
import com.zhiyu.zp.processor.ResponseResultFormatProcessor;
import com.zhiyu.zp.service.IZpBodyItemService;
import com.zhiyu.zp.service.IZpBodyResultLevelService;
import com.zhiyu.zp.service.IZpBodyResultService;
import com.zhiyu.zp.service.IZpBodyTemplateInfoService;
import com.zhiyu.zp.service.IZpEvaluateInfoService;
import com.zhiyu.zp.service.IZpEvaluateResultService;
import com.zhiyu.zp.service.IZpMarkSubjectService;
import com.zhiyu.zp.service.IZpMarkTemplateInfoService;
import com.zhiyu.zp.service.IZpTermBaseInfoService;
import com.zhiyu.zp.service.business.BodyResultService;
import com.zhiyu.zp.service.business.EvaluateLevelService;
import com.zhiyu.zp.service.business.EvaluateResultService;
import com.zhiyu.zp.service.business.MarkResultService;
import com.zhiyu.zp.service.business.ZpEvaluateBagBusinessService;
import com.zhiyu.zp.service.business.ZpEvaluateBusinessService;
import com.zhiyu.zp.utils.JasperHelper;
import com.zhiyu.zp.utils.TransformUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 
 * @author wdj
 *
 */
@RequestMapping("/web/report")
@Controller
public class WebEvaluateReportController {

	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;

	@Autowired
	private ITermBaseInfoService termBaseInfoService;

	@Autowired
	private EvaluateResultService evaluateResultService;

	@Autowired
	private IStudentBaseInfoService studentBaseInfoService;
	
	@Autowired
	private ITeacherBaseInfoService teacherBaseInfoService;

	@Autowired
	private ZpEvaluateBagBusinessService zpEvaluateBagBusinessService;

	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;

	@Autowired
	private EvaluateLevelService evaluateLevelService;

	@Autowired
	private ClassStudentRelationHandler classStudentRelationHandler;

	@Autowired
	private IZpBodyItemService zpBodyItemService;

	@Autowired
	private IZpBodyResultService zpBodyResultService;

	@Autowired
	private IZpBodyTemplateInfoService zpBodyTemplateInfoService;

	@Autowired
	private BodyResultService bodyResultService;

	@Autowired
	private IZpBodyResultLevelService zpBodyResultLevelService;

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;

	@Autowired
	private MarkResultService markResultService;

	@Autowired
	private IZpMarkTemplateInfoService zpMarkTemplateInfoService;

	@Autowired
	private IZpMarkSubjectService zpMarkSubjectService;

	@Autowired
	private ISchoolBaseInfoService schoolBaseInfoService;

	@Autowired
	private IZpTermBaseInfoService zpTermBaseInfoService;

	@Autowired
	private IZpEvaluateResultService zpEvaluateResultService;
	
	@Autowired
	private DegreeCollectionDao degreeCollectionDao;
	
	@Autowired
	private SchoolTermHandler termHandlerService;
	
	@Autowired
	private ClassStudentRelationHandler classStudentHandler;
	
	@Autowired
	private IClassBaseInfoService classBaseService;
	
	@Autowired
	private IGradeBaseInfoService gradeBaseService;
	
	/**
	 * 报表-学校各年级报告册展示列表
	 * 
	 * @param qry
	 * @return
	 */
	@RequestMapping(value = "/bagList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBagReportList(@RequestBody WebReportQryRequestDto qry) {
		List<ReportBagResultDto> dtos = Lists.newArrayList();
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			GradeBaseInfoEntity grade = new GradeBaseInfoEntity();
			grade.setSchoolId(qry.getSchoolId().intValue());
			if (qry.getGradeId() != null) {
				grade.setId(qry.getGradeId().intValue());
			}
			String termName = termBaseInfoService.findObjectById(qry.getTermId().intValue()).getName();
			List<GradeBaseInfoEntity> gradeList = gradeBaseInfoService.findByEntity(grade);
			List<ZpEvaluateBagEntity> bagList = zpEvaluateBagBusinessService.getCurrentSchoolBag(qry.getSchoolId(),
					qry.getTermId());
			for (GradeBaseInfoEntity g : gradeList) {
				ClassBaseInfoEntity cls = new ClassBaseInfoEntity();
				cls.setGradeId(g.getId());
				List<ClassBaseInfoEntity> classList = classBaseInfoService.findByEntity(cls);
				for (ClassBaseInfoEntity cbi : classList) {
					if(cbi.getTermId()!=null){
							if(qry.getTermId().intValue()==cbi.getTermId().intValue()){
								ReportBagResultDto dto = new ReportBagResultDto();
								dto.setClassId(cbi.getId().longValue());
								dto.setClassName(cbi.getName());
								dto.setGradeId(g.getId().longValue());
								dto.setGradeName(g.getName());
								dto.setTermId(qry.getTermId());
								dto.setTermName(termName);
								Long bagId=0L;
								String bagName="";
								for(ZpEvaluateBagEntity bag:bagList){
									String gradeStrInThatTerm = bag.getGradeIds();
									String[] gradeIds = gradeStrInThatTerm.split(",");
									for (String gradeId : gradeIds) {
										if(g.getId().intValue()==Integer.valueOf(gradeId)){
											bagId=bag.getId();
											bagName=bag.getBagName();
											break;
										}
									}
								}
								dto.setBagId(bagId);
								dto.setBagName(bagName);
								dtos.add(dto);
						}
					}else{
						ReportBagResultDto dto = new ReportBagResultDto();
						dto.setClassId(cbi.getId().longValue());
						dto.setClassName(cbi.getName());
						dto.setGradeId(g.getId().longValue());
						dto.setGradeName(g.getName());
						dto.setTermId(qry.getTermId());
						dto.setTermName(termName);
						Long bagId=0L;
						String bagName="";
						for(ZpEvaluateBagEntity bag:bagList){
							String gradeStrInThatTerm = bag.getGradeIds();
							String[] gradeIds = gradeStrInThatTerm.split(",");
							for (String gradeId : gradeIds) {
								if(g.getId().intValue()==Integer.valueOf(gradeId)){
									bagId=bag.getId();
									bagName=bag.getBagName();
									break;
								}
							}
						}
						dto.setBagId(bagId);
						dto.setBagName(bagName);
						dtos.add(dto);
					}
				}
			}
			// 获取对应报告册的评价进度
			List<ZpEvaluateInfoEntity> evaluateList = Lists.newArrayList();
			for(ZpEvaluateBagEntity bag:bagList){
			  List<ZpEvaluateInfoEntity> evaluates = zpEvaluateInfoService.findRegularNormalUsableByBagId(bag.getId());
			  evaluateList.addAll(evaluates);
			}
			// <EvaluateId,<ClassId,Entity>>
			Map<Long, Map<Long, List<ZpEvaluateResultEntity>>> evaluateGatheredResultMap = Maps.newConcurrentMap();
			for (ZpEvaluateInfoEntity evaluate : evaluateList) {
				List<ZpEvaluateResultEntity> gathereds = evaluateResultService.findStudentEvaluateGathered(
						qry.getSchoolId(), null, null, evaluate.getId(), EvaluateType.TOTAL_EVALUATED, false);
				Map<Long, List<ZpEvaluateResultEntity>> classGatheredResultMap = Maps.newConcurrentMap();
				for (ZpEvaluateResultEntity zr : gathereds) {
					if (classGatheredResultMap.containsKey(zr.getClassId())) {
						classGatheredResultMap.get(zr.getClassId()).add(zr);
					} else {
						List<ZpEvaluateResultEntity> subList = Lists.newArrayList();
						subList.add(zr);
						classGatheredResultMap.put(zr.getClassId(), subList);
					}
				}
				evaluateGatheredResultMap.put(evaluate.getId(), classGatheredResultMap);
			}

			for (ReportBagResultDto bagResultDto : dtos) {
				int evaluateComplateNum = 0;
				// 获取对应班级学生列表
				List<ClassStudentRelEntity> classStudents = classStudentRelationHandler
						.findTermClassStudents(qry.getTermId(), qry.getSchoolId(), bagResultDto.getClassId());
				for (Long eid : evaluateGatheredResultMap.keySet()) {
					Map<Long, List<ZpEvaluateResultEntity>> classGatheredResultMap = Maps.newConcurrentMap();
					classGatheredResultMap = evaluateGatheredResultMap.get(eid);
					List<ZpEvaluateResultEntity> list = classGatheredResultMap.get(bagResultDto.getClassId());
					if (list != null && classStudents.size() != 0 && list.size() >= classStudents.size()) {// 如果一个次评下，一个班级的记录数等于学生总数，则表示该班级该次评已全部完成
						evaluateComplateNum++;
					}
				}
				if (evaluateComplateNum == evaluateList.size()) {
					bagResultDto.setPercent(100);
				} else {
					bagResultDto.setPercent(evaluateComplateNum * 100 / evaluateList.size());
				}
			}
			data.put("list", dtos);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}
	}

	/**
	 * 获取学生终评结果列表
	 * 
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/student/list")
	public Map<String, Object> evaluateStudentEventualReportDisplay(HttpServletRequest request,
			@Valid @RequestBody WebEvaluateEventualRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<EventualStudentInfoDto> list = Lists.newArrayList();
			ZpStudentBaseInfoAdapter adapter = new ZpStudentBaseInfoAdapter();
			List<ClassStudentRelEntity> rels = classStudentRelationHandler.findTermClassStudents(dto.getTermId(),
					dto.getSchoolId(), dto.getClassId());

			Map<Long, ZpEvaluateResultEntity> eventualStudentMap = Maps.newConcurrentMap();
			if (dto.getSearchType() == 2) {
				List<ZpEvaluateResultEntity> classResultEntityList = evaluateResultService
						.findEventualResultList(dto.getSchoolId(), dto.getTermId().longValue(), dto.getClassId());
				for (ZpEvaluateResultEntity e : classResultEntityList) {
					eventualStudentMap.put(e.getStudentId(), e);
				}
			}

			Map<Long, ZpEvaluateResultEntity> timeStudentMap = Maps.newConcurrentMap();
			if (dto.getSearchType() != 2) {
				List<ZpEvaluateResultEntity> classTimeResultList = zpEvaluateResultService
						.findStudentRegularNormalTimeEvaluateList(dto.getSchoolId(), dto.getClassId(),
								dto.getEvaluateId());
				for (ZpEvaluateResultEntity e : classTimeResultList) {
					timeStudentMap.put(e.getStudentId(), e);
				}
			}

			for (ClassStudentRelEntity rel : rels) {
				// 各方式评价结果列表
				StudentBaseInfoEntity stu = studentBaseInfoService.findById(rel.getStudentId());
				if(stu==null){
					continue;
				}
				ZpStudentBaseInfoDto stuDto = adapter
						.getDtoFromEntity(stu);
				ZpEvaluateResultEntity entity = null;
				if (eventualStudentMap.containsKey(stuDto.getStudentId())) {
					entity = eventualStudentMap.get(stuDto.getStudentId());
				}
				EventualStudentInfoDto studentDto = new EventualStudentInfoDto();
				BeanUtils.copyProperties(stuDto, studentDto);
				if (entity != null) {
					studentDto.setEvaluateStatus(EvaluateStatus.EVALUATED);
				}
				// 获取学生各种方式评价的评价结果信息
				Long resultId = null;
				if (timeStudentMap.containsKey(studentDto.getStudentId())) {
					resultId = timeStudentMap.get(studentDto.getStudentId()).getId();
					studentDto.setMethodEvaluateStatus(
							evaluateResultService.findStudentTimeEvaluateStatusProgressResult(resultId,
									dto.getSchoolId(), dto.getClassId(), dto.getEvaluateId()));
				} else {
					List<StudentTimeEvaluateStatus> statusList = Lists.newArrayList();
					for (EvaluateMethod method : EvaluateMethod.values()) {
						StudentTimeEvaluateStatus status = new StudentTimeEvaluateStatus(method);
						status.setEvaluteStatus(EvaluateStatus.NOT_EVALUATE);
						statusList.add(status);
					}
					studentDto.setMethodEvaluateStatus(statusList);
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
	 * 获取评估报表中学生的某一次评整体信息
	 * 
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/display")
	public Map<String, Object> evaluateReportDisplay(HttpServletRequest request,
			@Valid @RequestBody WebTimeEvaluateStudentRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF, EvaluateMethod.PARENT,
					EvaluateMethod.TEACHER);
			data.put("list", zpEvaluateBusinessService.getRegularTimeEvaluateResult(dto.getSchoolId(), null,
					dto.getClassId(), dto.getStudentId(), dto.getEvaluateId(), methods));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	
	
	/**
	 * 获取学生终评结果信息（包含有次评汇总信息）
	 * 
	 * @param studentId
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/eventual/{studentId}")
	public Map<String, Object> evaluateEventualReportDisplay(@PathVariable Long studentId,
			@RequestBody WebEvaluateEventualRequestDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			// 获取学期报告册名称
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), dto.getTermId());
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(grade.getId().intValue()==Integer.valueOf(gradeId)){
						bagName=bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName", bagName);
			EventualReportDto evaluateResultDto = evaluateResultService.getStudentEventualReport(studentId,
					dto.getClassId(), dto.getSchoolId(), dto.getTermId());
//			DegreeEvaluateCollection evaluateResultDto = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue(), studentId));
			if(evaluateResultDto==null){
				return ResponseResultFormatProcessor.resultWrapper(null,"抱歉，数据还未生成",1);
			}
			if(evaluateResultDto.getPrize()==null||evaluateResultDto.getFeedback()==null){
				//获取评语、奖励等信息
				ZpEvaluateResultEntity otherInfo = evaluateResultService.findEventualResult(studentId, dto.getSchoolId(), dto.getTermId(), dto.getClassId());
				if(otherInfo!=null){
					if(otherInfo.getPrize()!=null){
						evaluateResultDto.setPrize(otherInfo.getPrize());
					}else{
						evaluateResultDto.setPrize("");
					}
					if (otherInfo.getFeedback()!=null) {
						evaluateResultDto.setFeedback(otherInfo.getFeedback());
					}else{
						evaluateResultDto.setFeedback("");
					}
				}
			}
			data.put("evaluateResult", evaluateResultDto);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	
	/**
	 * 获取学生终评结果信息（包含有次评汇总信息）  图表数据接口
	 * 
	 * @param studentId
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/chart/eventual/{studentId}")
	public Map<String, Object> chartEvaluateEventualReportDisplay(@PathVariable Long studentId,
			@RequestBody WebEvaluateEventualRequestDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			// 获取学期报告册名称
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), dto.getTermId());
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(grade.getId().intValue()==Integer.valueOf(gradeId)){
						bagName=bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName", bagName);
			DegreeEvaluateCollection evaluateResultDto = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue(), studentId));
			if(evaluateResultDto==null){
				return ResponseResultFormatProcessor.resultWrapper(null,"抱歉，数据还未生成",1);
			}
			//获取当前班级学生数
			Integer gradeId = classBaseInfoService.findObjectById(dto.getClassId().intValue()).getGradeId();
			ClassBaseInfoEntity temp = new ClassBaseInfoEntity();
			temp.setGradeId(gradeId);
			DegreeEvaluateCollection classDegreeCount = degreeCollectionDao.queryById(CollectionIdProcessor.getClassCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue()));
			DegreeEvaluateCollection gradeDegreeCount = degreeCollectionDao.queryById(CollectionIdProcessor.getGradeCollectionId(dto.getSchoolId(), gradeId.longValue(), dto.getTermId().intValue()));
			data.put("evaluateResult", evaluateResultDto);
			//年级需要学生数
			List<ClassBaseInfoEntity> clist = classBaseInfoService.findByEntity(temp);
			List<ClassBaseInfoEntity> cls = Lists.newArrayList();
			for (ClassBaseInfoEntity cbi : clist) {
				if(cbi.getTermId()!=null){
						if(dto.getTermId().intValue()==cbi.getTermId().intValue()){
							cls.add(cbi);
					}
				}else{
					cls.add(cbi);
				}
			}
			List<ClassStudentRelEntity> allRels = Lists.newArrayList();
			for(ClassBaseInfoEntity c:cls){
				List<ClassStudentRelEntity> crels = classStudentRelationHandler.findClassStudents(dto.getSchoolId().intValue(), c.getId());
				if(crels!=null && !crels.isEmpty()){
					allRels.addAll(crels);
				}
			}
			if(classDegreeCount!=null){
				List<ClassStudentRelEntity> rels = classStudentRelationHandler.findClassStudents(dto.getSchoolId().intValue(), dto.getClassId().intValue());
				int stunum = rels.size();
				int allstudentnum = allRels.size();
				data.put("stunum", stunum);
				data.put("allstudentnum", allstudentnum);
				data.put("classDegree", classDegreeCount);
				data.put("gradeDegree", gradeDegreeCount);
			}
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 评测项内容
	 * 
	 * @param qryDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/body/items")
	public Map<String, Object> getStudentBodyItemMapInfo(@RequestBody WebBodyStudentReportQryDto qryDto) {
		try {
			Long searchTermId = zpEvaluateBagBusinessService.findSearchTermId(qryDto.getSchoolId(), qryDto.getTermId());
			ZpBodyItemEntity zie = new ZpBodyItemEntity();
			zie.setSchoolId(qryDto.getSchoolId());
			zie.setCreateTime(null);
			zie.setUpdateTime(null);
			List<ZpBodyItemEntity> items = zpBodyItemService.findByEntity(zie);
			Map<Long, String> itemMap = Maps.newConcurrentMap();
			for (ZpBodyItemEntity item : items) {
				itemMap.put(item.getId(), item.getEngCode());
			}
			ZpBodyResultEntity zre = new ZpBodyResultEntity();
			zre.setMarkType(MarkType.LAST);
			zre.setTermId(searchTermId);
			if (qryDto.getStudentId() != null) {
				zre.setStudentId(qryDto.getStudentId());
			} else {
				zre.setStudentNo(qryDto.getStudentNo());
			}
			zre.setCreateTime(null);
			zre.setUpdateTime(null);
			Map<String, String> itemValueMap = Maps.newConcurrentMap();
			List<ZpBodyResultEntity> itemValues = zpBodyResultService.findByEntity(zre);
			if (itemValues != null && !itemValues.isEmpty()) {
				for (ZpBodyResultEntity item : itemValues) {
					Long key = item.getItemId();
					if (itemMap.containsKey(key)) {
						itemValueMap.put(itemMap.get(key), item.getResult());
					}
				}
			} else {
				for (Long k : itemMap.keySet()) {
					itemValueMap.put(itemMap.get(k), "");
				}
			}
			// 加入返回评测等级内容
			ZpBodyResultLevelEntity levelResult = new ZpBodyResultLevelEntity();
			levelResult.setTermId(searchTermId);
			levelResult.setSchoolId(qryDto.getSchoolId());
			levelResult.setStudentNo(qryDto.getStudentNo());
			List<ZpBodyResultLevelEntity> levels = zpBodyResultLevelService.fingByEntity(levelResult);
			if (levels != null && !levels.isEmpty()) {
				BodyResultLevelType levelType = BodyResultLevelType.getType(levels.get(0).getLevelId());
				itemValueMap.put("levelResult", levelType.getName());
				itemValueMap.put("levelResultValue", levelType.getValue().toString());
			} else {
				itemValueMap.put("levelResult", "");
				itemValueMap.put("levelResultValue", "");
			}
			return ResponseResultFormatProcessor.resultWrapper(itemValueMap, "获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "获取失败", 1);
		}
	}

	/**
	 * 返回iReport报表视图
	 * 
	 * @param model
	 *            这种方式在前端页面中展示类似预览的效果，给前端操作人员选择是否下载和打印
	 * @return
	 */
	@RequestMapping(value = "/exportReport", method = RequestMethod.GET)
	public String report(Model model, HttpServletRequest request) {
		// 报表数据源
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());

		// 动态指定报表模板url
		model.addAttribute("url", "/WEB-INF/jasper/report1.jasper");
		model.addAttribute("format", "pdf"); // 报表格式
		model.addAttribute("jrMainDataSource", jrDataSource);

		return "iReportView"; // 对应jasper-views.xml中的bean id
	}

	/**
	 * 返回iReport报表视图
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportReport2", method = RequestMethod.GET)
	public String report2(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		// 报表数据源
		List<JavaBeanPerson> list = JavaBeanPerson.getList();
		HashMap<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("list", list);
		JRDataSource jrDataSource = new JREmptyDataSource();
		// String listJson = JSONObject.toJSONString(list);

		// 第二种配置数据源的方式 InputStream
		// InputStream is = new
		// ByteArrayInputStream(listJson.getBytes("UTF-8"));
		// paramsMap.put("JSON_INPUT_STREAM", is);

		// 动态指定报表模板url
		model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
		model.addAttribute("format", "pdf"); // 报表格式
		model.addAllAttributes(subMap); // 报表格式
		model.addAttribute("jrMainDataSource", jrDataSource);

		return "iReportView"; // 对应jasper-views.xml中的bean id
	}

	/**
	 * 返回iReport报表视图
	 * 
	 * @param model
	 *            这种方式是直接生成文件到服务器上
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportReport3", method = RequestMethod.GET)
	public void report3(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		// 路径动态录入需要生产的pdf文件位置
		String fileepath = "e:/report3.pdf";
		try {
			// 判断文件是否存在
			File f = new File(fileepath);
			if (!f.exists()) {
				f.createNewFile();
			}
			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 获取Javabean list
			List<JavaBeanPerson> userList = JavaBeanPerson.getList();
			// 数据源为javabean
			JRDataSource data = new JRBeanCollectionDataSource(userList);
			// 设置数据
			parameters.put("p", data);
			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/report3.jasper");
			File reportFile = new File(ctxpathIreport);
			// 实际中编译报表很耗时,采用Ireport编译好的报表
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
			// 填充数据
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			// 导出pdf文件
			JasperExportManager.exportReportToPdfFile(jasperPrint, fileepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回iReport报表视图 -这个方式是将文件下载到浏览器中
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportReport4", method = RequestMethod.GET)
	public void report4(Model model, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		// 路径动态录入需要生产的pdf文件位置
		try {
			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 获取Javabean list
			List<JavaBeanPerson> userList = JavaBeanPerson.getList();
			// 数据源为javabean
			JRDataSource data = new JRBeanCollectionDataSource(userList);
			// 设置数据
			parameters.put("p", data);
			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/report4.jasper");
			File reportFile = new File(ctxpathIreport);
			// 导出pdf文件
			JasperHelper.export("pdf", "报告册", reportFile, request, response, parameters, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回iReport报表视图 -这个方式是将文件下载到浏览器中
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportReport5/{studentId}", method = RequestMethod.GET)
	public void report5(Model model, @PathVariable Long studentId, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 路径动态录入需要生产的pdf文件位置
		try {

			String schoolIdStr = request.getParameter("schoolId");
			String classIdStr = request.getParameter("classId");
			String termIdStr = request.getParameter("termId");

			if (StringUtils.isBlank(schoolIdStr) || StringUtils.isBlank(classIdStr) || StringUtils.isBlank(termIdStr)) {
				return;
			}
			Long schoolId = Long.valueOf(schoolIdStr);
			Long classId = Long.valueOf(classIdStr);
			Long termId = Long.valueOf(termIdStr);

			Map<String, Object> data = Maps.newConcurrentMap();
			EventualReportDto evaluateResultDto = new EventualReportDto();
			// 获取学期报告册名称
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(classId.intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=zpEvaluateBagBusinessService.getCurrentSchoolBag(schoolId, termId);
			String bagName="";
			for(ZpEvaluateBagEntity bag:bagList){
				String gradeStrInThatTerm = bag.getGradeIds();
				String[] gradeIds = gradeStrInThatTerm.split(",");
				for (String gradeId : gradeIds) {
					if(grade.getId().intValue()==Integer.valueOf(gradeId)){
						bag.getBagName();
						break;
					}
				}
			}
			data.put("bagName", bagName);
			List<EvaluateMethod> methods = Lists.newArrayList(EvaluateMethod.SELF, EvaluateMethod.PARENT,
					EvaluateMethod.TEACHER);
			// 获取累计列表信息
			List<EvaluateDegreeDto> list = zpEvaluateBusinessService.getRegularEventualResultWithTimeResult(schoolId,
					null, classId, studentId, methods);
			// 汇总所有要素值（也就是二级维度）
			Integer allValue = 0;
			for (EvaluateDegreeDto oneLevel : list) {
				for (BaseEvaluateDegreeDto twoLevel : oneLevel.getItems()) {
					allValue += twoLevel.getDegreeEvaluateTotalNum();
				}
			}
			evaluateResultDto.setTotalSecondValue(allValue);
			evaluateResultDto.setDegreeInfos(list);
			evaluateResultDto.setLevel(evaluateLevelService.getEventualResValue(schoolId, classId, allValue).getName());
			// 获取评语、奖励等信息
			ZpEvaluateResultEntity otherInfo = evaluateResultService.findEventualResult(studentId, schoolId, termId,
					classId);
			if (otherInfo != null) {
				evaluateResultDto.setPrize(otherInfo.getPrize());
				evaluateResultDto.setFeedback(otherInfo.getFeedback());
			}
			data.put("evaluateResult", evaluateResultDto);

			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 获取Javabean list
			List<JavaBeanPerson> userList = JavaBeanPerson.getList();
			// 数据源为javabean
			JRDataSource jrdata = new JRBeanCollectionDataSource(userList);
			// 设置数据
			parameters.put("p", jrdata);
			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/report5.jasper");
			File reportFile = new File(ctxpathIreport);
			// 导出pdf文件
			JasperHelper.export("pdf", "报告册", reportFile, request, response, parameters, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回iReport报表视图 -这个方式是将文件下载到浏览器中
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportReport6/{studentId}", method = RequestMethod.GET)
	public void report6(Model model, @PathVariable Long studentId, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 路径动态录入需要生产的pdf文件位置
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			List<MarkResultBean> beans = Lists.newArrayList();
			beans.add(new MarkResultBean("80", "90", "120"));
			beans.add(new MarkResultBean("60", "80", "100"));
			beans.add(new MarkResultBean("70", "90", "120"));

			List<OneLevelBean> ones = Lists.newArrayList();

			parameters.put("performsTableDataList", ones);

			// 数据源为javabean
			// JRDataSource jrdata = new JRBeanCollectionDataSource(beans);
			// 设置数据
			parameters.put("markTableDataList", beans);
			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/report6.jasper");
			File reportFile = new File(ctxpathIreport);
			// 导出pdf文件
			JasperHelper.export("pdf", "报告册", reportFile, request, response, parameters, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回iReport报表视图 -这个方式是将文件下载到浏览器中
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportEventualReport/{studentId}", method = RequestMethod.GET)
	public void report4StudentEventualResult(Model model, @PathVariable Long studentId, WebMarkQryDto dto,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		try {
			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 报告册相关信息
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(studentId.intValue());
			parameters.put("studentName", stu.getName());
			SchoolBaseInfoEntity school = schoolBaseInfoService.findObjectById(dto.getSchoolId().intValue());
			parameters.put("schoolName", school.getSchoolName());
			TermBaseInfoEntity term = termBaseInfoService.findObjectById(dto.getTermId().intValue());
			parameters.put("termName", term.getName());
			ClassBaseInfoEntity classBaseInfo = classBaseInfoService.findObjectById(dto.getClassId().intValue());
			parameters.put("className", classBaseInfo.getName());
			if(classBaseInfo.getMasterTeacherId()!=null){
				TeacherBaseInfoEntity masterTeacher = teacherBaseInfoService.findObjectById(classBaseInfo.getMasterTeacherId());
				if(masterTeacher!=null){
					parameters.put("masterTeacherName", masterTeacher.getName());
				}
			}
			// 开始获取成绩数据
			ZpMarkResultEntity result = new ZpMarkResultEntity();
			result.setCreateTime(null);
			result.setUpdateTime(null);
			result.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, result);
			List<MarkResultContentDto> contents = markResultService.getMarkResults(dto.getSchoolId(), result);
			ZpMarkTemplateInfoEntity templateInfo = zpMarkTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> markEnglishFieldCodes = Lists.newArrayList();
			if (templateInfo != null) {
				String[] arr = templateInfo.getSubjects().split(",");
				for (String id : arr) {
					ZpMarkSubjectEntity subject = zpMarkSubjectService.findById(Long.valueOf(id));
					markEnglishFieldCodes.add(subject.getEngCode());
				}
			}
			// 结束获取成绩数据

			// 获取体质数据开始
			ZpBodyResultEntity body = new ZpBodyResultEntity();
			body.setCreateTime(null);
			body.setUpdateTime(null);
			body.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, body);
			List<BodyResultContentDto> bodyContents = bodyResultService.getBodyResults(dto.getSchoolId(), body);
			ZpBodyTemplateInfoEntity bodytemplateInfo = zpBodyTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> bodyFieldCodes = Lists.newArrayList();
			if (bodytemplateInfo != null) {
				String[] arr = bodytemplateInfo.getItems().split(",");
				for (String id : arr) {
					ZpBodyItemEntity subject = zpBodyItemService.findById(Long.valueOf(id));
					bodyFieldCodes.add(subject.getEngCode());
				}
			}

			// 获取体质数据结束

			JasperDataSourceTransfer transfer = new JasperDataSourceTransfer(markEnglishFieldCodes, bodyFieldCodes);

			List<Map<String, String>> beans = transfer.transfer2MarkData(contents);

			List<BodyResultBean> bodyBeans = transfer.transfer2BodyData(bodyContents);

			// 一级列表数据
//			EventualReportDto eventualReportDto = evaluateResultService.getStudentEventualReport(studentId,
//					dto.getClassId(), dto.getSchoolId(), dto.getTermId());
			EventualReportDto eventualReportDto = new EventualReportDto();
			DegreeEvaluateCollection evaluateResultDto = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue(), studentId));
			if(evaluateResultDto == null){
				eventualReportDto = evaluateResultService.getStudentEventualReport(studentId,
					dto.getClassId(), dto.getSchoolId(), dto.getTermId());
			}else{
				if(evaluateResultDto.getPrize()==null||evaluateResultDto.getFeedback()==null){
					//获取评语、奖励等信息
					ZpEvaluateResultEntity otherInfo = evaluateResultService.findEventualResult(studentId, dto.getSchoolId(), dto.getTermId(), dto.getClassId());
					if(otherInfo!=null){
						if(otherInfo.getPrize()!=null){
							evaluateResultDto.setPrize(otherInfo.getPrize());
						}else{
							evaluateResultDto.setPrize("");
						}
						if (otherInfo.getFeedback()!=null) {
							evaluateResultDto.setFeedback(otherInfo.getFeedback());
						}else{
							evaluateResultDto.setFeedback("");
						}
					}
				}
				BeanUtils.copyProperties(evaluateResultDto, eventualReportDto);
			}
			List<OneLevelBean> ones = transfer.majorData(eventualReportDto);
			//
			// 加入返回评测等级内容
			ZpBodyResultLevelEntity levelResult = new ZpBodyResultLevelEntity();
			levelResult.setTermId(dto.getTermId());
			levelResult.setSchoolId(dto.getSchoolId());
			levelResult.setStudentNo(stu.getNumber());
			List<ZpBodyResultLevelEntity> levels = zpBodyResultLevelService.fingByEntity(levelResult);
			if (levels != null && !levels.isEmpty()) {
				BodyResultLevelType levelType = BodyResultLevelType.getType(levels.get(0).getLevelId());
				if (levelType.equals(BodyResultLevelType.GOOD)) {
					parameters.put("good", "√");
				}
				if (levelType.equals(BodyResultLevelType.FINE)) {
					parameters.put("fine", "√");
				}
				if (levelType.equals(BodyResultLevelType.COMMON)) {
					parameters.put("common", "√");
				}
				if (levelType.equals(BodyResultLevelType.BAD)) {
					parameters.put("bad", "√");
				}
			}
			// 下学期注册/开学时间
			Long currentTermId = zpEvaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			ZpTermBaseInfoEntity t = new ZpTermBaseInfoEntity();
			t.setCreateTime(null);
			t.setUpdateTime(null);
			t.setTermId(currentTermId);
			List<ZpTermBaseInfoEntity> list = zpTermBaseInfoService.findByEntity(t);

			// 设置数据
			if (list != null && !list.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				parameters.put("regDate", sdf.format(list.get(0).getNextRegDate()));
				parameters.put("startDate", sdf.format(list.get(0).getNextStartDate()));
			}
			parameters.put("performsTableDataList", ones);
			parameters.put("markTableDataList", beans);
			if (bodyBeans != null && !bodyBeans.isEmpty()) {
				parameters.putAll(TransformUtil.objectToMap(bodyBeans.get(0)));
			}
			parameters.put("totalStars", eventualReportDto.getTotalSecondValue() + "★");
			parameters.put("degreeLevelName", eventualReportDto.getLevel());
			parameters.put("prize", eventualReportDto.getPrize());
			parameters.put("feedback", eventualReportDto.getFeedback());

			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/" + stu.getSchoolId() + "/report.jasper");
			File reportFile = new File(ctxpathIreport);
//			// 导出pdf文件
			JasperHelper.export("pdf", stu.getName() + "报告册", reportFile, request, response, parameters,
					new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		
	}

	/**
	 * 返回iReport报表视图
	 * 
	 * @param model
	 *            这种方式在前端页面中展示类似预览的效果，给前端操作人员选择是否下载和打印
	 * @return
	 */
	@RequestMapping(value = "/previewEventualReport/{studentId}", method = RequestMethod.GET)
	public String previewStudentEventualResult(Model model, @PathVariable Long studentId, WebMarkQryDto dto,
			HttpServletRequest request) {
		// 自定义数据源
		Map<String, Object> parameters = new HashMap<String, Object>();

		try {
			// 报告册相关信息
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(studentId.intValue());
			parameters.put("studentName", stu.getName());
			SchoolBaseInfoEntity school = schoolBaseInfoService.findObjectById(dto.getSchoolId().intValue());
			parameters.put("schoolName", school.getSchoolName());
			TermBaseInfoEntity term = termBaseInfoService.findObjectById(dto.getTermId().intValue());
			parameters.put("termName", term.getName());
			ClassBaseInfoEntity classBaseInfo = classBaseInfoService.findObjectById(dto.getClassId().intValue());
			parameters.put("className", classBaseInfo.getName());

			// 开始获取成绩数据
			ZpMarkResultEntity result = new ZpMarkResultEntity();
			result.setCreateTime(null);
			result.setUpdateTime(null);
			result.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, result);
			List<MarkResultContentDto> contents = markResultService.getMarkResults(dto.getSchoolId(), result);
			ZpMarkTemplateInfoEntity templateInfo = zpMarkTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> markEnglishFieldCodes = Lists.newArrayList();
			if (templateInfo != null) {
				String[] arr = templateInfo.getSubjects().split(",");
				for (String id : arr) {
					ZpMarkSubjectEntity subject = zpMarkSubjectService.findById(Long.valueOf(id));
					markEnglishFieldCodes.add(subject.getEngCode());
				}
			}
			// 结束获取成绩数据

			// 获取体质数据开始
			ZpBodyResultEntity body = new ZpBodyResultEntity();
			result.setCreateTime(null);
			result.setUpdateTime(null);
			result.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, body);
			List<BodyResultContentDto> bodyContents = bodyResultService.getBodyResults(dto.getSchoolId(), body);
			ZpBodyTemplateInfoEntity bodytemplateInfo = zpBodyTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> bodyFieldCodes = Lists.newArrayList();
			if (bodytemplateInfo != null) {
				String[] arr = bodytemplateInfo.getItems().split(",");
				for (String id : arr) {
					ZpBodyItemEntity subject = zpBodyItemService.findById(Long.valueOf(id));
					bodyFieldCodes.add(subject.getEngCode());
				}
			}

			// 获取体质数据结束

			JasperDataSourceTransfer transfer = new JasperDataSourceTransfer(markEnglishFieldCodes, bodyFieldCodes);

			List<Map<String, String>> beans = transfer.transfer2MarkData(contents);

			List<BodyResultBean> bodyBeans = transfer.transfer2BodyData(bodyContents);

			// 一级列表数据
//			EventualReportDto eventualReportDto = evaluateResultService.getStudentEventualReport(studentId,
//					dto.getClassId(), dto.getSchoolId(), dto.getTermId());
			EventualReportDto eventualReportDto = new EventualReportDto();
			DegreeEvaluateCollection evaluateResultDto = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue(), studentId));
			BeanUtils.copyProperties(evaluateResultDto, eventualReportDto);
			List<OneLevelBean> ones = transfer.majorData(eventualReportDto);
			//
			// 加入返回评测等级内容
			ZpBodyResultLevelEntity levelResult = new ZpBodyResultLevelEntity();
			levelResult.setTermId(dto.getTermId());
			levelResult.setSchoolId(dto.getSchoolId());
			levelResult.setStudentNo(stu.getNumber());
			List<ZpBodyResultLevelEntity> levels = zpBodyResultLevelService.fingByEntity(levelResult);
			if (levels != null && !levels.isEmpty()) {
				BodyResultLevelType levelType = BodyResultLevelType.getType(levels.get(0).getLevelId());
				if (levelType.equals(BodyResultLevelType.GOOD)) {
					parameters.put("good", "√");
				}
				if (levelType.equals(BodyResultLevelType.FINE)) {
					parameters.put("fine", "√");
				}
				if (levelType.equals(BodyResultLevelType.COMMON)) {
					parameters.put("common", "√");
				}
				if (levelType.equals(BodyResultLevelType.BAD)) {
					parameters.put("bad", "√");
				}
			}
			// 下学期注册/开学时间
			Long currentTermId = zpEvaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			ZpTermBaseInfoEntity t = new ZpTermBaseInfoEntity();
			t.setCreateTime(null);
			t.setUpdateTime(null);
			t.setTermId(currentTermId);
			List<ZpTermBaseInfoEntity> list = zpTermBaseInfoService.findByEntity(t);

			// 设置数据
			if (list != null && !list.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				parameters.put("regDate", sdf.format(list.get(0).getNextRegDate()));
				parameters.put("startDate", sdf.format(list.get(0).getNextStartDate()));
			}
			parameters.put("performsTableDataList", ones);
			parameters.put("markTableDataList", beans);
			if (bodyBeans != null && !bodyBeans.isEmpty()) {
				parameters.putAll(TransformUtil.objectToMap(bodyBeans.get(0)));
			}
			parameters.put("totalStars", eventualReportDto.getTotalSecondValue() + "★");
			parameters.put("degreeLevelName", eventualReportDto.getLevel());
			parameters.put("prize", eventualReportDto.getPrize());
			parameters.put("feedback", eventualReportDto.getFeedback());

			JRDataSource jrDataSource = new JREmptyDataSource();

			// 动态指定报表模板url
			model.addAttribute("url", "/WEB-INF/jasper/" + stu.getSchoolId() + "/report.jasper");
			model.addAttribute("format", "pdf"); // 报表格式
			model.addAllAttributes(parameters); // 报表格式
			model.addAttribute("jrMainDataSource", jrDataSource);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "iReportView"; // 对应jasper-views.xml中的bean id
	}

	/**
	 * 返回iReport报表视图 -这个方式是将文件批量下载到浏览器中
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/exportEventualReportByBacth", method = RequestMethod.GET)
	public void report4StudentEventualResultByBacth(Model model, WebMarkQryDto dto, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		List<ClassStudentRelEntity> classStudents = classStudentRelationHandler.findTermClassStudents(dto.getTermId(),
				dto.getSchoolId(), dto.getClassId());
//		Map<Long, ByteArrayOutputStream> reportDataMap = Maps.newConcurrentMap();
//		for (ClassStudentRelEntity cs : classStudents) {
//			reportDataMap.put(cs.getStudentId().longValue(),
//					fillStudentEventualResult(cs.getStudentId().longValue(), dto, request, response));
//		}

		// 创建临时压缩文件
		ZpStudentBaseInfoAdapter adapter = new ZpStudentBaseInfoAdapter();
		// 压缩文件初始设置
		String path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/jasper/" + dto.getSchoolId() + "/output/");
		String base_name = "综评报告册";
		String fileZip = base_name + ".zip"; // 拼接zip文件
		String filePath = path + "\\" + fileZip;// 之后用来生成zip文件

		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
			ZipOutputStream zos = new ZipOutputStream(bos);
			ZipEntry ze = null;
			for (int i = 0; i < classStudents.size(); i++) {// 将所有需要下载的pdf文件都写入临时zip文件
				// 各方式评价结果列表
				ZpStudentBaseInfoDto stuDto = adapter
						.getDtoFromEntity(studentBaseInfoService.findById(classStudents.get(i).getStudentId()));
				ByteArrayOutputStream baos = fillStudentEventualResult(stuDto.getStudentId().longValue(), dto, request, response);
				ze = new ZipEntry(stuDto.getStudentName() + ".pdf");
				zos.putNextEntry(ze);
				zos.write(baos.toByteArray());
				baos.close();
			}
			zos.flush();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 以上，临时压缩文件创建完成

		// 进行浏览器下载
		// 获得浏览器代理信息
		final String userAgent = request.getHeader("USER-AGENT");
		// 判断浏览器代理并分别设置响应给浏览器的编码格式
		String finalFileName = null;
		if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {// IE浏览器
			finalFileName = URLEncoder.encode(fileZip, "UTF8");
			System.out.println("IE浏览器");
		} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
			finalFileName = new String(fileZip.getBytes(), "ISO8859-1");
		} else {
			finalFileName = URLEncoder.encode(fileZip, "UTF8");// 其他浏览器
		}
		response.setContentType("application/x-download");// 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
		response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");// 下载文件的名称

		try {
			ServletOutputStream servletOutputStream = response.getOutputStream();
			DataOutputStream temps = new DataOutputStream(servletOutputStream);

			DataInputStream in = new DataInputStream(new FileInputStream(filePath));// 浏览器下载文件的路径
			byte[] b = new byte[2048];
			File reportZip = new File(filePath);// 之后用来删除临时压缩文件
			try {
				while ((in.read(b)) != -1) {
					temps.write(b);
				}
				temps.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temps != null)
					try {
						temps.close();
						if (in != null) {
							in.close();
						}
						if (reportZip != null) {
							reportZip.delete();// 删除服务器本地产生的临时压缩文件
						}
						servletOutputStream.close();
					} catch (IOException e) {

					}

			}
		} catch (Exception e) {

		}

	}

	private ByteArrayOutputStream fillStudentEventualResult(Long studentId, WebMarkQryDto dto,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 自定义数据源
			Map<String, Object> parameters = new HashMap<String, Object>();

			// 报告册相关信息
			StudentBaseInfoEntity stu = studentBaseInfoService.findById(studentId.intValue());
			parameters.put("studentName", stu.getName());
			SchoolBaseInfoEntity school = schoolBaseInfoService.findObjectById(dto.getSchoolId().intValue());
			parameters.put("schoolName", school.getSchoolName());
			TermBaseInfoEntity term = termBaseInfoService.findObjectById(dto.getTermId().intValue());
			parameters.put("termName", term.getName());
			ClassBaseInfoEntity classBaseInfo = classBaseInfoService.findObjectById(dto.getClassId().intValue());
			parameters.put("className", classBaseInfo.getName());
			if(classBaseInfo.getMasterTeacherId()!=null){
				TeacherBaseInfoEntity masterTeacher = teacherBaseInfoService.findObjectById(classBaseInfo.getMasterTeacherId());
				if(masterTeacher!=null){
					parameters.put("masterTeacherName", masterTeacher.getName());
				}
			}
			// 开始获取成绩数据
			ZpMarkResultEntity result = new ZpMarkResultEntity();
			result.setCreateTime(null);
			result.setUpdateTime(null);
			result.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, result);
			List<MarkResultContentDto> contents = markResultService.getMarkResults(dto.getSchoolId(), result);
			ZpMarkTemplateInfoEntity templateInfo = zpMarkTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> markEnglishFieldCodes = Lists.newArrayList();
			if (templateInfo != null) {
				String[] arr = templateInfo.getSubjects().split(",");
				for (String id : arr) {
					ZpMarkSubjectEntity subject = zpMarkSubjectService.findById(Long.valueOf(id));
					markEnglishFieldCodes.add(subject.getEngCode());
				}
			}
			// 结束获取成绩数据

			// 获取体质数据开始
			ZpBodyResultEntity body = new ZpBodyResultEntity();
			body.setCreateTime(null);
			body.setUpdateTime(null);
			body.setStudentNo(stu.getNumber());
			BeanUtils.copyProperties(dto, body);
			List<BodyResultContentDto> bodyContents = bodyResultService.getBodyResults(dto.getSchoolId(), body);
			ZpBodyTemplateInfoEntity bodytemplateInfo = zpBodyTemplateInfoService.findBySchool(dto.getSchoolId());
			List<String> bodyFieldCodes = Lists.newArrayList();
			if (bodytemplateInfo != null) {
				String[] arr = bodytemplateInfo.getItems().split(",");
				for (String id : arr) {
					ZpBodyItemEntity subject = zpBodyItemService.findById(Long.valueOf(id));
					bodyFieldCodes.add(subject.getEngCode());
				}
			}

			// 获取体质数据结束

			JasperDataSourceTransfer transfer = new JasperDataSourceTransfer(markEnglishFieldCodes, bodyFieldCodes);

			List<Map<String, String>> beans = transfer.transfer2MarkData(contents);

			List<BodyResultBean> bodyBeans = transfer.transfer2BodyData(bodyContents);

			// 一级列表数据
//			EventualReportDto eventualReportDto = evaluateResultService.getStudentEventualReport(studentId,
//					dto.getClassId(), dto.getSchoolId(), dto.getTermId());
			EventualReportDto eventualReportDto = new EventualReportDto();
			DegreeEvaluateCollection evaluateResultDto = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(dto.getSchoolId(), dto.getClassId(), dto.getTermId().intValue(), studentId));
			BeanUtils.copyProperties(evaluateResultDto, eventualReportDto);
			List<OneLevelBean> ones = transfer.majorData(eventualReportDto);
			//
			// 加入返回评测等级内容
			ZpBodyResultLevelEntity levelResult = new ZpBodyResultLevelEntity();
			levelResult.setTermId(dto.getTermId());
			levelResult.setSchoolId(dto.getSchoolId());
			levelResult.setStudentNo(stu.getNumber());
			List<ZpBodyResultLevelEntity> levels = zpBodyResultLevelService.fingByEntity(levelResult);
			if (levels != null && !levels.isEmpty()) {
				BodyResultLevelType levelType = BodyResultLevelType.getType(levels.get(0).getLevelId());
				if (levelType.equals(BodyResultLevelType.GOOD)) {
					parameters.put("good", "√");
				}
				if (levelType.equals(BodyResultLevelType.FINE)) {
					parameters.put("fine", "√");
				}
				if (levelType.equals(BodyResultLevelType.COMMON)) {
					parameters.put("common", "√");
				}
				if (levelType.equals(BodyResultLevelType.BAD)) {
					parameters.put("bad", "√");
				}
			}
			// 下学期注册/开学时间
			Long currentTermId = zpEvaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			ZpTermBaseInfoEntity t = new ZpTermBaseInfoEntity();
			t.setCreateTime(null);
			t.setUpdateTime(null);
			t.setTermId(currentTermId);
			List<ZpTermBaseInfoEntity> list = zpTermBaseInfoService.findByEntity(t);

			// 设置数据
			if (list != null && !list.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				parameters.put("regDate", sdf.format(list.get(0).getNextRegDate()));
				parameters.put("startDate", sdf.format(list.get(0).getNextStartDate()));
			}
			parameters.put("performsTableDataList", ones);
			parameters.put("markTableDataList", beans);
			if (bodyBeans != null && !bodyBeans.isEmpty()) {
				parameters.putAll(TransformUtil.objectToMap(bodyBeans.get(0)));
			}
			parameters.put("totalStars", eventualReportDto.getTotalSecondValue() + "★");
			parameters.put("degreeLevelName", eventualReportDto.getLevel());
			parameters.put("prize", eventualReportDto.getPrize());
			parameters.put("feedback", eventualReportDto.getFeedback());

			// 模板文件
			String ctxpathIreport = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/jasper/" + stu.getSchoolId() + "/report.jasper");
			File reportFile = new File(ctxpathIreport);
			// 导出pdf文件
			// JasperHelper.export("pdf", stu.getName() + "报告册", reportFile,
			// request, response, parameters,
			// new JREmptyDataSource());

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
			// 填充数据
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			return baos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 同步学校终评结果信息（临时）
	 * 
	 * @param studentId
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/eventualSchool/{schoolId}")
	public Map<String, Object> eventualSchool(@PathVariable Long schoolId,
			@RequestBody WebEvaluateEventualRequestDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
				// 查询该学校的当前学期
				TermBaseInfoEntity term = termHandlerService.getCurrentTermInfoBySchoolId(schoolId.intValue());
				if (term == null) {
					return ResponseResultFormatProcessor.resultWrapper(null, "该学校对应学期信息有缺失", 1);
				} else {
					//获取全校班级
					Map<Integer, Integer> gradeClassNumMap = Maps.newConcurrentMap();
					Map<Integer, Integer> classStudentNumMap = Maps.newConcurrentMap();
					GradeBaseInfoEntity grade = new GradeBaseInfoEntity();
					grade.setSchoolId(schoolId.intValue());
					List<GradeBaseInfoEntity> grades = gradeBaseService.findByEntity(grade);
					List<ClassBaseInfoEntity> clzList = Lists.newArrayList();
					ClassBaseInfoEntity clz = new ClassBaseInfoEntity();
					for(GradeBaseInfoEntity g:grades){
						clz.setGradeId(g.getId());
						List<ClassBaseInfoEntity> clist = classBaseService.findByEntity(clz);
						List<ClassBaseInfoEntity> clzSubList = Lists.newArrayList();
						for (ClassBaseInfoEntity cbi : clist) {
							if(cbi.getTermId()!=null){
									if(dto.getTermId().intValue()==cbi.getTermId().intValue()){
										clzSubList.add(cbi);
								}
							}else{
								clzSubList.add(cbi);
							}
						}
						if(clzSubList!=null && !clzSubList.isEmpty()){
							gradeClassNumMap.put(g.getId(), clzSubList.size());
							clzList.addAll(clzSubList);
						}
						
					}
					//遍历班级下学生，获取所有学生班级关系
					List<ClassStudentRelEntity> classStudentRels = Lists.newArrayList();
					for(ClassBaseInfoEntity c:clzList){
						List<ClassStudentRelEntity> relSubList = classStudentHandler.findClassStudents(schoolId.intValue(), c.getId());
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
									rel.getClassId().longValue(), schoolId, term.getId().longValue());
							Integer gradeId = classBaseService.findObjectById(rel.getClassId()).getGradeId();
							DegreeEvaluateCollection collection = new DegreeEvaluateCollection();
							DegreeEvaluateCollection classCollection = new DegreeEvaluateCollection();
							DegreeEvaluateCollection gradeCollection = new DegreeEvaluateCollection();
							collection = degreeCollectionDao.queryById(CollectionIdProcessor.getCollectionId(schoolId, rel.getClassId().longValue(), term.getId(), rel.getStudentId().longValue()));
							if(collection!=null){
								continue;
							}else{
								if(evaluateResultDto.getFeedback()!=null){
									//汇总个人
									saveStudentPersonal(evaluateResultDto, collection, schoolId, rel.getClassId().longValue(), term.getId(), rel.getStudentId().longValue());
									//汇总对应班级各维度结果
									saveClassDegree(evaluateResultDto, classCollection, schoolId, rel.getClassId().longValue(), term.getId());
									//汇总对应年级各维度结果
									saveGradeDegree(evaluateResultDto, gradeCollection, schoolId, gradeId.longValue(), term.getId());
								}
							}
						} catch (Exception e1) {
							
						}
						
					}
				}
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
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
