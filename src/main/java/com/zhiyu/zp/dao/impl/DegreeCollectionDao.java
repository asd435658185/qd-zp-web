package com.zhiyu.zp.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.zhiyu.zp.collection.DegreeEvaluateCollection;
import com.zhiyu.zp.dao.AbsMongoOperateBaseDao;

/**
 * 
 * @author wdj
 *
 */
@Repository
public class DegreeCollectionDao extends AbsMongoOperateBaseDao<DegreeEvaluateCollection> {

	@Override
	protected Class<DegreeEvaluateCollection> getEntityClass() {
		return DegreeEvaluateCollection.class;
	}

	/**
     * 分页查询   对应mongodb操作中的  db.member.find().skip(10).limit(10);
     *
     * @author
     *                
     * @param member
     *                     查询的条件
     * @param start    
     *                     用户分页查询的起始值
     * @param size
     *                     查询的数据数目
     * 
     * @return
     *                     返回查询到的数据集合
     */
    public List<DegreeEvaluateCollection> queryPage(DegreeEvaluateCollection degree, Integer start, Integer size) {
        Query query = new Query();
        //此处可以增加分页查询条件Criteria.然后query.addCriteria(criteria);
        return this.getPage(query,(start-1)*size,size);
    }
    
    /**
     * 查询满足分页的记录总数
     *
     * @author
     *                
     * @param member
     *                     查询的条件
     * @return
     *                     返回满足条件的记录总数
     */
    public Long queryPageCount(DegreeEvaluateCollection degree){
        Query query = new Query();
        //此处可以增加分页查询条件Criteria.然后query.addCriteria(criteria);
        return this.getPageCount(query);
    }
	
    /**
     * 更新操作
     *
     * @author
     *                
     * @param degree
     *                         要更新的数据
     * @throws Exception
     *                         更新异常
     */
    public void updateFirst(DegreeEvaluateCollection degree) throws Exception {
        Update update = new Update();
        if(null==degree.getId()||"".equals(degree.getId().trim())){
            //如果主键为空,则不进行修改
            throw new Exception("Update data Id is Null");
        }
//        if(StringUtils.isNotNullValue(degree.getUsername())){
//            update.set("username", degree.getUsername());
//        }
//        if(StringUtils.isNotNullValue(degree.getPassword())){
//            update.set("password", degree.getPassword());
//        }
//        if(StringUtils.isNotNullValue(degree.getSex())){
//            update.set("sex", degree.getSex());
//        }
//        if(StringUtils.isNotNullValue(degree.getEmail())){
//            update.set("email", degree.getEmail());
//        }
        this.updateFirst(Query.query(Criteria.where("_id").is(degree.getId())),update);
    }
    
    /**
     * 更新库中所有数据
     *
     * @author
     *                
     * @param degree
     *                         更新的数据
     * @throws Exception
     *                         更新异常
     */
    public void updateMulti(DegreeEvaluateCollection degree) throws Exception {
        Update update = new Update();
        if(null==degree.getId()||"".equals(degree.getId().trim())){
            //如果主键为空,则不进行修改
            throw new Exception("Update data Id is Null");
        }
//        if(StringUtil.isNotNullValue(member.getUsername())){
//            update.set("username", member.getUsername());
//        }
//        if(StringUtil.isNotNullValue(member.getPassword())){
//            update.set("password", member.getPassword());
//        }
//        if(StringUtil.isNotNullValue(member.getSex())){
//            update.set("sex", member.getSex());
//        }
//        if(StringUtil.isNotNullValue(member.getEmail())){
//            update.set("email", member.getEmail());
//        }
        this.updateMulti(Query.query(Criteria.where("_id").is(degree.getId())),update);
    }
    
}
