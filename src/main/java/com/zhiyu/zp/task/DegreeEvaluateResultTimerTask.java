package com.zhiyu.zp.task;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.entity.ClassStudentRelEntity;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.service.ClassStudentRelationHandler;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.SchoolTermHandler;
import com.zhiyu.zp.collection.DegreeEvaluateCollection;
import com.zhiyu.zp.dao.impl.DegreeCollectionDao;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.entity.ZpSchoolSettingEntity;
import com.zhiyu.zp.processor.CollectionIdProcessor;
import com.zhiyu.zp.service.IZpSchoolSettingService;
import com.zhiyu.zp.service.business.EvaluateResultService;

/**
 * 数据更新
 * 
 * @author wdj
 *
 */
@Component("degreeEvaluateResultTimerTask")
public class DegreeEvaluateResultTimerTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

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
	private EvaluateResultService evaluateResultService;
	
	@Autowired
	private DegreeCollectionDao degreeCollectionDao;

	@Scheduled(cron = "0 18 17 ? * Thu ") // 每周六凌晨两点执行
	public void count() {
		long start = new Date().getTime();
		logger.info("开始执行数据更新操作");

		List<ZpSchoolSettingEntity> list = zpSchoolSettingService.findAll();
		for (ZpSchoolSettingEntity e : list) {
			if (e.getSchoolId()==null) {
				logger.debug("该学校更新账户信息有缺失，对方学校id未设定");
				continue;
			}
			Long schoolId = e.getSchoolId();
			// 查询该学校的当前学期
			TermBaseInfoEntity term = termHandlerService.getCurrentTermInfoBySchoolId(schoolId.intValue());
			if (term == null) {
				logger.debug("该学校对应学期信息有缺失，对方学校id为{}", schoolId);
				continue;
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
					List<ClassBaseInfoEntity> clzSubList = classBaseService.findByEntity(clz);
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
							logger.error("生成综评结果失败，已经生成了，不允许再次更新：classId={},studentId={}",rel.getClassId(),rel.getStudentId());
							continue;
						}else{
							//汇总个人
							saveStudentPersonal(evaluateResultDto, collection, schoolId, rel.getClassId().longValue(), term.getId(), rel.getStudentId().longValue());
							//汇总对应班级各维度结果
							saveClassDegree(evaluateResultDto, classCollection, schoolId, rel.getClassId().longValue(), term.getId());
							//汇总对应年级各维度结果
							saveGradeDegree(evaluateResultDto, gradeCollection, schoolId, gradeId.longValue(), term.getId());
						}
					} catch (Exception e1) {
						logger.error("生成综评结果失败：classId={},studentId={}",rel.getClassId(),rel.getStudentId());
					}
					
				}
			}
		}

		long end = new Date().getTime();
		logger.info("执行周评分汇总总共花费了{}秒", (end - start) / 1000);
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
