package com.css.base.entity;

/**
 * 系统角色类
 * @author gengdesheng
 *
 */
public class SSORole {
	
	/**
	 * 返回码
	 */
	private String rsltcode;
	/**
	 * 返回该account的角色
	 */
	private String rsltmsg;
	/**
	 * 若非管理角色，则isManager返回结果为false，若为管理角色则isManager为true，
	 */
	private Boolean isManager;
	/**
	 * 若非管理角色,manageType为空；若为管理角色则manageType返回管理角色类型，若为多个管理角色则通过“,”分隔
	 */
	private String  managerType;
	
	public String getRsltcode() {
		return rsltcode;
	}
	public void setRsltcode(String rsltcode) {
		this.rsltcode = rsltcode;
	}
	public String getRsltmsg() {
		return rsltmsg;
	}
	public void setRsltmsg(String rsltmsg) {
		this.rsltmsg = rsltmsg;
	}
	public String getManagerType() {
		return managerType;
	}
	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
}
