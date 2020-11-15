package com.zhiyu.zp.strategy;

import java.util.List;

import com.zhiyu.zp.dto.response.app.info.EvaluateSituationBaseDto;

/**
 * 
 * @author wdj
 *
 */

public abstract class EvaluateSituationMethodStrategy {

	public abstract List<? super EvaluateSituationBaseDto> getEvaluateSituations(Long schoolId,Long classId,Long operateId,Long bagId);
	
	public abstract Long getClassId(Long schoolId,Long operateId,Long bagId);
}
