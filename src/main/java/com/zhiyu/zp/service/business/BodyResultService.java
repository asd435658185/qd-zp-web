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
import com.zhiyu.zp.bean.ZpBodyResultEntityItemIdComparatorBean;
import com.zhiyu.zp.dto.response.web.body.BodyResultContentDto;
import com.zhiyu.zp.entity.ZpBodyItemEntity;
import com.zhiyu.zp.entity.ZpBodyResultEntity;
import com.zhiyu.zp.entity.ZpBodyResultLevelEntity;
import com.zhiyu.zp.entity.ZpMarkResultEntity;
import com.zhiyu.zp.enumcode.MarkType;
import com.zhiyu.zp.service.IZpBodyItemService;
import com.zhiyu.zp.service.IZpBodyResultLevelService;
import com.zhiyu.zp.service.IZpBodyResultService;
import com.zhiyu.zp.service.IZpMarkResultService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class BodyResultService {

	@Autowired
	private IZpBodyItemService zpBodyItemService;

	@Autowired
	private IZpBodyResultService zpBodyResultService;

	@Autowired
	private IClassBaseInfoService classBaseInfoService;

	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;

	@Autowired
	private IClassStudentRelService classStudentRelService;

	@Autowired
	private IStudentBaseInfoService studentBaseInfoService;

	@Autowired
	private IZpBodyResultLevelService zpBodyResultLevelService;
	
	@Autowired
	private IZpMarkResultService zpMarkResultService;
	
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
				// 生成成绩基础信息
				if (key.equals("result")) {
					ZpBodyResultLevelEntity bodyResult = new ZpBodyResultLevelEntity();
					bodyResult.setSchoolId(schoolId);
					bodyResult.setTermId(termId);
					if(newStudentNo!=null&&!newStudentNo.equals("")){
						bodyResult.setStudentNo(newStudentNo);
					}else{
						bodyResult.setStudentNo(studentNo);
					}
					if (zpBodyResultLevelService.fingByEntity(bodyResult) != null
							&& !zpBodyResultLevelService.fingByEntity(bodyResult).isEmpty()) {
						bodyResult = zpBodyResultLevelService.fingByEntity(bodyResult).get(0);
						bodyResult.setLevelId(Integer.valueOf(map.get("result").toString()));
						zpBodyResultLevelService.update(bodyResult);
					}else{
						bodyResult.setLevelId(Integer.valueOf(map.get("result").toString()));
						zpBodyResultLevelService.save(bodyResult);
					}
					
					continue;
				}
				ZpBodyResultEntity entity = new ZpBodyResultEntity();
				entity.setMarkType(markType);
				entity.setTermId(termId);
				if(newStudentNo!=null&&!newStudentNo.equals("")){
					entity.setStudentNo(newStudentNo);
				}else{
					entity.setStudentNo(studentNo);
				}
				entity.setCreateTime(null);
				entity.setUpdateTime(null);
				String testResult = map.get(key).toString();// 对应测试项的结果
				ZpBodyItemEntity subject = zpBodyItemService.findBySchoolEngCode(schoolId, key);
				entity.setItemId(subject.getId());
				// 判断是否已存在对应体质
				if (zpBodyResultService.findByEntity(entity) != null
						&& !zpBodyResultService.findByEntity(entity).isEmpty()) {
					entity = zpBodyResultService.findByEntity(entity).get(0);
					entity.setResult(testResult);
					entity.setUpdateTime(new Date());
					zpBodyResultService.update(entity);
				} else {
					entity.setResult(testResult);
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
					zpBodyResultService.save(entity);
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

	public List<BodyResultContentDto> getBodyResults(Long schoolId, ZpBodyResultEntity result) {
		List<BodyResultContentDto> list = Lists.newArrayList();
		List<ZpBodyResultEntity> results = zpBodyResultService.findByEntity(result);
		// 分组学生数据
		Map<String, List<ZpBodyResultEntity>> studentResultMap = studentResultsMap(results);

		// 获取学生对考试类型的分组成绩结果Map
		Map<String, List<BodyResultContentDto.Mark>> markResultMap = studentMarkTypeGroupMap(studentResultMap);

		// 补充数据结构
		for (String studentNo : markResultMap.keySet()) {
			BodyResultContentDto dto = new BodyResultContentDto();
			// 获取学生信息
			StudentBaseInfoEntity stu = new StudentBaseInfoEntity();
			stu.setNumber(studentNo);
			List<StudentBaseInfoEntity> stus=studentBaseInfoService.findByEntity(stu);
				if(stus!=null&&!stus.isEmpty()){
				stu = studentBaseInfoService.findByEntity(stu).get(0);
				dto.setStudentName(stu.getName());
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
	private Map<String, List<ZpBodyResultEntity>> studentResultsMap(List<ZpBodyResultEntity> results) {
		Map<String, List<ZpBodyResultEntity>> map = Maps.newConcurrentMap();
		for (ZpBodyResultEntity r : results) {
			if (!map.containsKey(r.getStudentNo())) {
				List<ZpBodyResultEntity> list = Lists.newArrayList();
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
	private Map<String, List<BodyResultContentDto.Mark>> studentMarkTypeGroupMap(
			Map<String, List<ZpBodyResultEntity>> studentResultMap) {
		Map<String, List<BodyResultContentDto.Mark>> map = Maps.newConcurrentMap();
		for (String key : studentResultMap.keySet()) {
			List<ZpBodyResultEntity> list = studentResultMap.get(key);
			TreeMap<MarkType, List<ZpBodyResultEntity>> markTypeGroupMap = markTypeGroupMap(list);
			List<BodyResultContentDto.Mark> marks = Lists.newArrayList();
			for (MarkType type : markTypeGroupMap.keySet()) {
				Collections.sort(markTypeGroupMap.get(type), new ZpBodyResultEntityItemIdComparatorBean());
				BodyResultContentDto.Mark mark = new BodyResultContentDto.Mark();
				mark.setMarkTypeName(type.getName());
				// mark.setName(name);
				List<String> values = Lists.newArrayList();
				for (ZpBodyResultEntity zre : markTypeGroupMap.get(type)) {
					values.add(zre.getResult());
				}
				mark.setValues(values);
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
	private TreeMap<MarkType, List<ZpBodyResultEntity>> markTypeGroupMap(List<ZpBodyResultEntity> list) {
		TreeMap<MarkType, List<ZpBodyResultEntity>> map = Maps.newTreeMap();
		for (ZpBodyResultEntity r : list) {
			if (!map.containsKey(r.getMarkType())) {
				List<ZpBodyResultEntity> sublist = Lists.newArrayList();
				sublist.add(r);
				map.put(r.getMarkType(), sublist);
			} else {
				map.get(r.getMarkType()).add(r);
			}
		}
		return map;
	}
}