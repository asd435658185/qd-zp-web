package com.zhiyu.zp.exception;

/**
 * 
 * @author wdj
 *
 */

public class EvaluateBagSettingException extends Exception {

	private static final long serialVersionUID = 6503877127794954193L;

	@Override
	public String getMessage() {
		return "评估报告册设置有问题，请仔细排查";
	}

}
