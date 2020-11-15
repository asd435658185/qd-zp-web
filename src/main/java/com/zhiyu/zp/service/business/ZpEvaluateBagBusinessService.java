package com.zhiyu.zp.service.business;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.baseplatform.entity.TermBaseInfoEntity;
import com.zhiyu.baseplatform.service.SchoolTermHandler;
import com.zhiyu.zp.bean.ZpEvaluateBagTermComparatorBean;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.exception.EvaluateBagSettingException;
import com.zhiyu.zp.service.IZpEvaluateBagService;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateBagBusinessService {

	@Autowired
	private IZpEvaluateDegreeInfoService zpEvaluateDegreeInfoService;
	
	@Autowired
	private IZpEvaluateBagService zpEvaluateBagService;
	
	@Autowired
	private SchoolTermHandler termHandler;
	
	/**
	 * 获取要查询的学期id
	 * @param schoolId
	 * @param termId
	 * @return
	 */
	public Long findSearchTermId(Long schoolId, Long termId){
		Long searchTermId = null;
		if (termId != null) {
			searchTermId = termId;
		} else {
			TermBaseInfoEntity term = termHandler.getCurrentTermInfoBySchoolId(schoolId.intValue());
			searchTermId = term.getId().longValue();
		}
		return searchTermId;
	}
	
	/**
	 * 获取学校当前学期生效的评价报告册集合
	 * @param schoolId
	 * @param termId
	 * @return
	 * @throws EvaluateBagSettingException
	 */
	public List<ZpEvaluateBagEntity> getCurrentSchoolBag(Long schoolId,Long termId) throws EvaluateBagSettingException{
		ZpEvaluateBagEntity entity = new ZpEvaluateBagEntity();
		entity.setUseable(Useable.USEABLE);
		Long searchTermId = findSearchTermId(schoolId,termId);
		List<ZpEvaluateBagEntity> entityList = null;
		if(searchTermId!=null){
			entity.setTermId(searchTermId);
			entity.setSchoolId(schoolId);
			entityList = zpEvaluateBagService.findByEntity(entity);
			if(entityList==null || entityList.isEmpty()){
				throw new EvaluateBagSettingException();
			}
		}else{
			throw new EvaluateBagSettingException();
		}
		return entityList;
	}
	
	/**
	 * 获取学校展示的报告册列表
	 * @param schoolId
	 * @return
	 */
	public List<ZpEvaluateBagEntity> getSchoolDisplayBags(Long schoolId,String bagName){
		List<ZpEvaluateBagEntity> entityList = null;
		Map<String, String> sortMap = Maps.newConcurrentMap();
		sortMap.put("termId", "desc");
		List<Object> params = Lists.newArrayList();
		StringBuffer condition = new StringBuffer(" and schoolId=? ");
		params.add(schoolId);
		if(StringUtils.isNotBlank(bagName)){
			condition.append(" and bagName like ? ");
			params.add("%"+bagName+"%");
		}
		condition.append(" and dataState=? ");
		params.add(DataState.NORMAL);
		entityList = zpEvaluateBagService.findListByCondition(condition.toString(), params.toArray(),sortMap);
		Collections.sort(entityList,new ZpEvaluateBagTermComparatorBean());
		return  entityList;
	}
	
	/**
	 * 新增报告册
	 * @param schoolId
	 * @param bagName
	 * @throws EvaluateBagSettingException 
	 */
	public String addNewBag(Long schoolId,Long termId,String bagName,Integer versionId,String gradeIds) throws EvaluateBagSettingException{
		String msg="数据更新成功";
		ZpEvaluateBagEntity bag = new ZpEvaluateBagEntity();
		bag.setUseable(Useable.UNUSEABLE);
		bag.setBagName(bagName);
		bag.setDataState(DataState.NORMAL);
		bag.setSchoolId(schoolId);
		bag.setTermId(findSearchTermId(schoolId,termId));
		ZpEvaluateDegreeInfoEntity degreeInfo=new ZpEvaluateDegreeInfoEntity();
		degreeInfo.setVersionId(versionId);
		degreeInfo.setPid(0L);
		degreeInfo.setSchoolId(schoolId);
		degreeInfo.setDataState(DataState.NORMAL);
		List<ZpEvaluateDegreeInfoEntity> topDegreeList = zpEvaluateDegreeInfoService.findByEntity(degreeInfo);
		StringBuilder sb = new StringBuilder();
		for(ZpEvaluateDegreeInfoEntity degree:topDegreeList){
			if(degree.getSchoolId().longValue()!=schoolId.longValue()){
				continue;
			}
			if(degree.getUseable().equals(Useable.USEABLE)){//过滤出生效的维度
				sb.append(degree.getId()).append(",");
			}
		}
		
		if(topDegreeList!=null && !topDegreeList.isEmpty()){
			if(sb.length()>0){
				bag.setTopDegrees(sb.substring(0, sb.length()-1));
			}else{
				msg="该版本下的维度未生效";
				return msg;
			}
		}
		List<String> gradeList=Lists.newArrayList();
		List<Integer> versionIdList=Lists.newArrayList();
		ZpEvaluateBagEntity eBag = new ZpEvaluateBagEntity();
		eBag.setSchoolId(schoolId);
		eBag.setTermId(findSearchTermId(schoolId,termId));
		eBag.setDataState(DataState.NORMAL);
		List<ZpEvaluateBagEntity> eBagList=zpEvaluateBagService.findByEntity(eBag);
		for(ZpEvaluateBagEntity e:eBagList){
			String[] grades = e.getGradeIds().split(",");
			for (String s : grades) {
				gradeList.add(s);
			}
			versionIdList.add(e.getVersionId());
		}
		String[] grades = gradeIds.split(",");
		for (String s : grades) {
			for(String g:gradeList){
				if(s.equals(g)){
					msg="该年级id"+s+"已被选择过";
					return msg;
				}
			}
		}
		for (Integer v : versionIdList) {
			if(versionId==v){
				msg="该版本id"+v+"已被选择过";
				return msg;
			}
	    }
		bag.setGradeIds(gradeIds);
		bag.setVersionId(versionId);
		zpEvaluateBagService.save(bag);
		return msg;
	}
	
	/**
	 * 修改报告册
	 * @param schoolId
	 * @param bagName
	 * @throws EvaluateBagSettingException 
	 */
	public String updateOldBag(Long schoolId,Long bagId,String bagName,Integer versionId,String gradeIds) throws EvaluateBagSettingException{
		String msg="数据更新成功";
		ZpEvaluateBagEntity bag = zpEvaluateBagService.findById(bagId);
		bag.setBagName(bagName);
		ZpEvaluateDegreeInfoEntity degreeInfo=new ZpEvaluateDegreeInfoEntity();
		degreeInfo.setVersionId(versionId);
		degreeInfo.setPid(0L);
		degreeInfo.setSchoolId(schoolId);
		degreeInfo.setDataState(DataState.NORMAL);
		List<ZpEvaluateDegreeInfoEntity> topDegreeList = zpEvaluateDegreeInfoService.findByEntity(degreeInfo);
		StringBuilder sb = new StringBuilder();
		for(ZpEvaluateDegreeInfoEntity degree:topDegreeList){
			if(degree.getSchoolId().longValue()!=schoolId.longValue()){
				continue;
			}
			if(degree.getUseable().equals(Useable.USEABLE)){//过滤出生效的维度
				sb.append(degree.getId()).append(",");
			}
		}
		
		if(topDegreeList!=null && !topDegreeList.isEmpty()){
			if(sb.length()>0){
				bag.setTopDegrees(sb.substring(0, sb.length()-1));
			}else{
				msg="该版本下的维度未生效";
				return msg;
			}
		}
		List<String> gradeList=Lists.newArrayList();
		List<Integer> versionIdList=Lists.newArrayList();
		ZpEvaluateBagEntity eBag = new ZpEvaluateBagEntity();
		eBag.setSchoolId(schoolId);
		eBag.setTermId(findSearchTermId(schoolId,bag.getTermId()));
		eBag.setDataState(DataState.NORMAL);
		List<ZpEvaluateBagEntity> eBagList=zpEvaluateBagService.findByEntity(eBag);
		for(ZpEvaluateBagEntity e:eBagList){
			if(bagId!=e.getId()){
				String[] grades = e.getGradeIds().split(",");
				for (String s : grades) {
					gradeList.add(s);
				}
				versionIdList.add(e.getVersionId());
			}
		}
		String[] grades = gradeIds.split(",");
		for (String s : grades) {
			for(String g:gradeList){
				if(s.equals(g)){
					msg="该年级id"+s+"已被选择过";
					return msg;
				}
			}
		}
		for (Integer v : versionIdList) {
				if(versionId==v){
					msg="该版本id"+v+"已被选择过";
					return msg;
				}
		}
		bag.setGradeIds(gradeIds);
		bag.setVersionId(versionId);
		zpEvaluateBagService.update(bag);
		return msg;
	}
	
}
