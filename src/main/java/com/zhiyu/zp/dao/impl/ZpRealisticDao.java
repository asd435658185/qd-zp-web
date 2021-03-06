package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpRealisticDao;
import com.zhiyu.zp.entity.ZpRealisticEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpRealisticDao extends CommonDaoImpl<ZpRealisticEntity> implements IZpRealisticDao{

}
