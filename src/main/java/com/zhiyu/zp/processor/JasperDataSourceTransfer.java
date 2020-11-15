package com.zhiyu.zp.processor;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.zp.bean.BodyResultBean;
import com.zhiyu.zp.bean.OneLevelBean;
import com.zhiyu.zp.bean.SecondLevelBean;
import com.zhiyu.zp.bean.ThirdLevelBean;
import com.zhiyu.zp.dto.response.app.degree.BaseEvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;
import com.zhiyu.zp.dto.response.app.report.EventualReportDto;
import com.zhiyu.zp.dto.response.web.body.BodyResultContentDto;
import com.zhiyu.zp.dto.response.web.mark.MarkResultContentDto;
import com.zhiyu.zp.dto.response.web.mark.MarkResultContentDto.Mark;
import com.zhiyu.zp.enumcode.MarkType;
import com.zhiyu.zp.utils.TransformUtil;

/**
 * 
 * @author wdj
 *
 */

public class JasperDataSourceTransfer {

	private List<String> markEnglishFieldCodes;
	
	private List<String> bodyEnglishFieldCodes;
	
	public JasperDataSourceTransfer(){}
	
	public JasperDataSourceTransfer(List<String> markEnglishFieldCodes,List<String> bodyEnglishFieldCodes) {
		super();
		this.markEnglishFieldCodes = markEnglishFieldCodes;
		this.bodyEnglishFieldCodes = bodyEnglishFieldCodes;
	}

	/**
	 * 转换成成绩数据
	 * @param contents
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<Map<String, String>> transfer2MarkData(List<MarkResultContentDto> contents) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		//List<MarkResultBean> beans = Lists.newArrayList();
		List<Map<String, String>> beans = Lists.newArrayList();
		for (MarkResultContentDto m : contents) {

			List<Mark> marks = m.getMarks();
			// 为了前端显示好看，特加点处理--------------
			int markLen = marks.size();
			int minus = 0;
			if (markLen < MarkType.values().length) {
				minus = MarkType.values().length - markLen;
			}
			for (int j = 0; j < minus; j++) {
				Mark temp_mark = new Mark();
				marks.add(temp_mark);
			}
			// ----------------------------------
			for (Mark mark : marks) {
				//MarkResultBean mrb = new MarkResultBean();
				Map<String, String> map = Maps.newConcurrentMap();
				List<String> values = mark.getScores();
				int size = values.size();
				for (int i = 0; i < markEnglishFieldCodes.size(); i++) {
					String field = markEnglishFieldCodes.get(i);
					String value = "";
					if (size >= (i + 1)) {
						value = values.get(i).toString();
					}
					//TransformUtil.setProperty(mrb, field, value);
					map.put(field, value);
				}
				beans.add(map);
			}
		}
		return beans;
	}
	
	/**
	 * 转换成体质数据（体质区数据相对固定，这个后期如果需要，也可以改成map方式，如上）
	 * @param contents
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<BodyResultBean> transfer2BodyData(List<BodyResultContentDto> contents) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		List<BodyResultBean> beans = Lists.newArrayList();
		for (BodyResultContentDto m : contents) {

			List<BodyResultContentDto.Mark> marks = m.getMarks();
			for (BodyResultContentDto.Mark mark : marks) {
				BodyResultBean mrb = new BodyResultBean();
				List<String> values = mark.getValues();
				int size = values.size();
				for (int i = 0; i < bodyEnglishFieldCodes.size(); i++) {
					String field = bodyEnglishFieldCodes.get(i);
					String value = "";
					if (size >= (i + 1)) {
						value = values.get(i).toString();
					}
					TransformUtil.setProperty(mrb, field, value);
				}
				beans.add(mrb);
			}
		}
		return beans;
	}
	
	/**
	 * 转成成主要数据
	 * @param eventualReportDto
	 * @return
	 */
	public List<OneLevelBean> majorData(EventualReportDto eventualReportDto){
		Integer index = 1;
		List<OneLevelBean> beans = Lists.newArrayList();
		//提取出主要大维度信息
		List<EvaluateDegreeDto> majorDegreeInfos = eventualReportDto.getDegreeInfos();
		for(EvaluateDegreeDto majorDegreeInfo:majorDegreeInfos){
			OneLevelBean bean = new OneLevelBean();
			bean.setName(majorDegreeInfo.getDegreeName());
			//提取二级维度信息
			List<BaseEvaluateDegreeDto> secondDegreeInfos = majorDegreeInfo.getItems();
			List<SecondLevelBean> secondBeans = Lists.newArrayList();
			for(BaseEvaluateDegreeDto secondDegree:secondDegreeInfos){
				SecondLevelBean second = new SecondLevelBean(secondDegree.getDegreeName(),secondDegree.getDegreeEvaluateTotalNum().toString()+"★");
				//提取三级维度信息
				List<BaseEvaluateDegreeDto> thirdDegreeInfos = secondDegree.getItems();
				List<ThirdLevelBean> thirdBeans = Lists.newArrayList();
				for(BaseEvaluateDegreeDto thirdDegree:thirdDegreeInfos){
					ThirdLevelBean third = new ThirdLevelBean();
					third.setName(thirdDegree.getDegreeName());
					third.setNo(index.toString());
					index++;
					thirdBeans.add(third);
				}
				second.setThirdLevelData(thirdBeans);
				secondBeans.add(second);
			}
			bean.setSecondLevelData(secondBeans);
			beans.add(bean);
		}
		return beans;
	}
	
	
}
