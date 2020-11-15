package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpSchoolSettingDao;
import com.zhiyu.zp.entity.ZpSchoolSettingEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpSchoolSettingDao extends CommonDaoImpl<ZpSchoolSettingEntity> implements IZpSchoolSettingDao{

}
