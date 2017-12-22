package com.css.addbase.apporgmapped.service;

import java.util.Map;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppOrgMappedService {
	
	Object orgMapped(String appId,String userId,String type);
	
	//这里的orgId是局级部门ID
	Object orgMappedByOrgId(String appId,String orgId,String type);
	
	
	/**
	 * 根据数据类型，获得访问的应用地址；
	 * @param type
	 * @return
	 */
	String getUrlByType(String userId, String type);
	
	/**
	 * 根据用户Id获取局级部门ID；
	 * @param type
	 * @return
	 */
	String getBareauByUserId(String userId);
	
	/**
	 * 获取访问路径
	 * @param type
	 * @param uri
	 * @return
	 */
	String getWebUrl(String type, String uri);
	
	/**
	 * 根据数据类型，获得稳健服务的文件目录
	 * @param type
	 * @return
	 */
	String getFileServerIdByType(String type);
	
	/**
	 * 根据用户id，获得当前用户所在局的应用id
	 * @param userId
	 * @param type
	 * @return
	 */
	String getAppIdByUserId(String type);
	
	/**
	 * 根据用户id，获得当前用户所在局的id
	 * @param userId
	 * @param type
	 * @return
	 */
	String getOrgIdByUserId(String type);
	
	/**
	 * 根据类型，获得当前应用的级别，是部级应用还是局级应用
	 * @param type
	 * @return
	 */
	String getAppLevelByType(String type);
	
	/**
	 * 根据类型，获得当前应用的appId和secret数据
	 * @param type
	 * @return
	 */
	Map<String, Object> getAppIdAndSecret(String type);
	
}
