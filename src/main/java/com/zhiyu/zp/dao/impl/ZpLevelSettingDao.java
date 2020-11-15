package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpLevelSettingDao;
import com.zhiyu.zp.entity.ZpLevelSettingEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpLevelSettingDao extends CommonDaoImpl<ZpLevelSettingEntity> implements IZpLevelSettingDao{

}
