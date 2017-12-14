package com.css.txl.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-25 15:08:05
 */
public class TxlOrgtel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//UUID
	private String id;
	//部门ID
	private String orgId;
	//部门名称
	private String orgName;
	//部门地址
	private String orgAddress;
	//部门电话
	private String orgTel;

	/**
	 * 设置：UUID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：UUID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：部门ID
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：部门ID
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * 获取：部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * 设置：部门地址
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	/**
	 * 获取：部门地址
	 */
	public String getOrgAddress() {
		return orgAddress;
	}
	/**
	 * 设置：部门电话
	 */
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	/**
	 * 获取：部门电话
	 */
	public String getOrgTel() {
		return orgTel;
	}
}
