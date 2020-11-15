package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateDetailDao;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateDetailDao extends CommonDaoImpl<ZpEvaluateDetailEntity> implements IZpEvaluateDetailDao{

}
