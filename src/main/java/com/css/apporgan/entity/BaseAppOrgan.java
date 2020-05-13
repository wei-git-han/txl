package com.css.apporgan.entity;

import java.io.Serializable;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public class BaseAppOrgan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	//树路径
	private String treePath;
	//删除标识
	private Integer isdelete;
	//部门名称
	private String name;
	//更新时间戳
	private Long timestamp;
	//排序
	private Integer sort;
	//更新类型
	private Integer type;
	//部门编码
	private String code;
	//上级部门
	private String parentId;
	//更新类型
	private Integer deptOfficer;

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
	 * 设置：树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	/**
	 * 获取：树路径
	 */
	public String getTreePath() {
		return treePath;
	}
	/**
	 * 设置：删除标识
	 */
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 获取：删除标识
	 */
	public Integer getIsdelete() {
		return isdelete;
	}
	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：更新时间戳
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 获取：更新时间戳
	 */
	public Long getTimestamp() {
		return timestamp;
	}
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：更新类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：更新类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：部门编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：部门编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：上级部门
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级部门
	 */
	public String getParentId() {
		return parentId;
	}
	public Integer getDeptOfficer() {
		return deptOfficer;
	}
	public void setDeptOfficer(Integer deptOfficer) {
		this.deptOfficer = deptOfficer;
	}
}
