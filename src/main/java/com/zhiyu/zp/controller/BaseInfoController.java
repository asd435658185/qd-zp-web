package com.zhiyu.zp.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.adapter.TeacherUserAdapter;
import com.zhiyu.baseplatform.dto.TeacherUserDto;
import com.zhiyu.baseplatform.entity.CityEntity;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.ClassTeacherRelEntity;
import com.zhiyu.baseplatform.entity.DistrictionEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ParentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ProvinceEntity;
import com.zhiyu.baseplatform.entity.SchoolBaseInfoEntity;
import com.zhiyu.baseplatform.entity.SchoolTypeEntity;
import com.zhiyu.baseplatform.entity.StudentAuthenticationEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.SysOparateManager;
import com.zhiyu.baseplatform.entity.TeacherBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.entity.UserAccessEntity;
import com.zhiyu.baseplatform.entity.UserAuthenticationEntity;
import com.zhiyu.baseplatform.service.ICityService;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IClassStudentRelService;
import com.zhiyu.baseplatform.service.IClassTeacherRelService;
import com.zhiyu.baseplatform.service.IDistrictionService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.IParentBaseInfoService;
import com.zhiyu.baseplatform.service.IProvinceService;
import com.zhiyu.baseplatform.service.ISchoolBaseInfoService;
import com.zhiyu.baseplatform.service.ISchoolTypeService;
import com.zhiyu.baseplatform.service.IStudentAuthenticationService;
import com.zhiyu.baseplatform.service.IStudentBaseInfoService;
import com.zhiyu.baseplatform.service.ISysOparateManagerService;
import com.zhiyu.baseplatform.service.ITeacherBaseInfoService;
import com.zhiyu.baseplatform.service.ITermBaseInfoService;
import com.zhiyu.baseplatform.service.IUserAccessService;
import com.zhiyu.baseplatform.service.IUserAuthenticationService;
import com.zhiyu.baseplatform.service.identifycode.UuidIdentifyCodeGenerator;
import com.zhiyu.baseplatform.util.Md5;
import com.zhiyu.zp.dto.OrgBaseInfoDto;
import com.zhiyu.zp.dto.SchoolBagDegreeDto;
import com.zhiyu.zp.dto.SchoolBagDegreeDto.StudentBagDegreeDto;
import com.zhiyu.zp.dto.SchoolBagDto;
import com.zhiyu.zp.dto.SchoolBagDto.SchoolBagDegreesDto;
import com.zhiyu.zp.dto.SongdaClassStuDto;
import com.zhiyu.zp.dto.SongdaClassTeacherDto;
import com.zhiyu.zp.dto.SongdaLoginUserInfo;
import com.zhiyu.zp.dto.SongdaSchoolClassDto;
import com.zhiyu.zp.dto.SongdaSchoolDto;
import com.zhiyu.zp.dto.SongdaSchoolGradeDto;
import com.zhiyu.zp.dto.SongdaSchoolManagerDto;
import com.zhiyu.zp.dto.SongdaSchoolStudentDto;
import com.zhiyu.zp.dto.SongdaSchoolTeacherDto;
import com.zhiyu.zp.utils.SongdaInterfaceDataUtil;

/**
 * 基础数据同步、单点登录
 * 
 * @author wdj
 *
 */
@RequestMapping("/qdzp")
@Controller
public class BaseInfoController {
	@Autowired
	private ISchoolBaseInfoService schoolBaseInfoService; 
	
	@Autowired
	private IProvinceService provinceService;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IDistrictionService districtionService;
	
	@Autowired
	private ISysOparateManagerService sysOparateManagerService;
	
	@Autowired
	private ITeacherBaseInfoService teacherBaseInfoService;
	
	@Autowired
	private IUserAccessService userAccessService;

	@Autowired
	private IUserAuthenticationService userAuthenticationService;
	
	@Autowired
	private IStudentBaseInfoService studentBaseInfoService; 
	
	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;
	
	@Autowired
	private IClassBaseInfoService classBaseInfoService;
	
	@Autowired
	private ITermBaseInfoService termBaseInfoService;
	
	@Autowired
	private IClassStudentRelService classStudentRelService;
	
	@Autowired
	private IClassTeacherRelService classTeacherRelService;
	
	@Autowired
	private TeacherUserAdapter teacherUserAdapter;
	
	@Autowired
	private IParentBaseInfoService parentBaseInfoService;
	
	@Autowired
	private IStudentAuthenticationService studentAuthenticationService;
	
	@Autowired
	private ISchoolTypeService schoolTypeService;
	/**
	 * 通过token获取用户信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserInfo")
	public Map<String, Object> getUserInfo(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 String accessToken = request.getParameter("accessToken");
			 SongdaLoginUserInfo loginUser = SongdaInterfaceDataUtil.loginUser(accessToken);
			 SysOparateManager sys = new SysOparateManager();
			 sys.setName(loginUser.getUserId());
			 List<SysOparateManager> sysList = sysOparateManagerService.findByEntity(sys);
			 SysOparateManager sysOperator = null;
			 if ( sysList != null && !sysList.isEmpty()) {//存在超级管理员
				  sysOperator = sysList.get(0);
			 }
			  if(sysOperator!=null){ //如果是超级管理员
				  result.put("code", 0);
				  result.put("type", 5);//超级管理员
				  result.put("sysOperatorData", sysOperator);
				  if(sysOperator.getSchoolId()!=null){
					 SchoolBaseInfoEntity school=schoolBaseInfoService.findObjectById(sysOperator.getSchoolId());
					 result.put("schoolInfo", school);
				  }
				  result.put("msg", "登录成功");
			  }else{//如果不是操作人员，这里就是教师等相关人员了
				//先查找老师存在不存在
				UserAuthenticationEntity teacherEntity = new UserAuthenticationEntity();
				teacherEntity.setLoginAccount(loginUser.getUserNo());
				List<UserAuthenticationEntity> commentList = userAuthenticationService.findByEntity(teacherEntity);
				if ( commentList != null && !commentList.isEmpty()) {//存在
					TeacherBaseInfoEntity teacher = teacherBaseInfoService
							.findObjectById(Integer.valueOf(commentList.get(0).getUserId()));
					TeacherUserDto teacherUserInfo = teacherUserAdapter.getData(teacher);
					//查找是不是也是家长
					ParentBaseInfoEntity parentEntity = new ParentBaseInfoEntity();
					parentEntity.setParentName(loginUser.getUserId());
					List<ParentBaseInfoEntity> parentInfos = parentBaseInfoService.findByEntity(parentEntity);
					if (parentInfos != null && !parentInfos.isEmpty()) {//存在
						result.put("code", 0);
						result.put("type", 2);//教师、家长
						result.put("teacherData", teacherUserInfo);
						if(teacherUserInfo.getSchoolId()!=null){
							 SchoolBaseInfoEntity school=schoolBaseInfoService.findObjectById(teacherUserInfo.getSchoolId());
							 result.put("schoolInfo", school);
						}
						result.put("parentData", parentInfos.get(0));
						result.put("msg", "登录成功");
					}else{
						result.put("code", 0);
						result.put("type", 1);//教师
						result.put("teacherData", teacherUserInfo);
						if(teacherUserInfo.getSchoolId()!=null){
							 SchoolBaseInfoEntity school=schoolBaseInfoService.findObjectById(teacherUserInfo.getSchoolId());
							 result.put("schoolInfo", school);
						}
						result.put("msg", "登录成功");
					}
				} else {
					//再查找家长存不存在
					ParentBaseInfoEntity parentEntity = new ParentBaseInfoEntity();
					parentEntity.setParentName(loginUser.getUserId());
					List<ParentBaseInfoEntity> parentInfos = parentBaseInfoService.findByEntity(parentEntity);
					if (parentInfos != null && !parentInfos.isEmpty()) {//存在
						result.put("code", 0);
						result.put("type", 3);//家长
						result.put("parentData", parentInfos.get(0));
						result.put("msg", "登录成功");
					} else {
						//最后查找学生存不存在
						StudentAuthenticationEntity s = new StudentAuthenticationEntity();
						s.setLoginAccount(loginUser.getUserId());
						List<StudentAuthenticationEntity> studentUserInfoList = studentAuthenticationService.findByEntity(s);
						if (studentUserInfoList != null && !studentUserInfoList.isEmpty()) {
							result.put("code", 0);
							result.put("type", 4);//学生
							result.put("studentData", studentUserInfoList.get(0));
							if(studentUserInfoList.get(0).getSchoolId()!=null){
								 SchoolBaseInfoEntity school=schoolBaseInfoService.findObjectById(studentUserInfoList.get(0).getSchoolId());
								 result.put("schoolInfo", school);
							}
							result.put("msg", "登录成功");
						} else {
							result.put("code", 1);
							result.put("type", 0);
							result.put("msg", "登录失败，用户名或密码有误");
						}
					}
				}
			  }
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "登录失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 获取学校类型列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchoolTypeList")
	public Map<String, Object> getSchoolTypeList(HttpServletRequest request,HttpServletResponse response
			,@RequestBody SchoolTypeEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 List<SchoolTypeEntity> schoolTypeList=schoolTypeService.findAll();
			 result.put("code", 0);
			 result.put("data", schoolTypeList);
			 result.put("msg", "查询成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "查询失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 获取教育局学校列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchoolList")
	public Map<String, Object> getSchoolList(HttpServletRequest request,HttpServletResponse response
			,@RequestBody SchoolBaseInfoEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 SchoolBaseInfoEntity school=new SchoolBaseInfoEntity();
			 school.setParentId(dto.getSchoolId());
			 List<SchoolBaseInfoEntity> schoolList=schoolBaseInfoService.findByEntity(school);
			 result.put("code", 0);
			 result.put("data", schoolList);
			 result.put("msg", "查询成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "查询失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 获取教育局学校综评任务列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchoolBagEvaluateList")
	public Map<String, Object> getSchoolBagEvaluateList(HttpServletRequest request,HttpServletResponse response
			,@RequestBody SchoolBaseInfoEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 String sql="CALL zpSchoolEvaluateData("+dto.getSchoolId()+")";
			 List data=schoolBaseInfoService.findBySql(sql);
			 result.put("code", 0);
			 result.put("data", data);
			 result.put("msg", "查询成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "查询失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 获取教育局学校综评等级列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchoolBagLevelList")
	public Map<String, Object> getSchoolBagLevelList(HttpServletRequest request,HttpServletResponse response
			,@RequestBody SchoolBaseInfoEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 String sql="CALL zpSchoolBagLevelData("+dto.getSchoolId()+")";
			 List data=schoolBaseInfoService.findBySql(sql);
			 result.put("code", 0);
			 result.put("data", data);
			 result.put("msg", "查询成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "查询失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 获取教育局学校综评维度分值列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSchoolBagDegreeList")
	public Map<String, Object> getSchoolBagDegreeList(HttpServletRequest request,HttpServletResponse response
			,@RequestBody SchoolBaseInfoEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 String sql="CALL zpSchoolBagDegreeData("+dto.getSchoolId()+")";
			 List data=schoolBaseInfoService.findBySql(sql);
			 List<SchoolBagDegreeDto> schoolBagDegreeDtos=Lists.newArrayList();
			 JSONArray arry=new JSONArray(data);
			 for (int j = 0;j<arry.size();j++) {
					JSONObject obj=arry.getJSONObject(j);
					SchoolBagDegreeDto schoolBagDegree=new SchoolBagDegreeDto();
					schoolBagDegree.setSchoolBagId(obj.getInteger("schoolBagId"));
					schoolBagDegree.setSchoolBagName(obj.getString("schoolBagName"));
					schoolBagDegree.setSchoolCurTermId(obj.getInteger("schoolCurTermId"));
					schoolBagDegree.setSchoolGradeClassId(obj.getInteger("schoolGradeClassId"));
					schoolBagDegree.setSchoolGradeClassName(obj.getString("schoolGradeClassName"));
					schoolBagDegree.setSchoolGradeClassStudentId(obj.getInteger("schoolGradeClassStudentId"));
					schoolBagDegree.setSchoolGradeId(obj.getInteger("schoolGradeId"));
					schoolBagDegree.setSchoolGradeName(obj.getString("schoolGradeName"));
					schoolBagDegree.setSchoolId(obj.getInteger("schoolId"));
					schoolBagDegree.setSchoolName(obj.getString("schoolName"));
					String sql2="CALL zpStudentBagDegreeData("+obj.getInteger("schoolBagId")
					+","+obj.getInteger("schoolGradeClassStudentId")+")";
					List data2=schoolBaseInfoService.findBySql(sql2);
					List<StudentBagDegreeDto> studentBagDegreeDtos=Lists.newArrayList();
					JSONArray arry2=new JSONArray(data2);
					for (int k = 0;k<arry2.size();k++) {
						JSONObject obj2=arry2.getJSONObject(k);
						StudentBagDegreeDto studentBagDegree=new StudentBagDegreeDto();
						studentBagDegree.setSbId(obj2.getInteger("sbId"));
						studentBagDegree.setSbNum(obj2.getInteger("sbNum"));
						studentBagDegree.setSbScore(obj2.getInteger("sbScore"));
						studentBagDegree.setSchoolBagId(obj2.getInteger("schoolBagId"));
						studentBagDegree.setSchoolBagName(obj2.getString("schoolBagName"));
						studentBagDegree.setSchoolDegreeId(obj2.getInteger("schoolDegreeId"));
						studentBagDegree.setSchoolDegreeName(obj2.getString("schoolDegreeName"));
						studentBagDegreeDtos.add(studentBagDegree);
					}
					schoolBagDegree.setStudentBagDegreeDtos(studentBagDegreeDtos);
					schoolBagDegreeDtos.add(schoolBagDegree);
				}
			    Map<Integer, SchoolBagDto> schoolBagDegreeDtoMap = Maps.newConcurrentMap();
				for (SchoolBagDegreeDto zr : schoolBagDegreeDtos) {
					if (schoolBagDegreeDtoMap.containsKey(zr.getSchoolBagId())) {
						schoolBagDegreeDtoMap.get(zr.getSchoolBagId());
					} else {
						SchoolBagDto s=new SchoolBagDto();
						s.setSchoolId(zr.getSchoolId());
						s.setSchoolName(zr.getSchoolName());
						s.setSchoolCurTermId(zr.getSchoolCurTermId());
						s.setSchoolBagId(zr.getSchoolBagId());
						s.setSchoolBagName(zr.getSchoolBagName());
						List<SchoolBagDegreesDto> schoolBagDegreesDtos=Lists.newArrayList();
						for(SchoolBagDegreeDto.StudentBagDegreeDto sd:zr.getStudentBagDegreeDtos()){
								SchoolBagDegreesDto sbd=new SchoolBagDegreesDto();
								sbd.setSchoolDegreeId(sd.getSchoolDegreeId());
								sbd.setSchoolDegreeName(sd.getSchoolDegreeName());
								schoolBagDegreesDtos.add(sbd);
						}
						s.setSchoolBagDegreesDtos(schoolBagDegreesDtos);
						schoolBagDegreeDtoMap.put(zr.getSchoolBagId(), s);
					}
				}
				List<SchoolBagDto> schoolBagDtos=Lists.newArrayList();
				for (Integer sId : schoolBagDegreeDtoMap.keySet()) {
					SchoolBagDto s= schoolBagDegreeDtoMap.get(sId);
					for(SchoolBagDegreesDto sbds:s.getSchoolBagDegreesDtos()){
					 Integer score=0;
					 for(SchoolBagDegreeDto sbd:schoolBagDegreeDtos){
						if(s.getSchoolId().intValue()==sbd.getSchoolId().intValue()&&
							s.getSchoolBagId().intValue()==sbd.getSchoolBagId().intValue()){
							for(SchoolBagDegreeDto.StudentBagDegreeDto stbd:sbd.getStudentBagDegreeDtos()){
								if(sbds.getSchoolDegreeId().intValue()==stbd.getSchoolDegreeId().intValue()){
									score=score+stbd.getSbScore();	
								}
							}
						}
					 }
					 sbds.setSchoolDegreeScore(score);
					}
					schoolBagDtos.add(s);
				}
			 result.put("code", 0);
			 result.put("data", schoolBagDtos);
			 result.put("msg", "查询成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "查询失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 青岛综评基础数据同步
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getQdzpData")
	public Map<String, Object> getQdzpData(HttpServletRequest request,HttpServletResponse response
			,@RequestBody UserAuthenticationEntity dto) {
		Map<String, Object> result = Maps.newConcurrentMap();
		 try {
			 //通过用户账号、密码获取token
			 String token=SongdaInterfaceDataUtil.token(dto.getLoginAccount(),dto.getLoginPassword());
			 //获取青岛教育局机构信息
			 OrgBaseInfoDto orgDto=SongdaInterfaceDataUtil.org(token, "939");
			 SchoolBaseInfoEntity orgData=getOrgData(orgDto);
			 //获取青岛教育局下的教育局管理员列表
			 List<SongdaSchoolManagerDto> orgManagerDtos=SongdaInterfaceDataUtil.orgManager(token, orgData.getSchoolCode());
			 List<SysOparateManager> orgManagerDatas=getOrgManagerData(orgData, orgManagerDtos);
			//获取青岛教育局下的教育局用户列表
			 List<SongdaSchoolTeacherDto> orgUserDtos=SongdaInterfaceDataUtil.orgUser(token, orgData.getSchoolCode());
			 List<TeacherBaseInfoEntity> orgUserDatas=getUserData(orgData, orgUserDtos);
			 //获取青岛教育局下的学校列表
			 List<SongdaSchoolDto> schoolDtos=SongdaInterfaceDataUtil.schoolList(token, orgData.getSchoolCode());
			 List<SchoolBaseInfoEntity> schoolDatas=getSchoolData(orgData,schoolDtos);
			 for(SchoolBaseInfoEntity school:schoolDatas){
				//获取青岛教育局下的学校信息
				 SongdaSchoolDto schoolDto = SongdaInterfaceDataUtil.schoolInfo(token, school.getSchoolCode());
				 SchoolBaseInfoEntity schoolData=getSchoolInfoData(orgData,schoolDto);
				 //获取青岛教育局下的学校学校管理员列表
				 List<SongdaSchoolManagerDto> managerDtos=SongdaInterfaceDataUtil.schoolManager(token, school.getSchoolCode());
				 List<SysOparateManager> managerDatas=getManagerData(school, managerDtos);
				 //获取青岛教育局下的学校老师列表
				 List<SongdaSchoolTeacherDto> teacherDtos=SongdaInterfaceDataUtil.schoolTeacher(token, school.getSchoolCode());
				 List<TeacherBaseInfoEntity> teacherDatas=getTeacherData(school, teacherDtos);
				 //获取青岛教育局下的学校学生列表
				 List<SongdaSchoolStudentDto> studentDtos=SongdaInterfaceDataUtil.schoolStudent(token, school.getSchoolCode());
				 List<StudentBaseInfoEntity> studentDatas=getStudentData(school, studentDtos);
				 //获取青岛教育局下的学校年级列表
				 List<SongdaSchoolGradeDto> gradeDtos=SongdaInterfaceDataUtil.schoolGrade(token, school.getSchoolCode());
				 List<GradeBaseInfoEntity> gradeDatas=getGradeData(school, gradeDtos);
				 for(GradeBaseInfoEntity grade:gradeDatas){
					//获取青岛教育局下的学校年级下班级列表
					 List<SongdaSchoolClassDto> classDtos=SongdaInterfaceDataUtil.schoolClass(token, school.getSchoolCode(),grade.getCode());
					 List<ClassBaseInfoEntity> classDatas=getClassData(grade, classDtos);
					 for(ClassBaseInfoEntity classInfo:classDatas){
						 //获取青岛教育局下的学校年级下班级学生列表
						 List<SongdaClassStuDto> classStudentDtos=SongdaInterfaceDataUtil.schoolClassStudent(token,classInfo.getCode());
						 List<ClassStudentRelEntity> classStudentDatas=getClassStudentData(classInfo, classStudentDtos);
						//获取青岛教育局下的学校年级下班级老师列表
						 List<SongdaClassTeacherDto> classTeacherDtos=SongdaInterfaceDataUtil.schoolClassTeacher(token, classInfo.getCode());
						 List<ClassTeacherRelEntity> classTeacherDatas=getClassTeacherData(classInfo, classTeacherDtos);
					 }
				 }
			 }
			 result.put("code", 0);
			 result.put("msg", "同步成功");
		} catch (Exception e) {
			result.put("code", 1);
			result.put("msg", "同步失败:" + e.getMessage());
		}
		return result;
	}
	/**
	 * 同步教育局机构信息
	 *
	 * @return
	 */
	private SchoolBaseInfoEntity getOrgData(OrgBaseInfoDto orgDto){
		//查询是否存在该教育局机构信息
		 SchoolBaseInfoEntity school=new SchoolBaseInfoEntity();
		 school.setSchoolCode(orgDto.getOrgId());
		 List<SchoolBaseInfoEntity> schoolList=schoolBaseInfoService.findByEntity(school);
		
		 //同步教育局机构信息
		 SchoolBaseInfoEntity  schoolData=null;
		 if(schoolList!=null&&!schoolList.isEmpty()){//更新
			 SchoolBaseInfoEntity updateData= schoolList.get(0);
			 updateData.setSchoolCode(orgDto.getOrgId());
			 updateData.setSchoolName(orgDto.getBureauName());
			 updateData.setSchoolKindId(1);
			 updateData.setSchoolLevelId(1);
			 updateData.setSchoolTypeId(1);
			 schoolBaseInfoService.update(updateData);
			 schoolData=updateData;
		 }else{//新增
			 //查询省市区
			 Integer schoolProvinceId=null;
			 Integer schoolCityId=null;
			 Integer schoolDistrictId=null;
			 if(orgDto.getProvinceCode()!=null&&orgDto.getCityCode()!=null&&orgDto.getAreaCode()!=null){
				 ProvinceEntity p=new ProvinceEntity();
				 p.setProvinceCode(orgDto.getProvinceCode());
				 List<ProvinceEntity> pList=provinceService.findByEntity(p);
				 if(pList!=null&&!pList.isEmpty()){
					 schoolProvinceId=pList.get(0).getProvinceId();
					 CityEntity c=new CityEntity();
					 c.setCityCode(orgDto.getCityCode());
					 c.setProvinceId(schoolProvinceId);
					 List<CityEntity> cList=cityService.findByEntity(c);
					 if(cList!=null&&!cList.isEmpty()){
						 schoolCityId=cList.get(0).getCityId();
						 DistrictionEntity d=new DistrictionEntity();
						 d.setDistrictCode(orgDto.getAreaCode());
						 d.setCityId(schoolCityId);
						 List<DistrictionEntity> dList=districtionService.findByEntity(d);
						 if(dList!=null&&!dList.isEmpty()){
							 schoolDistrictId= dList.get(0).getDistrictId();
						 }
					 }
				 }
			 }
			 SchoolBaseInfoEntity addData= new SchoolBaseInfoEntity();
			 addData.setSchoolCode(orgDto.getOrgId());
			 addData.setSchoolName(orgDto.getBureauName());
			 addData.setSchoolProvinceId(schoolProvinceId);
			 addData.setSchoolCityId(schoolCityId);
			 addData.setSchoolDistrictId(schoolDistrictId);
			 addData.setParentId(0);
			 addData.setSchoolKindId(1);
			 addData.setSchoolLevelId(1);
			 addData.setSchoolTypeId(1);
			 schoolData=schoolBaseInfoService.merge(addData);
		 }
		return schoolData;
	}
	/**
	 * 同步教育局管理员信息
	 *
	 * @return
	 */
	private List<SysOparateManager> getOrgManagerData(SchoolBaseInfoEntity school,List<SongdaSchoolManagerDto> managerDtos){
		List<SysOparateManager> managerDatas=Lists.newArrayList();
		for(SongdaSchoolManagerDto md:managerDtos){
			SysOparateManager manager=new SysOparateManager();
			manager.setSchoolId(school.getSchoolId());
			manager.setName(md.getUserId());
			List<SysOparateManager> managerList=sysOparateManagerService.findByEntity(manager);
			if(managerList!=null&&!managerList.isEmpty()){//更新
				 SysOparateManager updateData=managerList.get(0);
				 updateData.setSchoolId(school.getSchoolId());
				 updateData.setName(md.getUserId());
				 updateData.setRemark(school.getSchoolName());
				 updateData.setId(Md5.md5(String.valueOf(school.getSchoolId())));
				 sysOparateManagerService.saveOrUpdate(updateData);
				 managerDatas.add(updateData);
				//更新老师用户
				 UserAuthenticationEntity userAt = userAuthenticationService
							.findObjectById(updateData.getId());
				 userAt.setLoginAccount(updateData.getName());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(updateData.getId());
				 userAuthenticationService.update(userAt);
				//更新老师角色
				UserAccessEntity user = new UserAccessEntity();
				user.setUserId(updateData.getId());
				List<UserAccessEntity> userList = userAccessService.findByEntity(user);
				if (userList != null && !userList.isEmpty()) {
					user = userList.get(0);
					user.setAuthorityId(12);
					user.setRemark(updateData.getRemark()+"教育局超级管理员");
					userAccessService.update(user);
				}
			 }else{//新增
				 SysOparateManager addData= new SysOparateManager();
				 addData.setSchoolId(school.getSchoolId());
				 addData.setName(md.getUserId());
				 addData.setRemark(school.getSchoolName());
				 addData.setId(Md5.md5(String.valueOf(school.getSchoolId())));
				 sysOparateManagerService.saveOrUpdate(addData);
				 managerDatas.add(addData);
				 //老师用户
				 UserAuthenticationEntity userAt = new UserAuthenticationEntity();
				 userAt.setLoginAccount(addData.getName());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(addData.getId().toString());
				 userAuthenticationService.save(userAt);
				//老师角色
				UserAccessEntity user = new UserAccessEntity();
				user.setUserId(addData.getId());
				user.setAuthorityId(12);
				user.setRemark(addData.getRemark()+"教育局超级管理员");
				userAccessService.save(user);
			 }
		}
		return managerDatas;
	}
	/**
	 * 同步教育局下用户信息
	 *
	 * @return
	 */
	private List<TeacherBaseInfoEntity> getUserData(SchoolBaseInfoEntity school,List<SongdaSchoolTeacherDto> teacherDtos){
		List<TeacherBaseInfoEntity> teacherDatas=Lists.newArrayList();
		for(SongdaSchoolTeacherDto td:teacherDtos){
			TeacherBaseInfoEntity teacher=new TeacherBaseInfoEntity();
			teacher.setSchoolId(school.getSchoolId());
			teacher.setCode(td.getUserId());
			List<TeacherBaseInfoEntity> teacherList=teacherBaseInfoService.findByEntity(teacher);
			if(teacherList!=null&&!teacherList.isEmpty()){//更新
				TeacherBaseInfoEntity updateData= teacherList.get(0);
				 updateData.setSchoolId(school.getSchoolId());
				 updateData.setCode(td.getUserId());
				 updateData.setTel(td.getUserNo());
				 updateData.setName(td.getPersonName());
				 updateData.setAvatar(td.getUserHead());
					if ("1".equals(td.getGender())) {
						updateData.setSex(0);
					} else if ("2".equals(td.getGender())) {
						updateData.setSex(1);
					}
				 teacherBaseInfoService.update(updateData);
				 teacherDatas.add(updateData);
				//老师用户
				 UserAuthenticationEntity userAt = userAuthenticationService
							.findObjectById(String.valueOf(updateData.getId()));
				 userAt.setLoginAccount(updateData.getTel());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAuthenticationService.update(userAt);
				// 更新老师角色
					UserAccessEntity user = new UserAccessEntity();
					user.setUserId(String.valueOf(updateData.getId()));
					List<UserAccessEntity> userList = userAccessService.findByEntity(user);
					if (userList != null && !userList.isEmpty()) {
						user = userList.get(0);
						user.setAuthorityId(11);
						user.setRemark("教育局员工");
						userAccessService.update(user);
					}
			 }else{//新增
				 TeacherBaseInfoEntity addData= new TeacherBaseInfoEntity();
				 addData.setSchoolId(school.getSchoolId());
				 addData.setCode(td.getUserId());
				 addData.setTel(td.getUserNo());
				 addData.setName(td.getPersonName());
				 addData.setAvatar(td.getUserHead());
					if ("1".equals(td.getGender())) {
						addData.setSex(0);
					} else if ("2".equals(td.getGender())) {
						addData.setSex(1);
					}
				 TeacherBaseInfoEntity teacherData=teacherBaseInfoService.saveEntity(addData);
				 teacherDatas.add(teacherData);
				 //老师用户
				 UserAuthenticationEntity userAt = new UserAuthenticationEntity();
				 userAt.setLoginAccount(teacherData.getTel());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(teacherData.getId().toString());
				 userAuthenticationService.save(userAt);
				// 更新老师角色
					UserAccessEntity user = new UserAccessEntity();
					user.setUserId(String.valueOf(teacherData.getId()));
					user.setAuthorityId(11);
					user.setRemark("教育局员工");
				userAccessService.save(user);
			 }
		}
		return teacherDatas;
		
	}
	/**
	 * 同步教育局下学校列表信息
	 *
	 * @return
	 */
	private List<SchoolBaseInfoEntity> getSchoolData(SchoolBaseInfoEntity orgData,List<SongdaSchoolDto> schoolDtos){
		List<SchoolBaseInfoEntity> schoolDatas=Lists.newArrayList();
		for(SongdaSchoolDto sd:schoolDtos){
			 SchoolBaseInfoEntity school=new SchoolBaseInfoEntity();
			 school.setSchoolCode(sd.getSchoolId());
			 school.setParentId(orgData.getSchoolId());
			 List<SchoolBaseInfoEntity> schoolList=schoolBaseInfoService.findByEntity(school);
			 //查询省市区
			 Integer schoolProvinceId=orgData.getSchoolProvinceId();
			 Integer schoolCityId=orgData.getSchoolCityId();
			 Integer schoolDistrictId=orgData.getSchoolDistrictId();
			 //同步教育局机构下学校信息
			 if(schoolList!=null&&!schoolList.isEmpty()){//更新
				 SchoolBaseInfoEntity updateData= schoolList.get(0);
				 updateData.setSchoolCode(sd.getSchoolId());
				 updateData.setSchoolName(sd.getSchoolName());
				 updateData.setSchoolProvinceId(schoolProvinceId);
				 updateData.setSchoolCityId(schoolCityId);
				 updateData.setSchoolDistrictId(schoolDistrictId);
				 updateData.setSchoolKindId(1);
				 updateData.setSchoolLevelId(1);
				 if(sd.getSchoolType()!=null){
					 updateData.setSchoolTypeId(Integer.valueOf(sd.getSchoolType())+1); 
				 }else{
					 updateData.setSchoolTypeId(1);
				 }
				 schoolBaseInfoService.update(updateData);
				 schoolDatas.add(updateData);
			 }else{//新增
				 SchoolBaseInfoEntity addData= new SchoolBaseInfoEntity();
				 addData.setSchoolCode(sd.getSchoolId());
				 addData.setSchoolName(sd.getSchoolName());
				 addData.setSchoolProvinceId(schoolProvinceId);
				 addData.setSchoolCityId(schoolCityId);
				 addData.setSchoolDistrictId(schoolDistrictId);
				 addData.setParentId(orgData.getSchoolId());
				 addData.setSchoolKindId(1);
				 addData.setSchoolLevelId(1);
				 if(sd.getSchoolType()!=null){
					 addData.setSchoolTypeId(Integer.valueOf(sd.getSchoolType())+1); 
				 }else{
					 addData.setSchoolTypeId(1);
				 }
				 SchoolBaseInfoEntity schoolData=schoolBaseInfoService.merge(addData);
				 schoolDatas.add(schoolData);
			 }
		 }
		return schoolDatas;
	}
	/**
	 * 同步教育局下学校信息
	 *
	 * @return
	 */
	private SchoolBaseInfoEntity getSchoolInfoData(SchoolBaseInfoEntity orgData,SongdaSchoolDto schoolDto){
		SchoolBaseInfoEntity schoolData=new SchoolBaseInfoEntity();
			 SchoolBaseInfoEntity school=new SchoolBaseInfoEntity();
			 school.setSchoolCode(schoolDto.getSchoolId());
			 school.setParentId(orgData.getSchoolId());
			 List<SchoolBaseInfoEntity> schoolList=schoolBaseInfoService.findByEntity(school);
			 //查询省市区
			 Integer schoolProvinceId=orgData.getSchoolProvinceId();
			 Integer schoolCityId=orgData.getSchoolCityId();
			 Integer schoolDistrictId=orgData.getSchoolDistrictId();
			 //同步教育局机构下学校信息
			 if(schoolList!=null&&!schoolList.isEmpty()){//更新
				 SchoolBaseInfoEntity updateData= schoolList.get(0);
				 updateData.setSchoolCode(schoolDto.getSchoolId());
				 updateData.setSchoolName(schoolDto.getSchoolName());
				 updateData.setSchoolProvinceId(schoolProvinceId);
				 updateData.setSchoolCityId(schoolCityId);
				 updateData.setSchoolDistrictId(schoolDistrictId);
				 updateData.setSchoolKindId(1);
				 updateData.setSchoolLevelId(1);
				 if(schoolDto.getSchoolType()!=null){
					 updateData.setSchoolTypeId(Integer.valueOf(schoolDto.getSchoolType())+1); 
				 }else{
					 updateData.setSchoolTypeId(1);
				 }
				 schoolBaseInfoService.update(updateData);
				 schoolData=updateData;
			 }else{//新增
				 SchoolBaseInfoEntity addData= new SchoolBaseInfoEntity();
				 addData.setSchoolCode(schoolDto.getSchoolId());
				 addData.setSchoolName(schoolDto.getSchoolName());
				 addData.setSchoolProvinceId(schoolProvinceId);
				 addData.setSchoolCityId(schoolCityId);
				 addData.setSchoolDistrictId(schoolDistrictId);
				 addData.setParentId(orgData.getSchoolId());
				 addData.setSchoolKindId(1);
				 addData.setSchoolLevelId(1);
				 if(schoolDto.getSchoolType()!=null){
					 addData.setSchoolTypeId(Integer.valueOf(schoolDto.getSchoolType())+1); 
				 }else{
					 addData.setSchoolTypeId(1);
				 }
				 schoolData=schoolBaseInfoService.merge(addData);
			 }
		return schoolData;
	}
	/**
	 * 同步教育局下学校管理员信息
	 *
	 * @return
	 */
	private List<SysOparateManager> getManagerData(SchoolBaseInfoEntity school,List<SongdaSchoolManagerDto> managerDtos){
		List<SysOparateManager> managerDatas=Lists.newArrayList();
		for(SongdaSchoolManagerDto md:managerDtos){
			SysOparateManager manager=new SysOparateManager();
			manager.setSchoolId(school.getSchoolId());
			manager.setName(md.getUserId());
			List<SysOparateManager> managerList=sysOparateManagerService.findByEntity(manager);
			if(managerList!=null&&!managerList.isEmpty()){//更新
				 SysOparateManager updateData=managerList.get(0);
				 updateData.setSchoolId(school.getSchoolId());
				 updateData.setName(md.getUserId());
				 updateData.setRemark(school.getSchoolName());
				 updateData.setId(Md5.md5(String.valueOf(school.getSchoolId())));
				 sysOparateManagerService.saveOrUpdate(updateData);
				 managerDatas.add(updateData);
				//更新老师用户
				 UserAuthenticationEntity userAt = userAuthenticationService
							.findObjectById(updateData.getId());
				 userAt.setLoginAccount(updateData.getName());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(updateData.getId());
				 userAuthenticationService.update(userAt);
				//更新老师角色
				UserAccessEntity user = new UserAccessEntity();
				user.setUserId(updateData.getId());
				List<UserAccessEntity> userList = userAccessService.findByEntity(user);
				if (userList != null && !userList.isEmpty()) {
					user = userList.get(0);
					user.setAuthorityId(2);
					user.setRemark(updateData.getRemark()+"学校管理员");
					userAccessService.update(user);
				}
			 }else{//新增
				 SysOparateManager addData= new SysOparateManager();
				 addData.setSchoolId(school.getSchoolId());
				 addData.setName(md.getUserId());
				 addData.setRemark(school.getSchoolName());
				 addData.setId(Md5.md5(String.valueOf(school.getSchoolId())));
				 sysOparateManagerService.saveOrUpdate(addData);
				 managerDatas.add(addData);
				 //老师用户
				 UserAuthenticationEntity userAt = new UserAuthenticationEntity();
				 userAt.setLoginAccount(addData.getName());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(addData.getId().toString());
				 userAuthenticationService.save(userAt);
				//老师角色
				UserAccessEntity user = new UserAccessEntity();
				user.setUserId(addData.getId());
				user.setAuthorityId(2);
				user.setRemark(addData.getRemark()+"学校管理员");
				userAccessService.save(user);
			 }
		}
		return managerDatas;
	}
	/**
	 * 同步教育局下学校老师信息
	 *
	 * @return
	 */
	private List<TeacherBaseInfoEntity> getTeacherData(SchoolBaseInfoEntity school,List<SongdaSchoolTeacherDto> teacherDtos){
		List<TeacherBaseInfoEntity> teacherDatas=Lists.newArrayList();
		for(SongdaSchoolTeacherDto td:teacherDtos){
			TeacherBaseInfoEntity teacher=new TeacherBaseInfoEntity();
			teacher.setSchoolId(school.getSchoolId());
			teacher.setCode(td.getUserId());
			List<TeacherBaseInfoEntity> teacherList=teacherBaseInfoService.findByEntity(teacher);
			if(teacherList!=null&&!teacherList.isEmpty()){//更新
				TeacherBaseInfoEntity updateData= teacherList.get(0);
				 updateData.setSchoolId(school.getSchoolId());
				 updateData.setCode(td.getUserId());
				 updateData.setTel(td.getUserNo());
				 updateData.setName(td.getPersonName());
				 updateData.setAvatar(td.getUserHead());
					if ("1".equals(td.getGender())) {
						updateData.setSex(0);
					} else if ("2".equals(td.getGender())) {
						updateData.setSex(1);
					}
				 teacherBaseInfoService.update(updateData);
				 teacherDatas.add(updateData);
				//老师用户
				 UserAuthenticationEntity userAt = userAuthenticationService
							.findObjectById(String.valueOf(updateData.getId()));
				 userAt.setLoginAccount(updateData.getTel());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAuthenticationService.update(userAt);
				// 更新老师角色
					UserAccessEntity user = new UserAccessEntity();
					user.setUserId(String.valueOf(updateData.getId()));
					List<UserAccessEntity> userList = userAccessService.findByEntity(user);
					if (userList != null && !userList.isEmpty()) {
						user = userList.get(0);
						user.setAuthorityId(5);
						user.setRemark("普通老师");
						userAccessService.update(user);
					}
			 }else{//新增
				 TeacherBaseInfoEntity addData= new TeacherBaseInfoEntity();
				 addData.setSchoolId(school.getSchoolId());
				 addData.setCode(td.getUserId());
				 addData.setTel(td.getUserNo());
				 addData.setName(td.getPersonName());
				 addData.setAvatar(td.getUserHead());
					if ("1".equals(td.getGender())) {
						addData.setSex(0);
					} else if ("2".equals(td.getGender())) {
						addData.setSex(1);
					}
				 TeacherBaseInfoEntity teacherData=teacherBaseInfoService.saveEntity(addData);
				 teacherDatas.add(teacherData);
				 //老师用户
				 UserAuthenticationEntity userAt = new UserAuthenticationEntity();
				 userAt.setLoginAccount(teacherData.getTel());
				 userAt.setLoginPassword(Md5.md5("123456"));
				 userAt.setUserId(teacherData.getId().toString());
				 userAuthenticationService.save(userAt);
				// 更新老师角色
					UserAccessEntity user = new UserAccessEntity();
					user.setUserId(String.valueOf(teacherData.getId()));
					user.setAuthorityId(5);
					user.setRemark("普通老师");
				userAccessService.save(user);
			 }
		}
		return teacherDatas;
		
	}
	/**
	 * 同步教育局下学校学生信息
	 *
	 * @return
	 */
	private List<StudentBaseInfoEntity> getStudentData(SchoolBaseInfoEntity school,List<SongdaSchoolStudentDto> studentDtos){
		List<StudentBaseInfoEntity> studentDatas=Lists.newArrayList();
		for(SongdaSchoolStudentDto sd:studentDtos){
			StudentBaseInfoEntity student=new StudentBaseInfoEntity();
			student.setCode(sd.getUserId());
			student.setSchoolId(school.getSchoolId());
			List<StudentBaseInfoEntity> studentList=studentBaseInfoService.findByEntity(student);
			if(studentList!=null&&!studentList.isEmpty()){//更新
				StudentBaseInfoEntity updateData= studentList.get(0);
				updateData.setCode(sd.getUserId());
				updateData.setName(sd.getPersonName());
				if ("1".equals(sd.getGender())) {
					updateData.setSex(0);
				} else if ("2".equals(sd.getGender())) {
					updateData.setSex(1);
				}
				updateData.setTel(sd.getIdcard());// 电话代替身份证
				updateData.setHeading(sd.getUserHead());
				if (StringUtils.isBlank(sd.getEduNo())) {
					updateData.setNumber(UuidIdentifyCodeGenerator.getUUID());// 学籍号暂时代替账号
				} else {
					updateData.setNumber(sd.getEduNo());// 学籍号暂时代替账号
				}
				// 存储自身数据表的学生所在学校id
				updateData.setSchoolId(school.getSchoolId());
				studentBaseInfoService.update(student);
			 }else{//新增
				 StudentBaseInfoEntity addData= new StudentBaseInfoEntity();
				 addData.setCode(sd.getUserId());
				 addData.setName(sd.getPersonName());
				 if ("1".equals(sd.getGender())) {
					addData.setSex(0);
				 } else if ("2".equals(sd.getGender())) {
					addData.setSex(1);
				 }
				 addData.setTel(sd.getIdcard());// 电话代替身份证
				 addData.setHeading(sd.getUserHead());
				 if (StringUtils.isBlank(sd.getEduNo())) {
					addData.setNumber(UuidIdentifyCodeGenerator.getUUID());// 学籍号暂时代替账号
				 } else {
					addData.setNumber(sd.getEduNo());// 学籍号暂时代替账号
				 }
				 // 存储自身数据表的学生所在学校id
				 addData.setSchoolId(school.getSchoolId());
				 studentBaseInfoService.save(addData);
			 }
		}
		return studentDatas;
		
	}
	/**
	 * 同步教育局下学校年级信息
	 *
	 * @return
	 */
	private List<GradeBaseInfoEntity> getGradeData(SchoolBaseInfoEntity school,List<SongdaSchoolGradeDto> gradeDtos){
		List<GradeBaseInfoEntity> gradeDatas=Lists.newArrayList();
		for(SongdaSchoolGradeDto gd:gradeDtos){
			GradeBaseInfoEntity grade=new GradeBaseInfoEntity();
			grade.setSchoolId(school.getSchoolId());
			grade.setCode(gd.getGradeId());
			List<GradeBaseInfoEntity> gradeList=gradeBaseInfoService.findByEntity(grade);
			//同步教育局机构下学校年级信息
			 if(gradeList!=null&&!gradeList.isEmpty()){//更新
				 GradeBaseInfoEntity updateData= gradeList.get(0);
				 updateData.setCode(gd.getGradeId());
				 updateData.setName(gd.getGradeName());
				 updateData.setSchoolId(school.getSchoolId());
				 gradeBaseInfoService.update(updateData);
				 gradeDatas.add(updateData);
			 }else{//新增
				 GradeBaseInfoEntity addData= new GradeBaseInfoEntity();
				 addData.setCode(gd.getGradeId());
				 addData.setName(gd.getGradeName());
				 addData.setSchoolId(school.getSchoolId());
				 GradeBaseInfoEntity gradeData=gradeBaseInfoService.merge(addData);
				 gradeDatas.add(gradeData);
			 }
		}
		return gradeDatas;
	}
	/**
	 * 同步教育局下学校年级下班级信息
	 *
	 * @return
	 */
	private List<ClassBaseInfoEntity> getClassData(GradeBaseInfoEntity grade,List<SongdaSchoolClassDto> classDtos){
		List<ClassBaseInfoEntity> classDatas=Lists.newArrayList();
		for(SongdaSchoolClassDto cd:classDtos){
			 //新增学期
			TermBaseInfoEntity termData=null;
			 TermBaseInfoEntity term=new TermBaseInfoEntity();
			 term.setSchoolId(grade.getSchoolId());
			 term.setCode(cd.getStudyYear());
			 term.setState(1);
			 List<TermBaseInfoEntity> termList=termBaseInfoService.findByEntity(term);
			 if(termList!=null&&!termList.isEmpty()){//更新
				 termData=termList.get(0);
			 }else{//新增
				 TermBaseInfoEntity oldTerm=new TermBaseInfoEntity();
				 oldTerm.setSchoolId(grade.getSchoolId());
				 List<TermBaseInfoEntity> oldTermList=termBaseInfoService.findByEntity(oldTerm);
				 if(oldTermList!=null&&!oldTermList.isEmpty()){
					 for(TermBaseInfoEntity old:oldTermList){
						 old.setState(0);
						 termBaseInfoService.update(old);
					 }
				 }
				 TermBaseInfoEntity addData=new TermBaseInfoEntity();
				 addData.setCode(cd.getStudyYear());
				 addData.setSchoolId(grade.getSchoolId());
				 addData.setName(cd.getStudyYear());
				 addData.setState(1);
				 termData=termBaseInfoService.merge(addData);
			 }
			ClassBaseInfoEntity classInfo=new ClassBaseInfoEntity();
			classInfo.setGradeId(grade.getId());
			classInfo.setCode(cd.getClassId());
			List<ClassBaseInfoEntity> classList=classBaseInfoService.findByEntity(classInfo);
			//同步教育局机构下学校年级下班级信息
			 if(classList!=null&&!classList.isEmpty()){//更新
				 ClassBaseInfoEntity updateData= classList.get(0);
				 updateData.setCode(cd.getClassId());
				 updateData.setName(cd.getClassName());
				 updateData.setGradeId(grade.getId());
				// 班主任id
				TeacherBaseInfoEntity teacher = new TeacherBaseInfoEntity();
				if (StringUtils.isNotBlank(cd.getMasterId())) {
					teacher.setCode(cd.getMasterId());
					teacher.setSchoolId(grade.getSchoolId());
					List<TeacherBaseInfoEntity> teachers = teacherBaseInfoService.findByEntity(teacher);
					if (teachers != null && !teachers.isEmpty()) {
						updateData.setMasterTeacherId(teachers.get(0).getId());
					}
				}
				updateData.setClassAddress(cd.getStudyYear());//学年
				//学期
				updateData.setTermId(termData.getId());
				classBaseInfoService.update(updateData);
				classDatas.add(updateData);
			 }else{//新增
				 ClassBaseInfoEntity addData= new ClassBaseInfoEntity();
				 addData.setCode(cd.getClassId());
				 addData.setName(cd.getClassName());
				 addData.setGradeId(grade.getId());
				// 班主任id
				TeacherBaseInfoEntity teacher = new TeacherBaseInfoEntity();
				if (StringUtils.isNotBlank(cd.getMasterId())) {
					teacher.setCode(cd.getMasterId());
					teacher.setSchoolId(grade.getSchoolId());
					List<TeacherBaseInfoEntity> teachers = teacherBaseInfoService.findByEntity(teacher);
					if (teachers != null && !teachers.isEmpty()) {
						addData.setMasterTeacherId(teachers.get(0).getId());
					}
				}
				addData.setClassAddress(cd.getStudyYear());//学年
				//学期
				addData.setTermId(termData.getId());
				 ClassBaseInfoEntity classData=classBaseInfoService.merge(addData);
				 classDatas.add(classData);
			 }
		}
		return classDatas;
	}
	/**
	 * 同步教育局下学校年级下班级学生信息
	 *
	 * @return
	 */
	private List<ClassStudentRelEntity> getClassStudentData(ClassBaseInfoEntity classInfo,List<SongdaClassStuDto> classStudentDtos){
		List<ClassStudentRelEntity> classStudentDatas=Lists.newArrayList();
		for(SongdaClassStuDto csd:classStudentDtos){
			ClassStudentRelEntity classStudent=new ClassStudentRelEntity();
			classStudent.setClassId(classInfo.getId());
			// 自身学生id
			StudentBaseInfoEntity student = new StudentBaseInfoEntity();
			student.setCode(csd.getUserId());
			List<StudentBaseInfoEntity> students = studentBaseInfoService.findByEntity(student);
			if (students != null && !students.isEmpty()) {
				classStudent.setStudentId(students.get(0).getId());
			}
			List<ClassStudentRelEntity> classStudentList=classStudentRelService.findByEntity(classStudent);
			//同步教育局机构下学校年级下班级信息
			 if(classStudentList!=null&&!classStudentList.isEmpty()){//更新
				 ClassStudentRelEntity updateData= classStudentList.get(0);
				 // 自身班级id
				 updateData.setClassId(classInfo.getId());
				 // 自身学生id
				 StudentBaseInfoEntity studentInfo = new StudentBaseInfoEntity();
				 studentInfo.setCode(csd.getUserId());
				 List<StudentBaseInfoEntity> studentInfos = studentBaseInfoService.findByEntity(studentInfo);
				 if (studentInfos != null && !studentInfos.isEmpty()) {
					updateData.setStudentId(studentInfos.get(0).getId());
				 }
				 updateData.setTermId(classInfo.getTermId());
				 classStudentRelService.update(updateData);
				 classStudentDatas.add(updateData);
			 }else{//新增
				 ClassStudentRelEntity addData= new ClassStudentRelEntity();
				 // 自身班级id
				 addData.setClassId(classInfo.getId());
				 // 自身学生id
				 StudentBaseInfoEntity studentInfo = new StudentBaseInfoEntity();
				 studentInfo.setCode(csd.getUserId());
				 List<StudentBaseInfoEntity> studentInfos = studentBaseInfoService.findByEntity(studentInfo);
				 if (studentInfos != null && !studentInfos.isEmpty()) {
					addData.setStudentId(studentInfos.get(0).getId());
				 }
				 addData.setTermId(classInfo.getTermId());
				 ClassStudentRelEntity csData=classStudentRelService.merge(addData);
				 classStudentDatas.add(csData);
			 }
		}
		return classStudentDatas;
	}
	/**
	 * 同步教育局下学校年级下班级老师信息
	 *
	 * @return
	 */
	private List<ClassTeacherRelEntity> getClassTeacherData(ClassBaseInfoEntity classInfo,List<SongdaClassTeacherDto> classTeacherDtos){
		List<ClassTeacherRelEntity> classTeacherDatas=Lists.newArrayList();
		for(SongdaClassTeacherDto ctd:classTeacherDtos){
			ClassTeacherRelEntity classTeacher=new ClassTeacherRelEntity();
			classTeacher.setClassId(classInfo.getId());
			TeacherBaseInfoEntity teacher = new TeacherBaseInfoEntity();
			teacher.setCode(ctd.getUserId());
			List<TeacherBaseInfoEntity> teachers = teacherBaseInfoService.findByEntity(teacher);
			if (teachers != null && !teachers.isEmpty()) {
				classTeacher.setTeacherId(teachers.get(0).getId());
			}
			List<ClassTeacherRelEntity> classTeacherList=classTeacherRelService.findByEntity(classTeacher);
			//同步教育局机构下学校年级下班级老师信息
			 if(classTeacherList!=null&&!classTeacherList.isEmpty()){//更新
				 ClassTeacherRelEntity updateData= classTeacherList.get(0);
				// 自身班级id
				 updateData.setClassId(classInfo.getId());
					// 自身老师id
					TeacherBaseInfoEntity teacherInfo = new TeacherBaseInfoEntity();
					teacherInfo.setCode(ctd.getUserId());
					List<TeacherBaseInfoEntity> teacherInfos = teacherBaseInfoService.findByEntity(teacherInfo);
					if (teacherInfos != null && !teacherInfos.isEmpty()) {
						updateData.setTeacherId(teacherInfos.get(0).getId());
					}
					if (updateData.getCurfocus() == null) {
						updateData.setCurfocus(0);
					}
					// 自身学期id
					updateData.setTermId(classInfo.getTermId());
				 classTeacherRelService.update(updateData);
				 classTeacherDatas.add(updateData);
			 }else{//新增
				 ClassTeacherRelEntity addData= new ClassTeacherRelEntity();
				// 自身班级id
				 addData.setClassId(classInfo.getId());
					// 自身老师id
					TeacherBaseInfoEntity teacherInfo = new TeacherBaseInfoEntity();
					teacherInfo.setCode(ctd.getUserId());
					List<TeacherBaseInfoEntity> teacherInfos = teacherBaseInfoService.findByEntity(teacherInfo);
					if (teacherInfos != null && !teacherInfos.isEmpty()) {
						addData.setTeacherId(teacherInfos.get(0).getId());
					}
					if (addData.getCurfocus() == null) {
						addData.setCurfocus(0);
					}
					// 自身学期id
					addData.setTermId(classInfo.getTermId());
				 ClassTeacherRelEntity csData=classTeacherRelService.merge(addData);
				 classTeacherDatas.add(csData);
			 }
		}
		return classTeacherDatas;
	}
}
