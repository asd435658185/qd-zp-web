package com.zhiyu.zp.adapter;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.response.web.evaluate.BaseInfoDto;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpEvaluateBaseInfoAdapter {

	/**
	 * 转换实体对象
	 * @param entity
	 * @return
	 */
	public BaseInfoDto getBaseInfoFromEntity(ZpEvaluateInfoEntity entity){
		BaseInfoDto dto = new BaseInfoDto();
		dto.setEvaluateId(entity.getId());
		dto.setBagId(entity.getEvaluateBagId());
		dto.setBeginDate(entity.getBeginDate());
		dto.setEndDate(entity.getEndDate());
		dto.setEvaluateName(entity.getEvaluateName());
		dto.setUseableId(entity.getUseable().getValue());
		dto.setUseableName(entity.getUseable().getName());
		return dto;
	}
	
	public List<BaseInfoDto> getBaseInfoListFrom(List<ZpEvaluateInfoEntity> entityList){
		List<BaseInfoDto> dtos = Lists.newArrayList();
		for(ZpEvaluateInfoEntity entity:entityList){
			dtos.add(getBaseInfoFromEntity(entity));
		}
		return dtos;
	}
}
