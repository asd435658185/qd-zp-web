package com.zhiyu.zp.service.business;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.zp.bean.EvaluateDetailsCountBean;
import com.zhiyu.zp.dto.common.DegreeValuePair;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.service.IZpEvaluateDetailService;

/**
 * 评估明细服务类
 * 
 * @author wdj
 *
 */
@Service
public class EvaluateDetailService {

	@Autowired
	private IZpEvaluateDetailService zpEvaluateDetailService;

	/**
	 * 次评的明细记录是否已经评价结束（结束了就代表这个相应的学生的相应的次评已经评价结束）
	 * 
	 * @param evaluateResultId
	 * @return
	 */
	protected boolean isTimeEvaluateDetailEnd(Long evaluateResultId) {
		EvaluateMethod[] evaluateMethods = EvaluateMethod.values();
		List<EvaluateMethod> methods = Lists.newArrayList();
		boolean expectGather = true;
		// 先判断，明细表中一个学生的次评记录是否
		for (EvaluateMethod method : evaluateMethods) {
			methods.add(method);
			List<ZpEvaluateDetailEntity> details = zpEvaluateDetailService.findRegularResultDetailList(evaluateResultId,
					method, null);
			if (details == null || details.isEmpty()) {
				expectGather = false;
			}
		}
		return expectGather;
	}

	/**
	 * 批量保存评估明细
	 * 
	 * @param studentId
	 * @param resultId
	 * @param schoolId
	 * @param classId
	 * @param evaluateId
	 * @param items
	 * @param method
	 * @param operaterId
	 */
	@Transactional
	public void saveDetailsByBatch(Long studentId, Long resultId, Long classId, Long evaluateId,
			List<DegreeValuePair> items, EvaluateMethod method, Long operaterId) {
		for (DegreeValuePair valuePair : items) {
			ZpEvaluateDetailEntity detail = null;
			List<ZpEvaluateDetailEntity> details = zpEvaluateDetailService.findRegularResultDetailList(resultId, method, valuePair.getDegreeId());
			if(details!=null && !details.isEmpty()){
				detail = details.get(0);
			}else{
				detail = new ZpEvaluateDetailEntity();
				detail.setDataState(DataState.NORMAL);
				detail.setResultId(resultId);
				detail.setEvaluateMethod(method);
				detail.setDegreeId(valuePair.getDegreeId());
			}
			detail.setValue(valuePair.getValue());
			detail.setOperaterId(operaterId);
			if(detail.getId()==null){
				zpEvaluateDetailService.save(detail);
			}else{
				zpEvaluateDetailService.update(detail);
			}
		}
	}

	/**
	 * 传入次评结果id和维度id（这个是无孩子维度），以及要统计的方式列表
	 * 
	 * @param resultId
	 * @param degreeId
	 * @param methods
	 * @return
	 */
	public EvaluateDetailsCountBean getTotalDetailValue(Long resultId, Long degreeId, List<EvaluateMethod> methods) {
		EvaluateDetailsCountBean detailCountBean = new EvaluateDetailsCountBean();
		Map<EvaluateMethod, Integer> detailValueMap = Maps.newConcurrentMap();
		Map<EvaluateMethod, Integer> detailIdMap = Maps.newConcurrentMap();
		Integer totalNum = 0;// 各种评估方式的总数统计
		
		for (EvaluateMethod method : methods) {
			List<ZpEvaluateDetailEntity> detailList = zpEvaluateDetailService.findRegularResultDetailList(resultId,
					method, degreeId);
			Integer value = 0;
			Integer id = 0;
			if (!detailList.isEmpty()) {
				value = detailList.get(0).getValue();
				id = Integer.valueOf(detailList.get(0).getOperaterId()+"");
				totalNum += value;
			}
			detailValueMap.put(method,value);
			detailIdMap.put(method, id);
		}
		detailCountBean.setDetailValueMap(detailValueMap);
		detailCountBean.setDetailIdMap(detailIdMap);
		detailCountBean.setTotalMethodsValue(totalNum);
		return detailCountBean;
	}
}
