package com.zhiyu.zp.service.business;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.bean.ZpEvaluateLevelSettingMaxValueComparatorBean;
import com.zhiyu.zp.bean.ZpEvaluateWeightSettingMaxValueComparatorBean;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;
import com.zhiyu.zp.service.IZpEvaluateWeightSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class EvaluateWeightService {

	@Autowired
	private IZpEvaluateWeightSettingService zpEvaluateWeightSettingService;
	
	/**
	 * 获取次评的比重评估后的数值
	 * @param bagId
	 * @param degreeId
	 * @param value  比重后转成的整型数据
	 * @return
	 */
	public Integer getTimeEvaluateDegreeResValue(Long bagId,Long degreeId,Integer value){
		Integer resValue = 1;
		List<ZpEvaluateWeightSettingEntity> entityList = zpEvaluateWeightSettingService.findRegularTimeEvaluateWeightSetting(bagId, degreeId);
		//按最大值排序
		Collections.sort(entityList, new ZpEvaluateWeightSettingMaxValueComparatorBean());
		boolean isFind = false;
		for(ZpEvaluateWeightSettingEntity entity:entityList){
			if(entity.getMinValue()<=value && value<entity.getMaxValue()){
				resValue = entity.getResValue();
				isFind = true;
				break;
			}
		}
		if(!isFind){
			if(entityList!=null && !entityList.isEmpty()){
				if(value==100){//当值是最大值时
					resValue = entityList.get(entityList.size()-1).getResValue();
				}
			}
		}
		return resValue;
	}
	
	/**
	 * 获取终评的比重评估后的数值
	 * @param bagId
	 * @param degreeId
	 * @param value  比重后转成的整型数据
	 * @return
	 */
	public Integer getEventualEvaluateDegreeResValue(Long bagId,Integer value){
		Integer resValue = 0;
		List<ZpEvaluateWeightSettingEntity> entityList = zpEvaluateWeightSettingService.findRegularGatherEvaluateWeightSetting(bagId);
		//按最大值排序
		Collections.sort(entityList, new ZpEvaluateWeightSettingMaxValueComparatorBean());
		boolean isFind = false;
		for(ZpEvaluateWeightSettingEntity entity:entityList){
			if(entity.getMinValue()<=value && value<entity.getMaxValue()){
				resValue = entity.getResValue();
				isFind = true;
				break;
			}
		}
		if(!isFind || value.intValue()==100){
			if(entityList!=null && !entityList.isEmpty()){
				resValue = entityList.get(entityList.size()-1).getResValue();
			}
		}
		return resValue;
	}
}
