package mongo;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhiyu.zp.collection.DegreeEvaluateCollection;
import com.zhiyu.zp.dao.impl.DegreeCollectionDao;

import base.SpringTestBase;

/**
 * 
 * @author wdj
 *
 */

public class MongoTest extends SpringTestBase{

	@Autowired
	private DegreeCollectionDao dao;
	
	//@Test
	public void testSave(){
		DegreeEvaluateCollection dc = new DegreeEvaluateCollection();
		dc.setId("schoolId38#classId5#termId37#studentId5");
		dc.setTotalSecondValue(107);
		dc.setFeedback("测试");
		dao.save(dc);
	}
	
	@Test
	public void testFindAll(){
		List<DegreeEvaluateCollection> list = dao.queryList(null);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindById(){
		DegreeEvaluateCollection dc = dao.queryById("schoolId38#studentId4");
		System.out.println(dc.getFeedback()+":"+dc.getTotalSecondValue());
	}
}
