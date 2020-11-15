package com.zhiyu.zp.dto.response.web.gathersetting;

import com.zhiyu.zp.dto.response.web.bag.BagBaseInfo;

/**
 * 
 * @author wdj
 *
 */

public class BagBaseSetInfo extends BagBaseInfo{

	private boolean canSet;

	public boolean isCanSet() {
		return canSet;
	}

	public void setCanSet(boolean canSet) {
		this.canSet = canSet;
	}
	
	
}
