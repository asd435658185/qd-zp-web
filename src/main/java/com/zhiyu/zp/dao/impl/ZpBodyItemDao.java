package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpBodyItemDao;
import com.zhiyu.zp.entity.ZpBodyItemEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpBodyItemDao extends CommonDaoImpl<ZpBodyItemEntity> implements IZpBodyItemDao{

}
