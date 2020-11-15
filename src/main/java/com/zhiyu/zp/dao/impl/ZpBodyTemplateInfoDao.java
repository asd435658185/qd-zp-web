package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpBodyTemplateInfoDao;
import com.zhiyu.zp.entity.ZpBodyTemplateInfoEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpBodyTemplateInfoDao extends CommonDaoImpl<ZpBodyTemplateInfoEntity> implements IZpBodyTemplateInfoDao{

}
