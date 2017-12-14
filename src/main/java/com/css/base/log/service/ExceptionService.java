package com.css.base.log.service;

import java.util.List;
import java.util.Map;

import com.css.base.log.entity.SysLogException;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email csse@css.com.cn
 * @date 2017-03-07 20:39:29
 */
public interface ExceptionService {
	
	SysLogException queryObject(String projectId);
	
	List<SysLogException> queryList(Map<String, Object> map);
	
	void save(SysLogException tProjectInfo);
	
	void update(SysLogException ex_id);
	
	void delete(SysLogException ex_id);
	
	/*void deleteBatch(String[] projectIds);
	
	String[] getProjectIdByUserName(String username);
	String getProjectId();*/
}
