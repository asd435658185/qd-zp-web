package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpMarkResultDao;
import com.zhiyu.zp.entity.ZpMarkResultEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpMarkResultDao extends CommonDaoImpl<ZpMarkResultEntity> implements IZpMarkResultDao{

}
