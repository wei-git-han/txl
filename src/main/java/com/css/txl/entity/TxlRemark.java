package com.css.txl.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-05-13 13:42:33
 */
public class TxlRemark implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String remarkContent;
	//
	private String remarkCreatorId;
	//
	private String remarkCreatorName;
	//
	private Date createdTime;
	//
	private String remarkedPersonId;
	//
	private String remarkedPersonName;
	//
	private Date updateTime;

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
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}
	/**
	 * 获取：
	 */
	public String getRemarkContent() {
		return remarkContent;
	}
	/**
	 * 设置：
	 */
	public void setRemarkCreatorId(String remarkCreatorId) {
		this.remarkCreatorId = remarkCreatorId;
	}
	/**
	 * 获取：
	 */
	public String getRemarkCreatorId() {
		return remarkCreatorId;
	}
	/**
	 * 设置：
	 */
	public void setRemarkCreatorName(String remarkCreatorName) {
		this.remarkCreatorName = remarkCreatorName;
	}
	/**
	 * 获取：
	 */
	public String getRemarkCreatorName() {
		return remarkCreatorName;
	}
	/**
	 * 设置：
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * 设置：
	 */
	public void setRemarkedPersonId(String remarkedPersonId) {
		this.remarkedPersonId = remarkedPersonId;
	}
	/**
	 * 获取：
	 */
	public String getRemarkedPersonId() {
		return remarkedPersonId;
	}
	/**
	 * 设置：
	 */
	public void setRemarkedPersonName(String remarkedPersonName) {
		this.remarkedPersonName = remarkedPersonName;
	}
	/**
	 * 获取：
	 */
	public String getRemarkedPersonName() {
		return remarkedPersonName;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
