package com.zhiyu.zp.service.business;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.entity.StudentClassHistoryEntity;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IClassStudentRelService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.IStudentBaseInfoService;
import com.zhiyu.baseplatform.service.IStudentClassHistoryService;
import com.zhiyu.baseplatform.service.ITermBaseInfoService;
import com.zhiyu.zp.bean.ZpMarkResultEntitySubjectIdComparatorBean;
import com.zhiyu.zp.dto.response.web.mark.MarkResultContentDto;
import com.zhiyu.zp.entity.ZpBodyResultEntity;
import com.zhiyu.zp.entity.ZpMarkResultEntity;
import com.zhiyu.zp.entity.ZpMarkSubjectEntity;
import com.zhiyu.zp.enumcode.MarkType;
import com.zhiyu.zp.service.IZpBodyResultService;
import com.zhiyu.zp.service.IZpMarkResultService;
import com.zhiyu.zp.service.IZpMarkSubjectService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class MarkResultService {

	@Autowired
	private IZpMarkSubjectService zpMarkSubjectService;

	@Autowired
	private IZpMarkResultService zpMarkResultService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;

	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassStudentRelService classStudentRelService;

	@Autowired
	private IStudentBaseInfoService studentBaseInfoService;
	
	@Autowired
	private IZpBodyResultService zpBodyResultService;
	
	@Autowired
	private ITermBaseInfoService termBaseInfoService;
	
	@Autowired
	private IStudentClassHistoryService studentClassHistoryService;

	@Transactional
	public void saveByBatch(Map<String, Object> map, MarkType markType, Long schoolId, Long termId,Map<String, Object> errorResultMap) {
		String studentNo = map.get("studentNo").toString().trim();
		String newStudentNo = map.get("newStudentNo").toString().trim();
		//更新新的学籍号
		if(newStudentNo!=null&&!newStudentNo.equals("")){
			// 查询新学号是否存在
			StudentBaseInfoEntity schoolStu = new StudentBaseInfoEntity();
			schoolStu.setSchoolId(schoolId.intValue());
			List<StudentBaseInfoEntity> stuList = studentBaseInfoService.findByEntity(schoolStu);
			for(StudentBaseInfoEntity s:stuList){
				if(s.getNumber().equals(newStudentNo)){//新学号已存在
					errorResultMap.put("新学号："+newStudentNo+"已存在", 1);
					return;
				}
			}
			// 获取学生信息
			StudentBaseInfoEntity stu = new StudentBaseInfoEntity();
			stu.setNumber(studentNo);
			stu = studentBaseInfoService.findByEntity(stu).get(0);
			stu.setNumber(newStudentNo);
			studentBaseInfoService.saveOrUpdate(stu);
			
			//查找该原来学号的所有成绩记录
			ZpMarkResultEntity stuNoEntity = new ZpMarkResultEntity();
			stuNoEntity.setStudentNo(studentNo);
			List<ZpMarkResultEntity> stuNoMarks = zpMarkResultService.findByEntity(stuNoEntity);
			for(ZpMarkResultEntity s:stuNoMarks){
				s.setStudentNo(stu.getNumber());
				zpMarkResultService.update(s);
			}
			//查找该原来学号的所有体质记录
			ZpBodyResultEntity stuNoEntity1 = new ZpBodyResultEntity();
			stuNoEntity1.setStudentNo(studentNo);
			List<ZpBodyResultEntity> stuNoResults = zpBodyResultService.findByEntity(stuNoEntity1);
			for(ZpBodyResultEntity r:stuNoResults){
				r.setStudentNo(stu.getNumber());
				zpBodyResultService.update(r);
			}
		}
		try {
			for (String key : map.keySet()) {
				if (key.equals("studentNo")||key.equals("newStudentNo")) {
					continue;
				}
				ZpMarkResultEntity entity = new ZpMarkResultEntity();
				// 生成成绩基础信息
				entity.setMarkType(markType);
				entity.setTermId(termId);
				if(newStudentNo!=null&&!newStudentNo.equals("")){
					entity.setStudentNo(newStudentNo);
				}else{
					entity.setStudentNo(studentNo);
				}
				entity.setCreateTime(null);
				entity.setUpdateTime(null);
				String fieldScore = map.get(key).toString();// 对应科目的分数
				ZpMarkSubjectEntity subject = zpMarkSubjectService.findBySchoolEngCode(schoolId, key);
				entity.setSubjectId(subject.getId());
				// 判断是否已存在对应成绩
				List<ZpMarkResultEntity> dbMarks = zpMarkResultService.findByEntity(entity);
				if ( dbMarks != null && !dbMarks.isEmpty()) {
					entity = dbMarks.get(0);
					entity.setMark(fieldScore);
					entity.setUpdateTime(new Date());
					zpMarkResultService.update(entity);
				} else {
					entity.setMark(fieldScore);
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					// 获取学生信息
					StudentBaseInfoEntity stu = new StudentBaseInfoEntity();
					if(newStudentNo!=null&&!newStudentNo.equals("")){
						stu.setNumber(newStudentNo);
					}else{
						stu.setNumber(studentNo);
					}
					stu = studentBaseInfoService.findByEntity(stu).get(0);
					// 获取学生所在年级、班级
					ClassStudentRelEntity classStu = classStudentRelService.findByCondition(" and studentId=? and termId=?",
							new Object[] { stu.getId(), termId.intValue() }, null).get(0);
					entity.setGradeId(classBaseInfoService.findObjectById(classStu.getClassId()).getGradeId().longValue());
					entity.setClassId(classStu.getClassId().longValue());
					zpMarkResultService.save(entity);
				}
			}
			
		} catch (Exception e) {
			if(newStudentNo!=null&&!newStudentNo.equals("")){
			     errorResultMap.put(newStudentNo, 1);
			}else{
				errorResultMap.put(studentNo, 1);
			}
		}
		
	}

	public List<MarkResultContentDto> getMarkResults(Long schoolId, ZpMarkResultEntity result) {
		List<MarkResultContentDto> list = Lists.newArrayList();
		List<ZpMarkResultEntity> results = zpMarkResultService.findByEntity(result);
		// 分组学生数据
		Map<String, List<ZpMarkResultEntity>> studentResultMap = studentResultsMap(results);

		// 获取学生对考试类型的分组成绩结果Map
		Map<String, List<MarkResultContentDto.Mark>> markResultMap = studentMarkTypeGroupMap(studentResultMap);

		// 补充数据结构
		for (String studentNo : markResultMap.keySet()) {
			MarkResultContentDto dto = new MarkResultContentDto();
			// 获取学生信息
			StudentBaseInfoEntity stu = new StudentBaseInfoEntity();
			stu.setNumber(studentNo);
			List<StudentBaseInfoEntity> stus=studentBaseInfoService.findByEntity(stu);
			if(stus!=null&&!stus.isEmpty()){
				stu = studentBaseInfoService.findByEntity(stu).get(0);
				dto.setStudentName(stu.getName());
				dto.setStudentId(stu.getId().longValue());
				dto.setStudentNo(studentNo);
				dto.setMarks(markResultMap.get(studentNo));
				// 获取学生所在年级、班级
				TermBaseInfoEntity term = termBaseInfoService.findObjectById(result.getTermId().intValue());
				if(term.getState()==1){//当前学期
					List<ClassStudentRelEntity> csList=classStudentRelService.findByCondition(" and studentId=? and termId=?",
							new Object[] { stu.getId(), result.getTermId().intValue() }, null);
					if(csList!=null&&!csList.isEmpty()){
						ClassStudentRelEntity classStu = csList.get(0);
						dto.setGradeName(gradeBaseInfoService
								.findObjectById(classBaseInfoService.findObjectById(classStu.getClassId()).getGradeId()).getName());
						dto.setClassName(classBaseInfoService.findObjectById(classStu.getClassId()).getName());
						list.add(dto);
					}
				}else{
					List<StudentClassHistoryEntity> csList=studentClassHistoryService.findByCondition(" and studentId=? and termId=?",
							new Object[] { stu.getId(), result.getTermId().intValue() }, null);
					if(csList!=null&&!csList.isEmpty()){
						StudentClassHistoryEntity classStu = csList.get(0);
						dto.setGradeName(gradeBaseInfoService
								.findObjectById(classBaseInfoService.findObjectById(classStu.getClassId()).getGradeId()).getName());
						dto.setClassName(classBaseInfoService.findObjectById(classStu.getClassId()).getName());
						list.add(dto);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取学生成绩Map《String,list》-> 学号为键值
	 * 
	 * @param results
	 * @return
	 */
	private Map<String, List<ZpMarkResultEntity>> studentResultsMap(List<ZpMarkResultEntity> results) {
		Map<String, List<ZpMarkResultEntity>> map = Maps.newConcurrentMap();
		for (ZpMarkResultEntity r : results) {
			if (!map.containsKey(r.getStudentNo())) {
				List<ZpMarkResultEntity> list = Lists.newArrayList();
				list.add(r);
				map.put(r.getStudentNo(), list);
			} else {
				map.get(r.getStudentNo()).add(r);
			}
		}
		return map;
	}

	/**
	 * 获取学生成绩结果Map
	 * 
	 * @param studentResultMap
	 *            学号是键值
	 * @return
	 */
	private Map<String, List<MarkResultContentDto.Mark>> studentMarkTypeGroupMap(
			Map<String, List<ZpMarkResultEntity>> studentResultMap) {
		Map<String, List<MarkResultContentDto.Mark>> map = Maps.newConcurrentMap();
		for (String key : studentResultMap.keySet()) {
			List<ZpMarkResultEntity> list = studentResultMap.get(key);
			TreeMap<MarkType, List<ZpMarkResultEntity>> markTypeGroupMap = markTypeGroupMap(list);
			List<MarkResultContentDto.Mark> marks = Lists.newArrayList();
			for (MarkType type : markTypeGroupMap.keySet()) {
				Collections.sort(markTypeGroupMap.get(type), new ZpMarkResultEntitySubjectIdComparatorBean());
				MarkResultContentDto.Mark mark = new MarkResultContentDto.Mark();
				mark.setMarkTypeName(type.getName());
				mark.setMarkType(type);
				// mark.setName(name);
				List<String> scores = Lists.newArrayList();
				for (ZpMarkResultEntity zre : markTypeGroupMap.get(type)) {
					scores.add(zre.getMark());
				}
				mark.setScores(scores);
				marks.add(mark);
			}
			map.put(key, marks);
		}
		return map;
	}

	/**
	 * 对成绩类型进行分组
	 * 
	 * @param list
	 * @return
	 */
	private TreeMap<MarkType, List<ZpMarkResultEntity>> markTypeGroupMap(List<ZpMarkResultEntity> list) {
		TreeMap<MarkType, List<ZpMarkResultEntity>> map = Maps.newTreeMap();
		
		for(MarkType mt:MarkType.values()){
			List<ZpMarkResultEntity> tempList = Lists.newArrayList();
			map.put(mt, tempList);
		}
		
		for (ZpMarkResultEntity r : list) {
			if (!map.containsKey(r.getMarkType())) {
				List<ZpMarkResultEntity> sublist = Lists.newArrayList();
				sublist.add(r);
				map.put(r.getMarkType(), sublist);
			} else {
				map.get(r.getMarkType()).add(r);
			}
		}
		return map;
	}
}