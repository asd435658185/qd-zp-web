package com.zhiyu.zp.adapter;

import com.zhiyu.zp.dto.response.web.degree.BaseDegreeDescDto;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeInfoDto;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeValidDto;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;

/**
 * 
 * @author wdj
 *
 */

public class ZpEvaluateDegreeInfoAdapter {

	/**
	 * 获取维度基本信息
	 * @param entity
	 * @return
	 */
	public BaseDegreeInfoDto getBaseInfoFromEntity(ZpEvaluateDegreeInfoEntity entity){
		BaseDegreeInfoDto dto = new BaseDegreeInfoDto();
		dto.setDegreeId(entity.getId());
		dto.setDegreeName(entity.getDname());
		dto.setVersionId(entity.getVersionId());
		return dto;
	}
	
	/**
	 * 获取维度基本信息+描述信息
	 * @param entity
	 * @return
	 */
	public BaseDegreeDescDto getBaseDescInfoFromEntity(ZpEvaluateDegreeInfoEntity entity){
		BaseDegreeDescDto dto = new BaseDegreeDescDto();
		dto.setDegreeId(entity.getId());
		dto.setDegreeName(entity.getDname());
		dto.setDegreeDesc(entity.getDescription());
		dto.setVersionId(entity.getVersionId());
		return dto;
	}
	
	/**
	 * 获取维度基本信息+描述信息+有效性
	 * @param entity
	 * @return
	 */
	public BaseDegreeValidDto getBaseValidInfoFromEntity(ZpEvaluateDegreeInfoEntity entity){
		BaseDegreeValidDto dto = new BaseDegreeValidDto();
		dto.setDegreeId(entity.getId());
		dto.setDegreeName(entity.getDname());
		dto.setDegreeDesc(entity.getDescription());
		dto.setUseableId(entity.getUseable().getValue());
		dto.setUseableName(entity.getUseable().getName());
		dto.setVersionId(entity.getVersionId());
		return dto;
	}
	
	/**
	 * 将维度页节点信息转成实体对象
	 * @param schoolId
	 * @param degreeType
	 * @param degreePicId
	 * @param degreeName
	 * @param degreeDesc
	 * @return
	 */
	public ZpEvaluateDegreeInfoEntity getEntityFromLeafDegreeInfo(Long schoolId,int degreeType,Long degreePicId,String degreeName,String degreeDesc,Integer versionId){
		ZpEvaluateDegreeInfoEntity entity = new ZpEvaluateDegreeInfoEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setDegreePicId(degreePicId);
		entity.setDegreeType(DegreeType.getType(degreeType));
		entity.setDescription(degreeDesc);
		entity.setDname(degreeName);
		entity.setSchoolId(schoolId);
		entity.setVersionId(versionId);
		return entity;
	}
}
