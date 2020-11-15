package com.zhiyu.zp.service.excel.exportBase;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.baseplatform.util.TransformUtil;

/**
 * Excel导出服务
 * @author wdj
 *
 */
@Service
public class ExcelExportService {
	
	/**
	 * 生成表格
	 * @param objList
	 * @param keys    键值，对象中的域
	 * @param columnNames  列名，这里要特别注意，列名顺序定义要与键值一致
	 * @return
	 * @throws Exception
	 */

	public Workbook generateExcelTable(List<Object> objList,String []keys,String columnNames[]) throws Exception{
		
		List<Map<String, Object>> list = tranToMapList(objList);
		
		return ExcelUtil.createWorkBook(list, keys, columnNames);
	
	}
	
	private List<Map<String, Object>> tranToMapList(List<Object> objList) throws Exception{
		List<Map<String, Object>> list = Lists.newArrayList();
		for(Object o:objList){
			Map<String, Object> map = TransformUtil.objectToMap(o);
			list.add(map);
		}
		return list;
	}
	
	
}
