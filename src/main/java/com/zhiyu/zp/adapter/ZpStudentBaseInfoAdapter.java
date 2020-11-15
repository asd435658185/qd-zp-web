package com.zhiyu.zp.adapter;

import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;
import com.zhiyu.baseplatform.enumcode.UploadFileType;
import com.zhiyu.baseplatform.processor.PictureInfoBeanFactory;
import com.zhiyu.baseplatform.processor.PictureSaveAndDisplayProcessor;
import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;

/**
 * 
 * @author wdj
 *
 */
public class ZpStudentBaseInfoAdapter {

	private PictureSaveAndDisplayProcessor processor = new PictureSaveAndDisplayProcessor(
			PictureInfoBeanFactory.newInstance(UploadFileType.STUDENT_AVATAR));
	
	/**
	 * 将学生基础数据转成综评中学生数据
	 * @param stu
	 * @return
	 */
	public ZpStudentBaseInfoDto getDtoFromEntity(StudentBaseInfoEntity stu){
		ZpStudentBaseInfoDto dto = new ZpStudentBaseInfoDto();
		dto.setStudentId(stu.getId().longValue());
		dto.setStudentName(stu.getName());
		dto.setAvatar(processor.getFileService().getFileWebPathBySegment(stu.getHeading()));
		dto.setStudentNo(stu.getNumber());
		return dto;
	}
}
