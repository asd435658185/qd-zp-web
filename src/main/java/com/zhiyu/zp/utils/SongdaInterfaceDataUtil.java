
package com.zhiyu.zp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.util.Md5;
import com.zhiyu.zp.dto.OrgBaseInfoDto;
import com.zhiyu.zp.dto.SongdaClassMaster;
import com.zhiyu.zp.dto.SongdaClassStuDto;
import com.zhiyu.zp.dto.SongdaClassTeacherDto;
import com.zhiyu.zp.dto.SongdaLoginUserInfo;
import com.zhiyu.zp.dto.SongdaSchoolClassDto;
import com.zhiyu.zp.dto.SongdaSchoolDto;
import com.zhiyu.zp.dto.SongdaSchoolGradeDto;
import com.zhiyu.zp.dto.SongdaSchoolManagerDto;
import com.zhiyu.zp.dto.SongdaSchoolStudentDto;
import com.zhiyu.zp.dto.SongdaSchoolTeacherDto;
import com.zhiyu.zp.dto.SongdaUserRoleDto;

import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.StringUtils;


public class SongdaInterfaceDataUtil
{
    private static String url="http://124.129.157.204:8071";
    private static String tokenUrl="http://124.129.157.204:8071";
    public SongdaInterfaceDataUtil()
    {
    }
    //获取教育局机构信息
    public static OrgBaseInfoDto org(String token, String orgId)
    {
        Map params = Maps.newTreeMap();
        params.put("orgId", orgId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/org/simple/info", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String bureauId = data.getString("bureauId");
        String bureauCode = data.getString("bureauCode");
        String bureauName = data.getString("bureauName");
        String provinceCode = data.getString("provinceCode");
        String cityCode = data.getString("cityCode");
        String areaCode = data.getString("areaCode");
        String orgType = data.getString("orgType");
        OrgBaseInfoDto dto = new OrgBaseInfoDto(bureauId, bureauCode,bureauName, 
        		provinceCode,cityCode,areaCode,orgType);
        return dto;
    }
  //获取教育局机构管理员信息
    public static List orgManager(String token, String orgId)
    {
        Map params = Maps.newTreeMap();
        params.put("orgId", orgId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("pageNo", "1");
        params.put("pageSize", "100000");
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/org/manager/list", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List managersList = Lists.newArrayList();
        managersList = JSON.parseArray(contentStr, SongdaSchoolManagerDto.class);
        return managersList;
    }
  //获取教育局机构老师信息
    public static List orgUser(String token, String orgId)
    {
        Map params = Maps.newTreeMap();
        params.put("orgId", orgId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("pageNo", "1");
        params.put("pageSize", "100000");
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/org/user/list", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List managersList = Lists.newArrayList();
        managersList = JSON.parseArray(contentStr, SongdaSchoolTeacherDto.class);
        return managersList;
    }
    //获取教育局所在学校列表
    public static List schoolList(String token, String orgId)
    {
        Map params = Maps.newTreeMap();
        params.put("orgId", orgId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/org/children/eduSchool/list", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
        List dto = JSON.parseArray(contentStr, SongdaSchoolDto.class);
        return dto;
    }
  //获取教育局所在学校信息
    public static SongdaSchoolDto schoolInfo(String token, String schoolId)
    {
        Map params = Maps.newTreeMap();
        params.put("schoolId", schoolId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/simple/info", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONObject("data"));
        SongdaSchoolDto dto = JSON.parseObject(contentStr, SongdaSchoolDto.class);
        return dto;
    }
    //获取学校管理员
    public static List schoolManager(String token, String schoolId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("schoolId", schoolId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("pageNo", "1");
        schoolMapParam.put("pageSize", "100000");
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/manager/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List managersList = Lists.newArrayList();
        managersList = JSON.parseArray(contentStr, SongdaSchoolManagerDto.class);
        return managersList;
    }
    //获取学校老师列表
    public static List schoolTeacher(String token, String schoolId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("schoolId", schoolId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("pageNo", "1");
        schoolMapParam.put("pageSize", "100000");
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/teacher/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List teachers = JSON.parseArray(contentStr, SongdaSchoolTeacherDto.class);
        if(!teachers.isEmpty()){
        	StringBuilder sb = new StringBuilder();
            SongdaSchoolTeacherDto dto = new SongdaSchoolTeacherDto();
            for(Iterator iterator = teachers.iterator(); iterator.hasNext(); sb.append(dto.getUserId()).append(","))
                dto = (SongdaSchoolTeacherDto)iterator.next();

            String teacherIds = sb.substring(0, sb.length() - 1);
            List rolesList = schoolTeacherRoles(token, teacherIds);
            for(Iterator iterator1 = teachers.iterator(); iterator1.hasNext();)
            {
                SongdaSchoolTeacherDto dtot = (SongdaSchoolTeacherDto)iterator1.next();
                for(Iterator iterator2 = rolesList.iterator(); iterator2.hasNext();)
                {
                    SongdaUserRoleDto role = (SongdaUserRoleDto)iterator2.next();
                    if(dtot.getUserId().equals(role.getUserId()))
                    {
                        dtot.setRoles(role.getRoles());
                        break;
                    }
                }

            }
        }
        return teachers;
    }
    
    public static List schoolTeacherRoles(String token, String teacherIds)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("userIds", teacherIds);
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/teacher/role/list/batch", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
        List rolesList = Lists.newArrayList();
        rolesList = JSON.parseArray(contentStr, SongdaUserRoleDto.class);
        return rolesList;
    }
    //获取学校学生列表
    public static List schoolStudent(String token, String schoolId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("schoolId", schoolId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("pageNo", "1");
        schoolMapParam.put("pageSize", "100000");
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/student/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List students = JSON.parseArray(contentStr, SongdaSchoolStudentDto.class);
        return students;
    }
  //获取教育局所在学校年级列表
    public static List schoolGrade(String token, String schoolId)
    {
        Map params = Maps.newTreeMap();
        params.put("schoolId", schoolId);
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/grade/list", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
        List grades = JSON.parseArray(contentStr, SongdaSchoolGradeDto.class);
        return grades;
    }
    //获取学校年级下班级列表
    public static List schoolClass(String token, String schoolId,String gradeId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("schoolId", schoolId);
        schoolMapParam.put("gradeId", gradeId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("pageNo", "1");
        schoolMapParam.put("pageSize", "100");
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/school/class/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        String contentStr = JSON.toJSONString(data.getJSONArray("records"));
        List clzes = JSON.parseArray(contentStr, SongdaSchoolClassDto.class);
        if(!clzes.isEmpty()){
        	StringBuilder sb = new StringBuilder();
            SongdaSchoolClassDto dto = new SongdaSchoolClassDto();
            for(Iterator iterator = clzes.iterator(); iterator.hasNext(); sb.append(dto.getClassId()).append(","))
                dto = (SongdaSchoolClassDto)iterator.next();

            String classIds = sb.substring(0, sb.length() - 1);
            schoolMapParam.remove("schoolId");
            schoolMapParam.remove("gradeId");
            schoolMapParam.remove("pageNo");
            schoolMapParam.remove("pageSize");
            schoolMapParam.remove("sign");
            schoolMapParam.put("classId", classIds);
            schoolMapParam.put("sign", "1");
            response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/class/headTeacher/batch", schoolMapParam);
            List masterList = Lists.newArrayList();
            jsonObject = JSONObject.parseObject(response);
            contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
            masterList = JSON.parseArray(contentStr, SongdaClassMaster.class);
            if(!masterList.isEmpty()){
            	for(Iterator iterator1 = clzes.iterator(); iterator1.hasNext();)
                {
                    SongdaSchoolClassDto dtoc = (SongdaSchoolClassDto)iterator1.next();
                    for(Iterator iterator2 = masterList.iterator(); iterator2.hasNext();)
                    {
                        SongdaClassMaster master = (SongdaClassMaster)iterator2.next();
                        if(dtoc.getClassId().equals(master.getClassId()) && master.getHeadTeacher() != null && master.getHeadTeacher().getUserId() != null)
                        {
                            dtoc.setMasterId(master.getHeadTeacher().getUserId());
                            break;
                        }
                    }
            }
            }
        }
        return clzes;
    }
   //获取班级学生
    public static List schoolClassStudent(String token, String classId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("classId", classId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/class/student/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
        List students = JSON.parseArray(contentStr, SongdaClassStuDto.class);
        return students;
    }

    public static List schoolClassTeacher(String token, String classId)
    {
        Map schoolMapParam = Maps.newTreeMap();
        schoolMapParam.put("classId", classId);
        schoolMapParam.put("accessToken", token);
        schoolMapParam.put("appKey", "sundata");
        schoolMapParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        schoolMapParam.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/class/teacher/list", schoolMapParam);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String contentStr = JSON.toJSONString(jsonObject.getJSONArray("data"));
        List teachers = JSON.parseArray(contentStr,SongdaClassTeacherDto.class);
        return teachers;
    }

    public static SongdaLoginUserInfo loginUser(String token)
    {
        Map params = Maps.newTreeMap();
        params.put("accessToken", token);
        params.put("appKey", "sundata");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(url+"/api/auth/m/user/info",params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(jsonObject.getString("code").equals("200")){
        	JSONObject data = jsonObject.getJSONObject("data");
        	String userId = data.getString("userId");
            String userNo = data.getString("userNo");
            String userName = data.getString("userName");
            String mobile = data.getString("mobile");
            String userHead = data.getString("userHead");
            String personName = data.getString("personName");
            String identity = data.getString("identity");
            SongdaLoginUserInfo dto = new SongdaLoginUserInfo(userId, userNo, userName, mobile, userHead, personName, identity);
            return dto;
        }
		return null;
        
    }

    public static String token()
    {
        Map params = Maps.newTreeMap();
        params.put("appKey", "sundata");
        //青岛
        params.put("userKey", "yanghong");
        params.put("userPassword", "sundata@2016");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(tokenUrl+"/api/auth/m/login", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        return data.getString("accessToken");
    }

    public static String token(String userName, String userPassword)
    {
        Map params = Maps.newTreeMap();
        params.put("appKey", "sundata");
        params.put("userKey", userName);
        params.put("userPassword", userPassword);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("sign", "1");
        String response = HttpClientUtil2.doJsonPost(tokenUrl+"/api/auth/m/login", params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        return data.getString("accessToken");
    }
}


