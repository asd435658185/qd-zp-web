package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateBagDao;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateBagDao extends CommonDaoImpl<ZpEvaluateBagEntity> implements IZpEvaluateBagDao{

}
