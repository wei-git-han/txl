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
}
