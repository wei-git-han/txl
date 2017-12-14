package com.css.base.log.service;

import java.util.List;
import java.util.Map;

import com.css.base.log.entity.SysLogOperator;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email csse@css.com.cn
 * @date 2017-03-07 20:39:29
 */
public interface OperatorLogService {
	
	SysLogOperator queryObject(String userId);
	
	List<SysLogOperator> queryList(Map<String, Object> map);
	
	void save(SysLogOperator tProjectInfo);
	
	void update(SysLogOperator userId);
	
	void delete(SysLogOperator userId);
	
	/*void deleteBatch(String[] projectIds);
	
	String[] getProjectIdByUserName(String username);
	String getProjectId();*/
}
