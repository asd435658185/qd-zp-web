package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpBodyResultDao;
import com.zhiyu.zp.entity.ZpBodyResultEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpBodyResultDao extends CommonDaoImpl<ZpBodyResultEntity> implements IZpBodyResultDao{

}
