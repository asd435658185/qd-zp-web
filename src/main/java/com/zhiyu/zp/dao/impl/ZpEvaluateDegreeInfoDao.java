package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateDegreeInfoDao;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateDegreeInfoDao extends CommonDaoImpl<ZpEvaluateDegreeInfoEntity> implements IZpEvaluateDegreeInfoDao{

}
