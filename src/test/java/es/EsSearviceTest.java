package es;

import java.net.UnknownHostException;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhiyu.zp.service.ElasticSearchBaseService;

import base.SpringTestBase;

/**
 * 
 * @author wdj
 *
 */

public class EsSearviceTest {

	@Autowired
	private ElasticSearchBaseService es;
	
	@Value("${jdbc.url}")
	private String url;
	
	@Test
	public void testEsIndex() throws UnknownHostException{
		JSONObject jsonObject = new JSONObject();
		System.out.println(url);
		Map<String, String> blogInfo = Maps.newConcurrentMap();
		blogInfo.put("author", "王二小");
		blogInfo.put("title", "测试标题");
		blogInfo.put("content", "这是一个测试内容");
		jsonObject.putAll(blogInfo);
	//	es.addIndex(jsonObject);
	}
}
