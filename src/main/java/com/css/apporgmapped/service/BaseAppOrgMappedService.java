package com.css.apporgmapped.service;


import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgmapped.constant.AppType;
import com.css.apporgmapped.entity.BaseAppOrgMapped;

import java.util.List;
import java.util.Map;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppOrgMappedService {
	
	Object orgMapped(String appLevel, String userId, String type);

	//这里的orgId是局级部门ID
	Object orgMappedByOrgId(String appLevel, String orgId, String type);


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
	 * 只根据类型（type），获取相应的映射数据
	 * @param type
	 * @param uri
	 * @return
	 */
	String getWebUrlByType(String type, String uri);

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
	/**
	 * 根据类型，获得当前应用的appId和secret数据
	 * @param type
	 * @return
	 */
	Map<String, Object> getAppIdAndSecret(String userId, String type);

	String getAppIdByUserId(String appCcsq, String userId);

	/**
	 * 获取访问路径 根据AppLevel
	 * @param type
	 * @param uri
	 * @param appLevel 局级 1 部级 0
	 * @return
	 */
	String getWebUrlByAppLevel(String type, String uri, String appLevel);

	String getBareauByUserIdss(String userId);
	/**
	 * 根据id找机构
	 * @param Id
	 * @return
	 */
	BaseAppOrgan getbyId(String Id);

	/**
	 * 获取应用配置数据查询的唯一入口(BASE_APP_ORG_MAPPED表数据)
	 * @param appLevel 应用级别
	 * @param orgId 二级部门ID
	 * @param appType 应用类型
	 * @return
	 */
	List<BaseAppOrgMapped> getMappedData(String appLevel, String orgId, String type);


	/**
	 * 获取访问路径
	 * @param type
	 * @param uri
	 * @return
	 */
	String getWebUrl(AppType appType, String uri);

	/**
	 * 获取应用配置数据查询的唯一入口(BASE_APP_ORG_MAPPED表数据)
	 * @param appLevel 应用级别
	 * @param orgId 二级部门ID
	 * @param appType 应用类型
	 * @return
	 */
	List<BaseAppOrgMapped> getMappedData(String appLevel, String orgId, AppType appType);

	/**
	 *
	 * @param appLevel 应用级别(应用的级别：0代表部级应用；1代表局级应用；2代表通用应用)
	 * @param orgId 部门ID
	 * @param appType 应用类型
	 * @return
	 */
	Object orgMappedByOrgId(String appLevel, String orgId, AppType appType);

	/**
	 *
	 * @param appLevel 应用级别(应用的级别：0代表部级应用；1代表局级应用；2代表通用应用)
	 * @param userId 用户ID
	 * @param appType 应用类型
	 * @return
	 */
	Object orgMapped(String appLevel, String userId, AppType appType);
	/**
	 * @param userCode 人员编号
	 * @return
	 */
	

	String getDetilOrgByUserId(String userId);
	
	
	
}
