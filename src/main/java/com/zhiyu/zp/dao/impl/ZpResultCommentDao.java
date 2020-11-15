package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpResultCommentDao;
import com.zhiyu.zp.entity.ZpResultCommentEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpResultCommentDao extends CommonDaoImpl<ZpResultCommentEntity> implements IZpResultCommentDao{

}
