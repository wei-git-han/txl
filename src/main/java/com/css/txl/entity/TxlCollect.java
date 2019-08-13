package com.css.txl.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-20 20:26:21
 */
public class TxlCollect implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String userid;
	//
	private String collectUserid;
	private String dept;
	private String fullname;
	// 移动电话
	private String mobile;
	// 座机电话
	private String telephone;
	// 地址
	private String address;
	// 地址
	private String isShow;
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getIsSc() {
		return isSc;
	}
	public void setIsSc(String isSc) {
		this.isSc = isSc;
	}
	private String isSc;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setCollectUserid(String collectUserid) {
		this.collectUserid = collectUserid;
	}
	/**
	 * 获取：
	 */
	public String getCollectUserid() {
		return collectUserid;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
}
