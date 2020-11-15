package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpMarkSubjectDao;
import com.zhiyu.zp.entity.ZpMarkSubjectEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpMarkSubjectDao extends CommonDaoImpl<ZpMarkSubjectEntity> implements IZpMarkSubjectDao{

}
