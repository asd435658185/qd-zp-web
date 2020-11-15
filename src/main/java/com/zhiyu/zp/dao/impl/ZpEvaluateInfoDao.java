package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateInfoDao;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateInfoDao extends CommonDaoImpl<ZpEvaluateInfoEntity> implements IZpEvaluateInfoDao{

}
