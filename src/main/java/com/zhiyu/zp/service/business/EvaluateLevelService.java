package com.zhiyu.zp.service.business;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.baseplatform.entity.ClassBaseInfoEntity;
import com.zhiyu.baseplatform.service.IClassBaseInfoService;
import com.zhiyu.zp.bean.ZpEvaluateLevelSettingMaxValueComparatorBean;
import com.zhiyu.zp.bean.ZpEvaluateWeightSettingResValueComparatorBean;
import com.zhiyu.zp.entity.ZpGradeSettingEntity;
import com.zhiyu.zp.entity.ZpLevelSettingEntity;
import com.zhiyu.zp.enumcode.EvaluateLevelType;
import com.zhiyu.zp.exception.EvaluateEventualResultLevelSettingException;
import com.zhiyu.zp.exception.EvaluateGradeLevelSettingException;
import com.zhiyu.zp.service.IZpGradeSettingService;
import com.zhiyu.zp.service.IZpLevelSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class EvaluateLevelService {

	@Autowired
	private IZpLevelSettingService zpLevelSettingService;
	
	@Autowired
	private IZpGradeSettingService zpGradeSettingService;
	
	@Autowired
	private IClassBaseInfoService classBaseInfoService;
	
	/**
	 * 获取等级比重评估后的数值
	 * @param bagId
	 * @param degreeId
	 * @param value  比重后转成的整型数据
	 * @return
	 * @throws EvaluateGradeLevelSettingException 
	 * @throws EvaluateEventualResultLevelSettingException 
	 */
	public EvaluateLevelType getEventualResValue(Long schoolId,Long classId,Integer value) throws EvaluateGradeLevelSettingException, EvaluateEventualResultLevelSettingException{
		EvaluateLevelType resValue = EvaluateLevelType.FINE;
		ClassBaseInfoEntity clz = classBaseInfoService.findObjectById(classId.intValue());
		ZpGradeSettingEntity gradeSet = zpGradeSettingService.findBySchoolIdAndGradeId(schoolId, clz.getGradeId().longValue());
		if(gradeSet==null){
			throw new EvaluateGradeLevelSettingException();
		}
		List<ZpLevelSettingEntity> levelSets = zpLevelSettingService.findByLevelId(schoolId,gradeSet.getLevelId().getValue().longValue());
		if(levelSets==null || levelSets.isEmpty()){
			throw new EvaluateEventualResultLevelSettingException();
		}
		//按最大值排序
		Collections.sort(levelSets, new ZpEvaluateLevelSettingMaxValueComparatorBean());
		boolean isFind = false;
		for(ZpLevelSettingEntity entity:levelSets){
			if(entity.getMinValue()<=value && value<entity.getMaxValue()){
				resValue = entity.getEvaluateLevelType();
				isFind = true;
				break;
			}
		}
		if(!isFind){
			if(levelSets!=null && !levelSets.isEmpty()){
				resValue = levelSets.get(levelSets.size()-1).getEvaluateLevelType();
			}
		}
		return resValue;
	}
}
