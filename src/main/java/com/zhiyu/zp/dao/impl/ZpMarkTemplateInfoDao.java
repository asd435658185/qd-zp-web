package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpMarkTemplateInfoDao;
import com.zhiyu.zp.entity.ZpMarkTemplateInfoEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpMarkTemplateInfoDao extends CommonDaoImpl<ZpMarkTemplateInfoEntity> implements IZpMarkTemplateInfoDao{

}
