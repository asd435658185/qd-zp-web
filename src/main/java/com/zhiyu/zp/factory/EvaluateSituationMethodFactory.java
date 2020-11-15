package com.zhiyu.zp.factory;

import com.zhiyu.baseplatform.service.SpringContextHolder;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.strategy.EvaluateSituationMethodStrategy;

/**
 * 
 * @author wdj
 *
 */

public class EvaluateSituationMethodFactory {

	private EvaluateSituationMethodStrategy evaluateSituationMethodStrategy;
	
	/**
	 * 获取次评情况实例
	 * @param method
	 * @return
	 */
	public EvaluateSituationMethodStrategy getInstance(EvaluateMethod method){
		if(method.equals(EvaluateMethod.SELF)){
			evaluateSituationMethodStrategy = SpringContextHolder.getBean("evaluateSituationSelfMethod");
		}else if(method.equals(EvaluateMethod.PARENT)){
			evaluateSituationMethodStrategy = SpringContextHolder.getBean("evaluateSituationParentMethod");
		}else{
			evaluateSituationMethodStrategy = SpringContextHolder.getBean("evaluateSituationTeacherMethod");
		}
		return evaluateSituationMethodStrategy;
	}
}
