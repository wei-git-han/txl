package com.css.addbase.orgservice;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class UserInfo {

	private String userid;
	private String fullname;
	@JSONField(serialize = false)
	private String account;
	@JSONField(serialize = false)
	private String password;
	private String sex;
	private String organId;
	private Integer orderId;
	@JSONField(serialize = false)
	private String dn;
	private Integer isDelete;
	@JSONField(serialize = false)
	private String ca;
	@JSONField(serialize = false)
	private Integer isManager;
	@JSONField(serialize = false)
	private String tokenId;
	@JSONField(serialize = false)
	private String spId;
	@JSONField(serialize = false)
	private String sn;
	@JSONField(serialize = false)
	private String ip;
	@JSONField(serialize = false)
	private String startDate;
	@JSONField(serialize = false)
	private String endDate;
	@JSONField(serialize = false)
	private String userUuid;
	private String userEmail;
	@JSONField(serialize = false)
	private String secLevel;
	@JSONField(serialize = false)
	private Integer failedLoginCount;
/*	@JSONField(serialize = false)
	private java.sql.Timestamp editPwdTime;*/
	@JSONField(serialize = false)
	private String mobile;
	@JSONField(serialize = false)
	private String type;
	@JSONField(serialize = false)
	private Long timestamp;
	
	private String dept;
	
	@JSONField(serialize = false)
	private String duty;//职务
	@JSONField(serialize = false)
	private String position;//职级
	@JSONField(serialize = false)
	private Map<String,String> extAttribute=new HashMap<String,String>();
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSecLevel() {
		return secLevel;
	}

	public void setSecLevel(String secLevel) {
		this.secLevel = secLevel;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	/*public java.sql.Timestamp getEditPwdTime() {
		return editPwdTime;
	}

	public void setEditPwdTime(java.sql.Timestamp editPwdTime) {
		this.editPwdTime = editPwdTime;
	}*/

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public Map<String, String> getExtAttribute() {
		return extAttribute;
	}

	public void setExtAttribute(Map<String, String> extAttribute) {
		this.extAttribute = extAttribute;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
}