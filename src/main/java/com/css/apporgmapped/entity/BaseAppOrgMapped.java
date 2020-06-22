package com.css.apporgmapped.entity;

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

	// 应用APP标识 如DZBMS
	private String appId;
	// APP的secret码
	private String appSecret;
	// 应用类型
	private String type;
	// 域名下URI的配置；
	private String webUri;
	// 部门ID 如各局级单位ID
	private String orgId;
	// 部门名称 如各局级单位
	private String orgName;
	// 要获取的URL,APP的访问地址
	private String url;
	// 文件在文件服务下存储的目录
	private String fileServer;
	// 应用的级别：0代表部级应用；1代表局级应用；2代表通用应用
	private String appLevel;
	// 配置项备注，在新增时一定要备注清楚
	private String remark;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

	public String getWebUri() {
		return webUri;
	}

	public void setWebUri(String webUri) {
		this.webUri = webUri;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
