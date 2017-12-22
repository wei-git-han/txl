package com.css.addbase.apporgmapped.entity;

import java.io.Serializable;

/**
 * 人员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public class BaseAppOrgMapped implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//应用APP标识 如DZBMS
	private String appId;
	//部门ID 如各局级单位ID
	private String orgId;
	//要获取的URL
	private String url;
	//文件存储的文件夹
	private String fileServer;
	//APP的secret码
	private String appSecret;
	//类型
	private String type;
	//配置项备注，在新增时一定要备注清楚
	private String remake;
	// 访问的uri地址
	private String webUri;
	// 应用的级别：0代表局级应用；1代表部级应用；2代表通用应用
	private String appLevel;
	//MINISTRY_APP 是否部级应用：1为部级 局级可不设置
	private String ministryApp;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileServer() {
		return fileServer;
	}
	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	public String getWebUri() {
		return webUri;
	}
	public void setWebUri(String webUri) {
		this.webUri = webUri;
	}
	public String getAppLevel() {
		return appLevel;
	}
	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}
	public String getMinistryApp() {
		return ministryApp;
	}
	public void setMinistryApp(String ministryApp) {
		this.ministryApp = ministryApp;
	}
	
}
