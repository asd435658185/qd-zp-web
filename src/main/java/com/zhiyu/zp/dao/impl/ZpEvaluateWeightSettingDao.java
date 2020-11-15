package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateWeightSettingDao;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateWeightSettingDao extends CommonDaoImpl<ZpEvaluateWeightSettingEntity> implements IZpEvaluateWeightSettingDao{

}
