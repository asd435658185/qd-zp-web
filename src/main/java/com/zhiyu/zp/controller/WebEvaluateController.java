package com.zhiyu.zp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.ClassTeacherRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TeacherBaseInfoEntity;
import com.zhiyu.baseplatform.entity.UserAccessEntity;
import com.zhiyu.baseplatform.entity.UserAuthenticationEntity;
import com.zhiyu.baseplatform.enumcode.UploadFileType;
import com.zhiyu.baseplatform.processor.PictureInfoBeanFactory;
import com.zhiyu.baseplatform.processor.PictureSaveAndDisplayProcessor;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.ClassTeacherRelationHandler;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.ISchoolBaseInfoService;
import com.zhiyu.baseplatform.service.ITeacherBaseInfoService;
import com.zhiyu.baseplatform.service.IUserAccessService;
import com.zhiyu.baseplatform.service.IUserAuthenticationService;
import com.zhiyu.baseplatform.util.Md5;
import com.zhiyu.zp.adapter.ZpBagBaseInfoAdapter;
import com.zhiyu.zp.adapter.ZpEvaluateBaseInfoAdapter;
import com.zhiyu.zp.adapter.ZpEvaluateDegreeInfoAdapter;
import com.zhiyu.zp.bean.ZpEvaluateLevelSettingResValueComparatorBean;
import com.zhiyu.zp.bean.ZpEvaluateWeightSettingResValueComparatorBean;
import com.zhiyu.zp.dto.common.WebBaseRequestDto;
import com.zhiyu.zp.dto.request.AppEvaluateCommentRequestDto;
import com.zhiyu.zp.dto.request.UserLoginDto;
import com.zhiyu.zp.dto.request.WebBagQryDto;
import com.zhiyu.zp.dto.request.WebBodyImportDto;
import com.zhiyu.zp.dto.request.WebBodyQryDto;
import com.zhiyu.zp.dto.request.WebCommentFuzzyQryDto;
import com.zhiyu.zp.dto.request.WebCommentQryDto;
import com.zhiyu.zp.dto.request.WebDegreeQryDto;
import com.zhiyu.zp.dto.request.WebDegreeUpdateRequestDto;
import com.zhiyu.zp.dto.request.WebEvaluateQryDto;
import com.zhiyu.zp.dto.request.WebGradeLevelQryDto;
import com.zhiyu.zp.dto.request.WebMarkImportDto;
import com.zhiyu.zp.dto.request.WebMarkQryDto;
import com.zhiyu.zp.dto.request.WebTermQryDto;
import com.zhiyu.zp.dto.response.web.UserInfo;
import com.zhiyu.zp.dto.response.web.bag.BagBaseInfo;
import com.zhiyu.zp.dto.response.web.body.BodyResultContentDto;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeValidDto;
import com.zhiyu.zp.dto.response.web.evaluate.BaseAddInfo;
import com.zhiyu.zp.dto.response.web.evaluate.BaseInfoDto;
import com.zhiyu.zp.dto.response.web.evaluate.BaseInfoUpdateUseableDto;
import com.zhiyu.zp.dto.response.web.evaluate.BaseUpdateInfo;
import com.zhiyu.zp.dto.response.web.evaluate.StudentResultStatusDto;
import com.zhiyu.zp.dto.response.web.gathersetting.BagBaseSetInfo;
import com.zhiyu.zp.dto.response.web.gradesetting.GradeSettingDto;
import com.zhiyu.zp.dto.response.web.gradesetting.UpdateSettingDto;
import com.zhiyu.zp.dto.response.web.level.EventualLevelResultDto;
import com.zhiyu.zp.dto.response.web.level.EventualLevelResultUpdateDto;
import com.zhiyu.zp.dto.response.web.mark.MarkResultContentDto;
import com.zhiyu.zp.dto.response.web.timesetting.BaseStarInfoDto;
import com.zhiyu.zp.dto.response.web.timesetting.WeightUpdateDto;
import com.zhiyu.zp.dto.response.web.timesetting.WeightViewDto;
import com.zhiyu.zp.entity.ZpBodyItemEntity;
import com.zhiyu.zp.entity.ZpBodyResultEntity;
import com.zhiyu.zp.entity.ZpBodyTemplateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeVersionEntity;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;
import com.zhiyu.zp.entity.ZpGradeSettingEntity;
import com.zhiyu.zp.entity.ZpLevelSettingEntity;
import com.zhiyu.zp.entity.ZpMarkResultEntity;
import com.zhiyu.zp.entity.ZpMarkSubjectEntity;
import com.zhiyu.zp.entity.ZpMarkTemplateInfoEntity;
import com.zhiyu.zp.entity.ZpRealisticEntity;
import com.zhiyu.zp.entity.ZpResultCommentEntity;
import com.zhiyu.zp.entity.ZpTermBaseInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.EvaluateLevelType;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.enumcode.GradeLevel;
import com.zhiyu.zp.enumcode.MarkType;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.enumcode.UserRole;
import com.zhiyu.zp.processor.ResponseResultFormatProcessor;
import com.zhiyu.zp.service.IZpBodyItemService;
import com.zhiyu.zp.service.IZpBodyTemplateInfoService;
import com.zhiyu.zp.service.IZpEvaluateBagService;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateDegreeVersionService;
import com.zhiyu.zp.service.IZpEvaluateInfoService;
import com.zhiyu.zp.service.IZpEvaluateWeightSettingService;
import com.zhiyu.zp.service.IZpGradeSettingService;
import com.zhiyu.zp.service.IZpLevelSettingService;
import com.zhiyu.zp.service.IZpMarkSubjectService;
import com.zhiyu.zp.service.IZpMarkTemplateInfoService;
import com.zhiyu.zp.service.IZpRealisticService;
import com.zhiyu.zp.service.IZpResultCommentService;
import com.zhiyu.zp.service.IZpTermBaseInfoService;
import com.zhiyu.zp.service.business.BodyResultService;
import com.zhiyu.zp.service.business.EvaluateDegreeService;
import com.zhiyu.zp.service.business.EvaluateResultService;
import com.zhiyu.zp.service.business.GradeLevelService;
import com.zhiyu.zp.service.business.MarkResultService;
import com.zhiyu.zp.service.business.ZpEvaluateBagBusinessService;
import com.zhiyu.zp.service.business.ZpEvaluateBusinessService;

/**
 * 
 * @author wdj
 *
 */
@RequestMapping("/web")
@Controller
public class WebEvaluateController {

	@Autowired
	private EvaluateDegreeService evaluateDegreeService;

	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;

	private ZpEvaluateDegreeInfoAdapter zpEvaluateDegreeInfoAdapter = new ZpEvaluateDegreeInfoAdapter();

	private ZpEvaluateBaseInfoAdapter zpEvaluateBaseInfoAdapter = new ZpEvaluateBaseInfoAdapter();

	@Autowired
	private ZpBagBaseInfoAdapter zpBagBaseInfoAdapter;

	@Autowired
	private ZpEvaluateBagBusinessService evaluateBagBusinessService;

	@Autowired
	private IZpEvaluateBagService zpEvaluateBagService;

	@Autowired
	private IZpEvaluateInfoService zpEvaluateInfoService;

	@Autowired
	private GradeLevelService gradeLevelService;

	@Autowired
	private IZpGradeSettingService zpGradeSettingService;

	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;

	@Autowired
	private ZpEvaluateBusinessService zpEvaluateBusinessService;

	@Autowired
	private IZpEvaluateWeightSettingService zpEvaluateWeightSettingService;

	@Autowired
	private IZpLevelSettingService zpLevelSettingService;

	@Autowired
	private IZpMarkTemplateInfoService zpMarkTemplateInfoService;

	@Autowired
	private IZpMarkSubjectService zpMarkSubjectService;

	@Autowired
	private MarkResultService markResultService;

	@Autowired
	private IZpBodyTemplateInfoService zpBodyTemplateInfoService;

	@Autowired
	private IZpBodyItemService zpBodyItemService;

	@Autowired
	private BodyResultService bodyResultService;

	@Autowired
	private IZpResultCommentService zpResultCommentService;

	@Autowired
	private IUserAuthenticationService userAuthenticationService;// 用户身份认证服务

	@Autowired
	private IUserAccessService userAccessService;// 用户访问权限服务

	@Autowired
	private ITeacherBaseInfoService teacherBaseInfoService;

	@Autowired
	private ClassTeacherRelationHandler classTeacherRelationHandler;
	
	@Autowired
	private ClassStudentRelationHandler classStudentRelationHandler;

	@Autowired
	private ISchoolBaseInfoService schoolBaseInfoService;

	@Autowired
	private IZpTermBaseInfoService zpTermBaseInfoService;
	
	@Autowired
	private EvaluateResultService evaluateResultService;

	@Autowired
	private IZpRealisticService realisticService;
	
	@Autowired
	private IZpEvaluateDegreeVersionService evaluateDegreeVersionService;
	/**
	 * 获取维度版本列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeVersion/list", method = RequestMethod.POST)
	public Map<String, Object> degreeVersion(@RequestBody WebDegreeQryDto dto) {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			ZpEvaluateDegreeVersionEntity version=new ZpEvaluateDegreeVersionEntity();
			version.setSchoolId(dto.getSchoolId().intValue());
			List<ZpEvaluateDegreeVersionEntity> list=evaluateDegreeVersionService.findByEntity(version);
			map.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(map, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 保存维度版本(新增、更新)
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeVersion/update", method = RequestMethod.POST)
	public Map<String, Object> addDegreeVersion(@RequestBody ZpEvaluateDegreeVersionEntity dto) {
		try {
			if(dto.getId()!=null){
				evaluateDegreeVersionService.update(dto);	
			}else{
				ZpEvaluateDegreeVersionEntity version=new ZpEvaluateDegreeVersionEntity();
				version.setSchoolId(dto.getSchoolId().intValue());
				version.setVersionName(dto.getVersionName());
				List<ZpEvaluateDegreeVersionEntity> list=evaluateDegreeVersionService.findByEntity(version);
				if(list!=null&&!list.isEmpty()){
					return ResponseResultFormatProcessor.resultWrapper(null, "数据保存失败，已存在该版本！", 1);	
				}
				evaluateDegreeVersionService.save(dto);
			}
			return ResponseResultFormatProcessor.resultWrapper(null, "数据保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 删除维度版本
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degreeVersion/del", method = RequestMethod.POST)
	public Map<String, Object> delDegreeVersion(@RequestBody ZpEvaluateDegreeVersionEntity dto) {
		try {
			evaluateDegreeVersionService.delete(dto.getId());
			return ResponseResultFormatProcessor.resultWrapper(null, "数据删除成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 获取主维度列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degree/list", method = RequestMethod.POST)
	public Map<String, Object> degreeList(@RequestBody WebDegreeQryDto dto) {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			List<ZpEvaluateDegreeInfoEntity> entityList = zpEvaluateDegreeInfoService.findRegular(dto.getSchoolId(),
					null, DataState.NORMAL,dto.getVersionId());
			List<BaseDegreeValidDto> list = Lists.newArrayList();
			for (ZpEvaluateDegreeInfoEntity entity : entityList) {
				BaseDegreeValidDto validDto = zpEvaluateDegreeInfoAdapter.getBaseValidInfoFromEntity(entity);
				if (dto.getDegreeId() != null) {
					if (validDto.getDegreeId().equals(dto.getDegreeId())) {
						list.add(validDto);
					}
				} else {
					if (entity.getPid() == 0L) {
						list.add(validDto);
					}
				}
			}
			map.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(map, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 添加维度的保存
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degree/add", method = RequestMethod.POST)
	public Map<String, Object> addDegree(@RequestBody WebDegreeUpdateRequestDto dto) {
		try {
			evaluateDegreeService.saveDegree(dto.getSchoolId(), dto.getDegreeType(), dto.getDegreePicId(),
					dto.getDegreeName(), dto.getDegreeDesc(),dto.getVersionId(), dto.getItems());
			return ResponseResultFormatProcessor.resultWrapper(null, "数据保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 查询学校某一个具体主维度信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degree/view", method = RequestMethod.POST)
	public Map<String, Object> viewDegree(@RequestBody WebDegreeQryDto dto) {
		try {
			return ResponseResultFormatProcessor.resultWrapper(
					evaluateDegreeService.viewDegreeDetail(dto.getSchoolId(), dto.getDegreeId()), "数据保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 更新维度（包括删除维度）
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degree/update", method = RequestMethod.POST)
	public Map<String, Object> updateDegree(@RequestBody WebDegreeUpdateRequestDto dto) {
		try {
			evaluateDegreeService.updateDegreeRecursive(dto.getSchoolId(), dto.getDegreeType(), 0L,dto.getVersionId(), dto.getDegreeId(),
					dto.getDegreeName(), dto.getDegreeDesc(), 0, dto.getStatus(), dto.getItems());
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 更新维度的生效状态
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/degree/updateUseStatus", method = RequestMethod.POST)
	public Map<String, Object> updateDegreeUseStatus(@RequestBody WebDegreeQryDto dto) {
		try {
			ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(dto.getDegreeId());
			degree.setUseable(Useable.USEABLE);
			zpEvaluateDegreeInfoService.update(degree);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取学校报告册列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluateBag/list", method = RequestMethod.POST)
	public Map<String, Object> bagList(@RequestBody WebBagQryDto dto) {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			List<ZpEvaluateBagEntity> entityList = evaluateBagBusinessService.getSchoolDisplayBags(dto.getSchoolId(),
					dto.getBagName());
			map.put("list", zpBagBaseInfoAdapter.getListFromEntityList(entityList));
			Long currentTermId = evaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			boolean isCurrentCreate = false;
			for (ZpEvaluateBagEntity entity : entityList) {
				if (entity.getTermId().equals(currentTermId)) {
					isCurrentCreate = true;
					break;
				}
			}
			map.put("isCurrentCreate", isCurrentCreate);
			return ResponseResultFormatProcessor.resultWrapper(map, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 报告册的生效
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluateBag/updateUseStatus", method = RequestMethod.POST)
	public Map<String, Object> updateBagUseStatus(@RequestBody WebBagQryDto dto) {
		try {
			ZpEvaluateBagEntity bag = zpEvaluateBagService.findById(dto.getBagId());
			bag.setUseable(Useable.USEABLE);
			zpEvaluateBagService.update(bag);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 添加一个报告册
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluateBag/add", method = RequestMethod.POST)
	public Map<String, Object> addBag(@RequestBody WebBagQryDto dto) {
		try {
			String msg=evaluateBagBusinessService.addNewBag(dto.getSchoolId(),dto.getTermId(), dto.getBagName(),dto.getVersionId(),dto.getGradeIds());
			if(!msg.equals("数据更新成功")){
				return ResponseResultFormatProcessor.resultWrapper(null, msg, 1);
			}
			return ResponseResultFormatProcessor.resultWrapper(null,msg, 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 修改一个报告册
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluateBag/update", method = RequestMethod.POST)
	public Map<String, Object> updateBag(@RequestBody WebBagQryDto dto) {
		try {
			String msg=evaluateBagBusinessService.updateOldBag(dto.getSchoolId(), dto.getBagId(), dto.getBagName()
					,dto.getVersionId(),dto.getGradeIds());
			if(!msg.equals("数据更新成功")){
				return ResponseResultFormatProcessor.resultWrapper(null, msg, 1);
			}
			return ResponseResultFormatProcessor.resultWrapper(null, msg, 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 报告册的删除
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluateBag/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteBag(@RequestBody WebBagQryDto dto) {
		try {
			ZpEvaluateBagEntity bag = zpEvaluateBagService.findById(dto.getBagId());
			bag.setDataState(DataState.DELETED);
			zpEvaluateBagService.update(bag);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 常规次评信息列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/info/list", method = RequestMethod.POST)
	public Map<String, Object> evaluateInfoList(@RequestBody WebEvaluateQryDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			StringBuilder sb = new StringBuilder();
			
			ZpEvaluateBagEntity bag = zpEvaluateBagService.findById(dto.getBagId());
			sb.append(" and dataState=? and evaluateBagId=? and degreeType=? ");
			
			List<Object> params = Lists.newArrayList();
			params.add(DataState.NORMAL);
			params.add(dto.getBagId());
			params.add(DegreeType.REGULAR);
			if(bag!=null && Useable.USEABLE.equals(bag.getUseable())){
				sb.append(" and useable=? ");
				params.add(Useable.USEABLE);
			}
			if (StringUtils.isNotBlank(dto.getEvaluateName())) {
				sb.append(" and evaluateName like ?");
				params.add("%" + dto.getEvaluateName() + "%");
			}
			if (dto.getBeginDate() != null) {
				sb.append(" and beginDate>=?");
				params.add(dto.getBeginDate());
			}
			if (dto.getEndDate() != null) {
				sb.append(" and endDate<=?");
				params.add(dto.getEndDate());
			}
			
			List<BaseInfoDto> list = zpEvaluateBaseInfoAdapter.getBaseInfoListFrom(
					zpEvaluateInfoService.findListByCondition(sb.toString(), params.toArray(), null));
			//处理次评评价进度
			if(dto.getClassId()!=null && dto.getTermId()!=null){
				//获取对应班级学生列表
				List<ClassStudentRelEntity> classStudents = classStudentRelationHandler.findTermClassStudents(dto.getTermId(), dto.getSchoolId(), dto.getClassId());
				for(BaseInfoDto baseDto:list){
					int evaluateNum = 0;
					for(ClassStudentRelEntity classStudent:classStudents){
						if(!evaluateResultService.checkIfStudentEvaluateStart(dto.getSchoolId(), Long.valueOf(classStudent.getStudentId().toString()),dto.getClassId(),baseDto.getEvaluateId(),EvaluateType.TOTAL_EVALUATED,false)){
							
						}else{
							evaluateNum++;
						}
					}
					if(evaluateNum==classStudents.size()){
						baseDto.setPercent(100);
					}else{
						baseDto.setPercent(evaluateNum*100/classStudents.size());
					}
				}
			}
			
			data.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 添加次评信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/info/add", method = RequestMethod.POST)
	public Map<String, Object> addEvaluateInfo(@RequestBody BaseAddInfo dto) {
		try {
			ZpEvaluateInfoEntity entity = new ZpEvaluateInfoEntity();
			entity.setBeginDate(dto.getBeginDate());
			entity.setDataState(DataState.NORMAL);
			entity.setDegreeType(DegreeType.getType(dto.getDegreeType()));
			entity.setEndDate(dto.getEndDate());
			entity.setEvaluateBagId(dto.getBagId());
			entity.setUseable(Useable.UNUSEABLE);
			entity.setEvaluateName(dto.getEvaluateName());
			if (dto.getDegreeType() == DegreeType.SPECIAL.getValue()) {
				entity.setDegrees(dto.getDegrees());
			}
			zpEvaluateInfoService.save(entity);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 更新次评信息的生效状态
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/info/updateUseStatus", method = RequestMethod.POST)
	public Map<String, Object> updateEvaluateUseStatus(@RequestBody BaseInfoUpdateUseableDto dto) {
		try {
			ZpEvaluateInfoEntity entity = zpEvaluateInfoService.findById(dto.getEvaluateId());
			entity.setUseable(Useable.USEABLE);
			zpEvaluateInfoService.update(entity);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 更新次评信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/info/update", method = RequestMethod.POST)
	public Map<String, Object> updateEvaluateInfo(@RequestBody BaseUpdateInfo dto) {
		try {
			ZpEvaluateInfoEntity entity = zpEvaluateInfoService.findById(dto.getEvaluateId());
			entity.setBeginDate(dto.getBeginDate());
			entity.setDegreeType(DegreeType.getType(dto.getDegreeType()));
			entity.setEndDate(dto.getEndDate());
			entity.setEvaluateName(dto.getEvaluateName());
			if (dto.getDegreeType() == DegreeType.SPECIAL.getValue()) {
				entity.setDegrees(dto.getDegrees());
			}
			zpEvaluateInfoService.update(entity);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 删除次评信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/info/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteEvaluateInfo(@RequestBody BaseInfoUpdateUseableDto dto) {
		try {
			ZpEvaluateInfoEntity entity = zpEvaluateInfoService.findById(dto.getEvaluateId());
			entity.setDataState(DataState.DELETED);
			zpEvaluateInfoService.update(entity);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 新增年级设置
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gradeLevel/add", method = RequestMethod.POST)
	public Map<String, Object> addGradeLevelInfo(@RequestBody WebGradeLevelQryDto dto) {
		try {
			List<Integer> list = Lists.newArrayList();
			String[] grades = dto.getGrades().split(",");
			for (String s : grades) {
				list.add(Integer.valueOf(s));
			}
			gradeLevelService.saveGradeSetting(dto.getLevelId(), dto.getSchoolId(), list);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取年级设置列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gradeLevel/list", method = RequestMethod.POST)
	public Map<String, Object> gradeLevelList(@RequestBody WebBaseRequestDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			List<GradeSettingDto> list = Lists.newArrayList();
			GradeBaseInfoEntity grade = new GradeBaseInfoEntity();
			grade.setSchoolId(dto.getSchoolId().intValue());
			List<GradeBaseInfoEntity> grades = gradeBaseInfoService.findByEntity(grade);
			for (GradeBaseInfoEntity entity : grades) {
				ZpGradeSettingEntity setting = new ZpGradeSettingEntity();
				setting.setGradeId(entity.getId().longValue());
				setting.setDataState(DataState.NORMAL);
				setting.setSchoolId(dto.getSchoolId());
				// 获取设置信息
				List<ZpGradeSettingEntity> settings = zpGradeSettingService.findByEntity(setting);
				if (settings != null && !settings.isEmpty()) {
					GradeSettingDto setDto = new GradeSettingDto();
					setDto.setGradeId(entity.getId());
					setDto.setGradeName(entity.getName());
					setDto.setGradeLevelId(settings.get(0).getId());
					setDto.setLevelId(settings.get(0).getLevelId().getValue());
					setDto.setLevelName(settings.get(0).getLevelId().getName());
					list.add(setDto);
				}
			}
			data.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 更新年级设置信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gradeLevel/update", method = RequestMethod.POST)
	public Map<String, Object> updateGradeLevelInfo(@RequestBody UpdateSettingDto dto) {
		try {
			ZpGradeSettingEntity setting = zpGradeSettingService.findById(dto.getGradeLevelId());
			setting.setUpdateTime(new Date());
			setting.setLevelId(GradeLevel.getType(dto.getLevelId()));
			zpGradeSettingService.update(setting);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 删除年级设置
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gradeLevel/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteGradeLevelInfo(@RequestBody UpdateSettingDto dto) {
		try {
			ZpGradeSettingEntity setting = zpGradeSettingService.findById(dto.getGradeLevelId());
			setting.setUpdateTime(new Date());
			setting.setDataState(DataState.DELETED);
			zpGradeSettingService.update(setting);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 次评星级设置列表
	 * 
	 * @param schoolId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/weight/list", method = RequestMethod.POST)
	public Map<String, Object> timeEvaluateSettingList(@RequestBody WebBaseRequestDto dto) {
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			// 获取对应学期内评价包涉及到的主常规维度
			List<BaseStarInfoDto> dtos = evaluateDegreeService
					.getRegularTimeEvaluateMajorDegrees(dto.getSchoolId(), dto.getTermId());
			data.put("list", dtos);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 查询学校某一个具体维度下的维度（目录级）次评设置的信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/weight/view", method = RequestMethod.POST)
	public Map<String, Object> viewDegreeSetting(@RequestBody WebDegreeQryDto dto) {
		try {
			List<ZpEvaluateDegreeInfoEntity> childDegrees = zpEvaluateDegreeInfoService.findByPid(dto.getDegreeId());
			//获取学期下的总评任务列表
			List<ZpEvaluateBagEntity> bagList = evaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), dto.getTermId());
			//查找该维度下的总评id
			Long bagId=0L;
			for(ZpEvaluateBagEntity bag:bagList){
				String degreesStrInThatTerm = bag.getTopDegrees();
				String[] degreeIds = degreesStrInThatTerm.split(",");
				for (String degreeId : degreeIds) {
					ZpEvaluateDegreeInfoEntity degree = zpEvaluateDegreeInfoService.findById(Long.valueOf(degreeId));
					if(dto.getDegreeId()==degree.getId()){
						bagId=bag.getId();
						break;
					}
				}
			}
			List<WeightViewDto> views = Lists.newArrayList();
			for (ZpEvaluateDegreeInfoEntity degree : childDegrees) {
				WeightViewDto view = new WeightViewDto();
				view.setDegreeId(degree.getId());
				view.setDegreeName(degree.getDname());
				view.setSchoolId(dto.getSchoolId());
				view.setBagId(bagId);
				List<ZpEvaluateWeightSettingEntity> setting = zpEvaluateWeightSettingService
						.findRegularTimeEvaluateWeightSetting(bagId, degree.getId());
				// 按最大值排序
				if (!setting.isEmpty()) {
					Collections.sort(setting, new ZpEvaluateWeightSettingResValueComparatorBean());
				}
				view.setItems(setting);
				views.add(view);
			}
			return ResponseResultFormatProcessor.resultWrapper(views, "数据保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 次评设置的更新
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/weight/update", method = RequestMethod.POST)
	public Map<String, Object> updateDegreeSetting(@RequestBody List<WeightUpdateDto> views) {
		try {
			zpEvaluateBusinessService.updateWeightDegreeSetting(views, DegreeType.REGULAR, EvaluateType.TIME_EVALUATE);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 评估总评权重列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/gather/weight/list", method = RequestMethod.POST)
	public Map<String, Object> eventualWeightList(@RequestBody WebBagQryDto dto) {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			List<ZpEvaluateBagEntity> entityList = evaluateBagBusinessService.getSchoolDisplayBags(dto.getSchoolId(),
					dto.getBagName());
			List<BagBaseSetInfo> list = Lists.newArrayList();
			for (BagBaseInfo base : zpBagBaseInfoAdapter.getListFromEntityList(entityList)) {
				BagBaseSetInfo set = new BagBaseSetInfo();
				BeanUtils.copyProperties(base, set);
				set.setCanSet(
						!zpEvaluateBusinessService.checkIfGatherEvaluateStart(dto.getSchoolId(), set.getTermId(),base.getBagId()));
				list.add(set);
			}
			map.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(map, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 查询学校某一个具体报告册总评设置的信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/gather/weight/view", method = RequestMethod.POST)
	public Map<String, Object> viewGatherSetting(@RequestBody WebBagQryDto dto) {
		try {
			WeightViewDto view = new WeightViewDto();
			view.setSchoolId(dto.getSchoolId());
			view.setBagId(dto.getBagId());
			List<ZpEvaluateWeightSettingEntity> setting = zpEvaluateWeightSettingService
					.findRegularGatherEvaluateWeightSetting(dto.getBagId());
			// 按最大值排序
			if (!setting.isEmpty()) {
				Collections.sort(setting, new ZpEvaluateWeightSettingResValueComparatorBean());
			}
			view.setItems(setting);
			return ResponseResultFormatProcessor.resultWrapper(view, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 总评设置的更新
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/gather/weight/update", method = RequestMethod.POST)
	public Map<String, Object> updateGatherSetting(@RequestBody WeightUpdateDto view) {
		try {
			List<WeightUpdateDto> list = Lists.newArrayList();
			list.add(view);
			zpEvaluateBusinessService.updateWeightDegreeSetting(list, DegreeType.REGULAR, EvaluateType.TOTAL_EVALUATED);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 评估最终等级列表
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/eventual/level/list", method = RequestMethod.POST)
	public Map<String, Object> eventualLevelList(@RequestBody WebBaseRequestDto dto) {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			List<EventualLevelResultDto> list = Lists.newArrayList();
			for (GradeLevel gradeLevel : GradeLevel.values()) {
				EventualLevelResultDto result = new EventualLevelResultDto();
				result.setLevelId(gradeLevel.getValue().longValue());
				result.setLevelName(gradeLevel.getName());
				// 获取对应等级设置信息
				List<ZpLevelSettingEntity> sets = zpLevelSettingService.findByLevelId(dto.getSchoolId(),
						gradeLevel.getValue().longValue());
				Collections.sort(sets, new ZpEvaluateLevelSettingResValueComparatorBean());
				List<EventualLevelResultDto.LevelResult> items = Lists.newArrayList();
				for (ZpLevelSettingEntity e : sets) {
					EventualLevelResultDto.LevelResult lr = new EventualLevelResultDto.LevelResult();
					lr.setId(e.getId());
					lr.setMaxValue(e.getMaxValue());
					lr.setMinValue(e.getMinValue());
					lr.setEvaluateLevelId(e.getEvaluateLevelType().getValue().longValue());
					lr.setEvaluateLevelName(e.getEvaluateLevelType().getName());
					items.add(lr);
				}
				result.setItems(items);
				list.add(result);
			}
			map.put("list", list);
			return ResponseResultFormatProcessor.resultWrapper(map, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 等级设置的更新
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/eventual/level/update", method = RequestMethod.POST)
	public Map<String, Object> updateEventualSetting(@RequestBody EventualLevelResultUpdateDto updateDto) {
		try {
			zpEvaluateBusinessService.updateEventualLevelSetting(updateDto);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取对应的枚举类数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/enumMap", method = RequestMethod.POST)
	public Map<String, Object> findEnumCodeMap() {
		try {
			Map<String, Object> map = Maps.newConcurrentMap();
			map.put("gradeLevelItem", GradeLevel.listData());
			map.put("useableItem", Useable.listData());
			map.put("evaluateLevelItem", EvaluateLevelType.listData());
			map.put("markTypeItem", MarkType.listData());
			return ResponseResultFormatProcessor.resultWrapper(map, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}

	/**
	 * 获取成绩导入模板
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/template/mark/{schoolId}", method = RequestMethod.POST)
	public Map<String, Object> getMarkTemplate(HttpServletRequest request, @PathVariable Long schoolId,
			HttpServletResponse response) {
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			String segment = "markImportExcel/" + schoolId + "/" + "base.xls";
			PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(
					PictureInfoBeanFactory.newInstance(UploadFileType.CLOUD_BASE_IMPORT_TEMPLATE_STUDENT));
			map.put("url", processor.getFileService().getFileWebPathBySegment(segment));
			List<String> titles = Lists.newArrayList();
			titles.add("学籍号");
			titles.add("姓名");
			titles.add("年级");
			titles.add("班级");
			// titles.add("名称");
			titles.add("类型");
			ZpMarkTemplateInfoEntity templateInfo = zpMarkTemplateInfoService.findBySchool(schoolId);
			List<String> englishFieldCodes = Lists.newArrayList();
			if (templateInfo != null) {
				String[] arr = templateInfo.getSubjects().split(",");
				for (String id : arr) {
					ZpMarkSubjectEntity subject = zpMarkSubjectService.findById(Long.valueOf(id));
					englishFieldCodes.add(subject.getEngCode());
					titles.add(subject.getSubjectName());
				}
				map.put("fields", englishFieldCodes);
				map.put("titles", titles);
			}
			return ResponseResultFormatProcessor.resultWrapper(map, "模板获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "模板获取失败", 1);
		}
	}

	/**
	 * 成绩数据的导入提交
	 * 
	 * @param request
	 * @param dto
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mark/commit", method = RequestMethod.POST)
	public Map<String, Object> markCommitByBatch(HttpServletRequest request, @RequestBody WebMarkImportDto dto,
			HttpServletResponse response) {
		Map<String, Object> errorResultMap = Maps.newConcurrentMap();
		try {
			Long currentTermId = evaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			for (Map<String, Object> map : dto.getData()) {
				markResultService.saveByBatch(map, MarkType.getType(dto.getMarkTypeValue()), dto.getSchoolId(),
						currentTermId,errorResultMap);
			}
			if(!errorResultMap.keySet().isEmpty()){
				return ResponseResultFormatProcessor.resultWrapper(errorResultMap.keySet(), "这些学生数据提交失败", 1);
			}
			return ResponseResultFormatProcessor.resultWrapper(null, "提交成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(errorResultMap.keySet(), "提交失败", 1);
		}

	}

	/**
	 * 查询学校对应学期下的对应班级学生成绩列表
	 * @param request
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mark/result/query", method = RequestMethod.POST)
	public Map<String, Object> queryMarkResult(HttpServletRequest request, @RequestBody WebMarkQryDto dto) {
		ZpMarkResultEntity result = new ZpMarkResultEntity();
		result.setCreateTime(null);
		result.setUpdateTime(null);
		BeanUtils.copyProperties(dto, result);
		try {
			List<MarkResultContentDto> contents = markResultService.getMarkResults(dto.getSchoolId(), result);
			return ResponseResultFormatProcessor.resultWrapper(contents, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}

	}

	/**
	 * 获取体质导入模板
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/template/body/{schoolId}", method = RequestMethod.POST)
	public Map<String, Object> getBodyTemplate(HttpServletRequest request, @PathVariable Long schoolId,
			HttpServletResponse response) {
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			String segment = "bodyImportExcel/" + schoolId + "/" + "base.xls";
			PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(
					PictureInfoBeanFactory.newInstance(UploadFileType.CLOUD_BASE_IMPORT_TEMPLATE_STUDENT));
			map.put("url", processor.getFileService().getFileWebPathBySegment(segment));
			List<String> titles = Lists.newArrayList();
			titles.add("学籍号");
			titles.add("姓名");
			titles.add("年级");
			titles.add("班级");
			ZpBodyTemplateInfoEntity templateInfo = zpBodyTemplateInfoService.findBySchool(schoolId);
			List<String> englishFieldCodes = Lists.newArrayList();
			if (templateInfo != null) {
				String[] arr = templateInfo.getItems().split(",");
				for (String id : arr) {
					ZpBodyItemEntity subject = zpBodyItemService.findById(Long.valueOf(id));
					englishFieldCodes.add(subject.getEngCode());
					titles.add(subject.getItemName());
				}
				map.put("fields", englishFieldCodes);
				map.put("titles", titles);
			}
			return ResponseResultFormatProcessor.resultWrapper(map, "模板获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "模板获取失败", 1);
		}
	}

	/**
	 * 体质数据的导入提交
	 * 
	 * @param request
	 * @param dto
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/body/commit", method = RequestMethod.POST)
	public Map<String, Object> bodyCommitByBatch(HttpServletRequest request, @RequestBody WebBodyImportDto dto,
			HttpServletResponse response) {
		try {
			Map<String, Object> errorResultMap = Maps.newConcurrentMap();
			dto.setMarkTypeValue(MarkType.LAST.getValue());
			Long currentTermId = evaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			for (Map<String, Object> map : dto.getData()) {
				bodyResultService.saveByBatch(map, MarkType.getType(dto.getMarkTypeValue()), dto.getSchoolId(),
						currentTermId,errorResultMap);
			}
			if(!errorResultMap.keySet().isEmpty()){
				return ResponseResultFormatProcessor.resultWrapper(errorResultMap.keySet(), "这些学生数据提交失败", 1);
			}
			return ResponseResultFormatProcessor.resultWrapper(null, "提交成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "提交失败", 1);
		}

	}

	/**
	 * 体质结果查询列表
	 * 
	 * @param request
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/body/result/query", method = RequestMethod.POST)
	public Map<String, Object> queryBodyResult(HttpServletRequest request, @RequestBody WebBodyQryDto dto) {
		ZpBodyResultEntity result = new ZpBodyResultEntity();
		result.setCreateTime(null);
		result.setUpdateTime(null);
		BeanUtils.copyProperties(dto, result);
		try {
			List<BodyResultContentDto> contents = bodyResultService.getBodyResults(dto.getSchoolId(), result);
			return ResponseResultFormatProcessor.resultWrapper(contents, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}

	}

	/**
	 * 查询学校下管理的评语列表
	 * 
	 * @param schoolId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/result-comment/query/{schoolId}", method = RequestMethod.POST)
	public Map<String, Object> queryCommentList(@PathVariable Long schoolId) {
		try {
			return ResponseResultFormatProcessor.resultWrapper(zpResultCommentService.findAll(schoolId), "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/result-comment/fuzzyquery", method = RequestMethod.POST)
	public Map<String, Object> fuzzyQueryCommentList(@RequestBody WebCommentFuzzyQryDto qryDto) {
		try {
			return ResponseResultFormatProcessor.resultWrapper(zpResultCommentService.findByKeywords(qryDto.getKeywords(),qryDto.getSchoolId()), "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}
	}

	/**
	 * 评语信息的更新
	 * 
	 * @param qry
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/result-comment/update", method = RequestMethod.POST)
	public Map<String, Object> saveOrUpdateComment(@RequestBody WebCommentQryDto qry) {
		try {
			ZpResultCommentEntity entity = null;
			if (qry.getId() == null) {
				entity = new ZpResultCommentEntity();
				entity.setRemark(qry.getRemark());
				entity.setSchoolId(qry.getSchoolId());
				zpResultCommentService.save(entity);
			} else {
				entity = zpResultCommentService.findById(qry.getId());
				entity.setRemark(qry.getRemark());
				zpResultCommentService.update(entity);
			}
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据更新失败", 1);
		}
	}

	/**
	 * 查询学校下管理的评语删除
	 * 
	 * @param schoolId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/result-comment/delete/{id}", method = RequestMethod.POST)
	public Map<String, Object> deleteCommentInfo(@PathVariable Long id) {
		try {
			zpResultCommentService.delete(id);
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获删除成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据删除失败", 1);
		}
	}

	/**
	 * 登录
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(@Valid @RequestBody UserLoginDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserRole(UserRole.TEACHER);
			UserAuthenticationEntity entity = new UserAuthenticationEntity();
			entity.setLoginAccount(dto.getUserAccount());
			entity.setLoginPassword(Md5.md5(dto.getPassword()));
			List<UserAuthenticationEntity> userList = userAuthenticationService.findByEntity(entity);
			if (userList != null && !userList.isEmpty()) {
				userInfo.setUserId(userList.get(0).getUserId());
				UserAccessEntity uae = new UserAccessEntity();
				uae.setUserId(userInfo.getUserId());
				List<UserAccessEntity> userAccessList = userAccessService.findByEntity(uae);// 获取用户的权限列表
				for (UserAccessEntity e : userAccessList) {
					if (e.getAuthorityId().intValue() == 3) {// 德育处
						userInfo.setUserRole(UserRole.DYC);
					}
				}
				TeacherBaseInfoEntity teacher = teacherBaseInfoService
						.findObjectById(Integer.valueOf(userInfo.getUserId()));
				userInfo.setTeacherName(teacher.getName());
				userInfo.setSchoolId(teacher.getSchoolId().longValue());
				userInfo.setSchoolName(
						schoolBaseInfoService.findObjectById(userInfo.getSchoolId().intValue()).getSchoolName());
				Long termId = evaluateBagBusinessService.findSearchTermId(userInfo.getSchoolId(), null);
				userInfo.setTermId(termId);
				if (userInfo.getUserRole() == UserRole.TEACHER) {
					ClassTeacherRelEntity ctr = classTeacherRelationHandler
							.findCurrentFocusClassTeacherRel(Integer.valueOf(userInfo.getUserId()));
					if(ctr!=null){
						userInfo.setClassId(ctr.getClassId().longValue());
						userInfo.setGradeId(classBaseInfoService.findObjectById(userInfo.getClassId().intValue())
								.getGradeId().longValue());
						userInfo.setClassName(
								classBaseInfoService.findObjectById(userInfo.getClassId().intValue()).getName());
						userInfo.setGradeName(
								gradeBaseInfoService.findObjectById(userInfo.getGradeId().intValue()).getName());
					}else{
						return ResponseResultFormatProcessor.resultWrapper(null, "登录失败，该教师并未有关注班级，请使用该账号登录手机app", 1);
					}
				}
				return ResponseResultFormatProcessor.resultWrapper(userInfo, "登录成功", 0);
			} else {
				return ResponseResultFormatProcessor.resultWrapper(null, "登录失败", 1);
			}
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "登录失败", 1);
		}
	}

	/**
	 * 查询下学期信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/find-next-term",method = RequestMethod.POST)
	public Map<String, Object> findNextTermInfo(@RequestBody WebBaseRequestDto dto) {
		try {
			Long currentTermId = evaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
			ZpTermBaseInfoEntity t = new ZpTermBaseInfoEntity();
			t.setCreateTime(null);
			t.setUpdateTime(null);
			t.setTermId(currentTermId);
			List<ZpTermBaseInfoEntity> list = zpTermBaseInfoService.findByEntity(t);
			if(!list.isEmpty()){
				return ResponseResultFormatProcessor.resultWrapper(list.get(0), "查询成功", 0);
			}else{
				return ResponseResultFormatProcessor.resultWrapper(null, "暂时未添加该信息", 1);
			}
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "查询失败", 1);
		}
	}

	/**
	 * 保存下学期信息
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save-next-term",method = RequestMethod.POST)
	public Map<String, Object> saveNextTermInfo(@RequestBody WebTermQryDto dto) {
		try {
			ZpTermBaseInfoEntity t = null;
			if(dto.getId()!=null){
				t = zpTermBaseInfoService.findById(dto.getId());
			}else{
				Long currentTermId = evaluateBagBusinessService.findSearchTermId(dto.getSchoolId(), null);
				t = new ZpTermBaseInfoEntity();
				t.setTermId(currentTermId);
			}
			t.setNextRegDate(dto.getNextRegDate());
			t.setNextStartDate(dto.getNextStartDate());
			zpTermBaseInfoService.saveOrUpdate(t);
			return ResponseResultFormatProcessor.resultWrapper(null, "保存成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "保存失败", 1);
		}
	}

	/**
	 * 获取综合评语的学生列表情况
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
			//获取该班级下的总评id
			ClassBaseInfoEntity classBase=classBaseInfoService.findObjectById(dto.getClassId().intValue());
			GradeBaseInfoEntity grade=gradeBaseInfoService.findObjectById(classBase.getGradeId());
			List<ZpEvaluateBagEntity> bagList=evaluateBagBusinessService.getCurrentSchoolBag(dto.getSchoolId(), null);
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
			data.put("list", zpEvaluateBusinessService.getClassStudentEvaluateWebCommentList(dto.getSchoolId(), null, dto.getClassId(),bagId));
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 获取班级学生写实记录列表
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClassRealistic")
	public Map<String, Object> getClassRealistic(HttpServletRequest request,
			@Valid @RequestBody ZpRealisticEntity dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			ZpRealisticEntity entity=new ZpRealisticEntity();
			entity.setTermId(dto.getTermId());
			entity.setClassId(dto.getClassId());
			entity.setEvaluateId(dto.getEvaluateId());
			if(dto.getStudentId()!=null){
				entity.setStudentId(dto.getStudentId());
			}
			List<ZpRealisticEntity> entityList=realisticService.findByEntity(entity);
			PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(PictureInfoBeanFactory.newInstance(UploadFileType.CLASS_ACTIVITY_SPACE));
			for(ZpRealisticEntity e:entityList){
				e.setRealisticUrl(processor.getFileService().getFileWebPathBySegment(e.getRealisticUrl()));
			}
			data.put("list", entityList);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据查询成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 新增学生写实记录
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRealistic")
	public Map<String, Object> addRealistic(HttpServletRequest request,
			@Valid @RequestBody ZpRealisticEntity dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			ZpRealisticEntity entity=new ZpRealisticEntity();
			entity.setTermId(dto.getTermId());
			entity.setClassId(dto.getClassId());
			entity.setEvaluateId(dto.getEvaluateId());
			entity.setStudentId(dto.getStudentId());
			entity.setRealisticType(dto.getRealisticType());
			entity.setRealisticName(dto.getRealisticName());
			entity.setRealisticDec(dto.getRealisticDec());
			entity.setRealisticScore(dto.getRealisticScore());
			entity.setRealisticUrl(dto.getRealisticUrl());
			entity.setRealisticNum(dto.getRealisticNum());
			entity.setRealisticFileName(dto.getRealisticFileName());
			entity.setRealisticDate(dto.getRealisticDate());
			entity.setRealisticUserType(dto.getRealisticUserType());
			entity.setRealisticUserId(dto.getRealisticUserId());
			realisticService.save(entity);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据添加成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 更新学生写实记录
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRealistic/{id}")
	public Map<String, Object> updateRealistic(HttpServletRequest request,
			@Valid @RequestBody ZpRealisticEntity dto, BindingResult bindingResult,@PathVariable Long id) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			ZpRealisticEntity entity=realisticService.findById(id);
			if(dto.getTermId()!=null){
				entity.setTermId(dto.getTermId());
			}
			if(dto.getClassId()!=null){
				entity.setClassId(dto.getClassId());
			}
			if(dto.getEvaluateId()!=null){
				entity.setEvaluateId(dto.getEvaluateId());
			}
			if(dto.getStudentId()!=null){
				entity.setStudentId(dto.getStudentId());
			}
			if(dto.getRealisticType()!=null){
				entity.setRealisticType(dto.getRealisticType());
			}
            if(dto.getRealisticName()!=null){
            	entity.setRealisticName(dto.getRealisticName());
			}
            if(dto.getRealisticDec()!=null){
            	entity.setRealisticDec(dto.getRealisticDec());
			}
            if(dto.getRealisticScore()!=null){
            	entity.setRealisticScore(dto.getRealisticScore());
			}
            if(dto.getRealisticUrl()!=null){
            	ZpRealisticEntity r=realisticService.findById(id);
    			PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(PictureInfoBeanFactory.newInstance(UploadFileType.CLASS_ACTIVITY_SPACE));
    			processor.getFileService().deleteFile(r.getRealisticUrl());
    			
            	entity.setRealisticUrl(dto.getRealisticUrl());
			}
            if(dto.getRealisticFileName()!=null){
              entity.setRealisticNum(dto.getRealisticNum());
            }
            if(dto.getRealisticFileName()!=null){
            	entity.setRealisticFileName(dto.getRealisticFileName());
            }
            if(dto.getRealisticDate()!=null){
            	entity.setRealisticDate(dto.getRealisticDate());
			}
            if(dto.getRealisticUserType()!=null){
            	entity.setRealisticUserType(dto.getRealisticUserType());
			}
            if(dto.getRealisticUserId()!=null){
            	entity.setRealisticUserId(dto.getRealisticUserId());
			}
			realisticService.update(entity);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据更新成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 删除学生写实记录
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delRealistic/{id}")
	public Map<String, Object> delRealistic(HttpServletRequest request,
			@Valid @RequestBody ZpRealisticEntity dto, BindingResult bindingResult,@PathVariable Long id) {
		if (bindingResult.hasErrors()) {
			return ResponseResultFormatProcessor.resultWrapper(null, "请求参数格式有误", 1);
		}
		try {
			Map<String, Object> data = Maps.newConcurrentMap();
			ZpRealisticEntity r=realisticService.findById(id);
			PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(PictureInfoBeanFactory.newInstance(UploadFileType.CLASS_ACTIVITY_SPACE));
			processor.getFileService().deleteFile(r.getRealisticUrl());
			
			realisticService.delete(id);
			return ResponseResultFormatProcessor.resultWrapper(data, "数据删除成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, e.getMessage(), 1);
		}
	}
	/**
	 * 上传学生写实记录文件
	 */
    @ResponseBody
	@RequestMapping("/uploadRealisticFile/{schoolId}")
	public void uploadRuleAssignFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") CommonsMultipartFile file,@PathVariable Integer schoolId) throws IOException {
		PrintWriter out;
		Map<String, Object> result = Maps.newConcurrentMap();
		ObjectMapper mapper = new ObjectMapper();
		if (file.getSize() > 0) {
				try {
					String defaultSuffix = request.getParameter("defaultSuffix");
					PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(
							PictureInfoBeanFactory.newInstance(UploadFileType.CLASS_ACTIVITY_SPACE),defaultSuffix,"picDirectory");
					String segment = processor.getPicUploadHandler().uploadPicByWeb(schoolId, file.getInputStream());
					// 返回相关数据
					result.put("msg", "上传成功");
					result.put("path", segment);
					result.put("fileName", file.getOriginalFilename());
					result.put("fileSize", file.getSize());
					result.put("uploadedImgUrl", processor.getFileService().getFileWebPathBySegment(segment));
					result.put("code", 0);
				} catch (Exception e) {
					result.put("msg", "上传失败");
					result.put("code", 1);
				}
			}
		out = response.getWriter();
		out.println(mapper.writeValueAsString(result));
	}
    /**
	 * 评价状态统计查询列表
	 * 
	 * @param request
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentEvaluate/resultStatus", method = RequestMethod.POST)
	public Map<String, Object> resultStatus(HttpServletRequest request, @RequestBody ZpEvaluateResultEntity dto) {
		try {
			List<StudentBaseInfoEntity> csrList=classStudentRelationHandler.findStudentsByClass(dto.getSchoolId().intValue(), dto.getClassId().intValue());
			StudentResultStatusDto resultDto=evaluateResultService.findStudentResultStatus(csrList,dto.getSchoolId(),dto.getClassId(), dto.getEvaluateId());
			return ResponseResultFormatProcessor.resultWrapper(resultDto, "数据获取成功", 0);
		} catch (Exception e) {
			return ResponseResultFormatProcessor.resultWrapper(null, "数据获取失败", 1);
		}

	}
}
