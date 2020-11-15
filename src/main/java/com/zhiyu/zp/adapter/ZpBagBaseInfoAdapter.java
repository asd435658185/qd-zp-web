package com.zhiyu.zp.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zhiyu.baseplatform.entity.GradeBaseInfoEntity;
import com.zhiyu.baseplatform.service.IGradeBaseInfoService;
import com.zhiyu.baseplatform.service.ITermBaseInfoService;
import com.zhiyu.zp.dto.response.web.bag.BagBaseInfo;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.entity.ZpEvaluateDegreeVersionEntity;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;
import com.zhiyu.zp.service.IZpEvaluateDegreeVersionService;

/**
 * 
 * @author wdj
 *
 */
@Component
public class ZpBagBaseInfoAdapter {

	@Autowired
	private ITermBaseInfoService termBaseInfoService;
	
	@Autowired
	private IZpEvaluateDegreeVersionService evaluateDegreeVersionService;
	
	@Autowired
	private IZpEvaluateDegreeInfoService evaluateDegreeInfoService;
	
	@Autowired
	private IGradeBaseInfoService gradeBaseInfoService;
	
	public BagBaseInfo getFromEntity(ZpEvaluateBagEntity entity){
		BagBaseInfo dto = new BagBaseInfo();
		dto.setBagId(entity.getId());
		dto.setBagName(entity.getBagName());
		dto.setUseableId(entity.getUseable().getValue());
		dto.setUseableName(entity.getUseable().getName());
		dto.setTermId(entity.getTermId());
		dto.setTermName(termBaseInfoService.findObjectById(entity.getTermId().intValue()).getName());
		dto.setVersionId(entity.getVersionId());
		if(entity.getVersionId()!=null){
			ZpEvaluateDegreeVersionEntity version=evaluateDegreeVersionService.findById(entity.getVersionId().longValue());
			if(version!=null){
				dto.setVersionName(version.getVersionName());
			}
		}
		dto.setTopDegrees(entity.getTopDegrees());
		if(entity.getTopDegrees()!=null){
			String topDegreeNames="";
			String[] arr = entity.getTopDegrees().split(",");
			for (String id : arr) {
				if (!id.equals("")) {
					ZpEvaluateDegreeInfoEntity d=evaluateDegreeInfoService.findById(Long.valueOf(id));
					if(d!=null){
						if(topDegreeNames.equals("")){
							topDegreeNames=topDegreeNames+d.getDname();
						}else{
							topDegreeNames=topDegreeNames+","+d.getDname();
						}
					}
				}
			}
			dto.setTopDegreeNames(topDegreeNames);	
		}
		dto.setGradeIds(entity.getGradeIds());
		if(entity.getGradeIds()!=null){
			String gradeNames="";
			String[] arr1 = entity.getGradeIds().split(",");
			for (String id : arr1) {
				if (!id.equals("")) {
					GradeBaseInfoEntity g=gradeBaseInfoService.findObjectById(Integer.valueOf(id));
					if(g!=null){
						if(gradeNames.equals("")){
							gradeNames=gradeNames+g.getName();
						}else{
							gradeNames=gradeNames+","+g.getName();
						}
					}
				}
			}
			dto.setGradeNames(gradeNames);
		}
		return dto;
	}
	
	public List<BagBaseInfo> getListFromEntityList(List<ZpEvaluateBagEntity> entityList){
		List<BagBaseInfo> dtos = Lists.newArrayList();
		for(ZpEvaluateBagEntity entity:entityList){
			dtos.add(getFromEntity(entity));
		}
		return dtos;
	}
}
