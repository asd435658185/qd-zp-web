package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpGradeSettingDao;
import com.zhiyu.zp.entity.ZpGradeSettingEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpGradeSettingDao extends CommonDaoImpl<ZpGradeSettingEntity> implements IZpGradeSettingDao{

}
