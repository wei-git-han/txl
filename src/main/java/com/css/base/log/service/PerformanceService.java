package com.css.base.log.service;

import java.util.List;
import java.util.Map;

import com.css.base.log.entity.SysLogPerformance;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email csse@css.com.cn
 * @date 2017-03-07 20:39:29
 */
public interface PerformanceService {
	
	SysLogPerformance queryObject(String per_id);
	
	List<SysLogPerformance> queryList(Map<String, Object> map);
	
	void save(SysLogPerformance tProjectInfo);
	
	void update(SysLogPerformance per_id);
	
	void delete(SysLogPerformance per_id);
	
	/*void deleteBatch(String[] projectIds);
	
	String[] getProjectIdByUserName(String username);
	String getProjectId();*/
}
