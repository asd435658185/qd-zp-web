package com.zhiyu.zp.service.business;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.entity.ZpGradeSettingEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.GradeLevel;
import com.zhiyu.zp.exception.EvaluateGradeLevelSettingException;
import com.zhiyu.zp.service.IZpGradeSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class GradeLevelService {

	@Autowired
	private IZpGradeSettingService zpGradeSettingService;
	
	private void saveGradeLevelSetting(ZpGradeSettingEntity entity) throws EvaluateGradeLevelSettingException{
		entity.setDataState(DataState.NORMAL);
		List<ZpGradeSettingEntity> list = zpGradeSettingService.findByEntity(entity);
		if(!list.isEmpty()){
			throw new EvaluateGradeLevelSettingException();
		}else{
			zpGradeSettingService.save(entity);
		}
	}
	
	/**
	 * 保存年级设置
	 * @param levelId
	 * @param schoolId
	 * @param grades
	 * @throws EvaluateGradeLevelSettingException
	 */
	@Transactional
	public void saveGradeSetting(int levelId,Long schoolId,List<Integer> grades) throws EvaluateGradeLevelSettingException{
		for(Integer gradeId:grades){
			ZpGradeSettingEntity setting = new ZpGradeSettingEntity();
			setting.setGradeId(gradeId.longValue());
			setting.setSchoolId(schoolId);
			setting.setLevelId(GradeLevel.getType(levelId));
			setting.setDataState(DataState.NORMAL);
			saveGradeLevelSetting(setting);
		}
	}
}
