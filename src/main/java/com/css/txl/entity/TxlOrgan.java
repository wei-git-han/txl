package com.css.txl.entity;

import java.io.Serializable;
/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-20 21:01:42
 */
public class TxlOrgan implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String organid;
	//
	private String organname;
	//
	private String fatherid;
	//
	private String orderid;
	//
	private String dn;
	//
	private String isdelete;
	//
	private String code;
	//
	private String path;
	//
	private String orguuid;
	//
	private Long timestamp;
	//
	private String type;
	private String isShow;//1或空显示，0隐藏

	/**
	 * 设置：
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**
	 * 获取：
	 */
	public String getOrganid() {
		return organid;
	}

	/**
	 * 设置：
	 */
	public void setOrganname(String organname) {
		this.organname = organname;
	}

	/**
	 * 获取：
	 */
	public String getOrganname() {
		return organname;
	}

	/**
	 * 设置：
	 */
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}

	/**
	 * 获取：
	 */
	public String getFatherid() {
		return fatherid;
	}

	/**
	 * 设置：
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 * 获取：
	 */
	public String getDn() {
		return dn;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置：
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取：
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置：
	 */
	public void setOrguuid(String orguuid) {
		this.orguuid = orguuid;
	}

	/**
	 * 获取：
	 */
	public String getOrguuid() {
		return orguuid;
	}

	/**
	 * 设置：
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 获取：
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * 设置：
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
}
