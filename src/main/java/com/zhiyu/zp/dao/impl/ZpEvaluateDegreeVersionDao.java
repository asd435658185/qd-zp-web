package com.zhiyu.zp.dao.impl;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.zhiyu.baseplatform.dao.base.impl.CommonDaoImpl;
import com.zhiyu.zp.dao.IZpEvaluateDegreeVersionDao;
import com.zhiyu.zp.entity.ZpEvaluateDegreeVersionEntity;

/**
 * 
 * @author wdj
 *
 */
@Transactional
@Repository
public class ZpEvaluateDegreeVersionDao  extends CommonDaoImpl<ZpEvaluateDegreeVersionEntity> implements IZpEvaluateDegreeVersionDao{

}
