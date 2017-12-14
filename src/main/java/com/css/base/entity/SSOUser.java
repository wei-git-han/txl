package com.css.base.entity;

import java.util.Map;

public class SSOUser {

//	private String userId;
//	private String  account;
//	private String orderId;
//	private String organName;
	
	private String userId;
	private String fullname;
	private String account;
	private String  orgName;
	private String password;
	private String sex;
	private String organId;
	private Integer orderId;
	private boolean delete ;
	private String ca;
	private String secLevel;
	private Integer failedLoginCount;//登陆错误次数

	/**
	 * 用户有效期开始时间
	 * 
	 */
	private String startdate;
	/**
	 * 用户有效期结束时间
	 * 
	 */
	private String enddate;
	private String useruuid;
	private String useremail;
	/**
	 * 扩展属性
	 */
	private Map<String, String> attrMap;
	/**
	 * 0 默认不是管理员 1:是管理员 可以进入单点登录后台
	 */
	private Integer isManager;
	
	/**
	 * 动态口令的种子
	 */
	private String tokenId;
	
	/**
	 * 动态口令卡型号
	 */
	private String spid;
	/**
	 * 动态口令卡序列号
	 */
	private String sn;
	
	/**
	 * 用户登录方式
	 */
	private String loginMode;
	
	/**
	 * Ip限制
	 */
	private String ip;
	
	/**
	 * 权限的级别标识
	 */
	private String manageLevel;
	
	private String nodeType;
	
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}



	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		return "user [userid=" + userId + ", account=" + account
				+ ", fullname=" + fullname + ", fatherId=" + organId + ", startdate=" + startdate + ", enddate=" + enddate+"]";
	}

	private String dn;

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}
	
	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getManageLevel() {
		return manageLevel;
	}

	public void setManageLevel(String manageLevel) {
		this.manageLevel = manageLevel;		 
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getUseruuid() {
		return useruuid;
	}

	public void setUseruuid(String useruuid) {
		this.useruuid = useruuid;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getSecLevel() {
		return secLevel;
	}

	public void setSecLevel(String secLevel) {
		this.secLevel = secLevel;
	}

//	public Date getEditPwdTime() {
//		return editPwdTime;
//	}
//
//	public void setEditPwdTime(Date editPwdTime) {
//		this.editPwdTime = editPwdTime;
//	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}
