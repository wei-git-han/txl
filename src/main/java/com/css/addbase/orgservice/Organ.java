package com.css.addbase.orgservice;

import com.alibaba.fastjson.annotation.JSONField;

public class Organ {

	private String organId;
	private String organName;
	private String fatherId;
	@JSONField(serialize = false)
	private String dn;
	private Integer orderId;
	private Integer isDelete;
	@JSONField(serialize = false)
	private String code;
	@JSONField(serialize = false)
	private String p;
	@JSONField(serialize = false)
	private String orguuid;
	private String path;
	@JSONField(serialize = false)
	private String type;
	@JSONField(serialize = false)
	private Long timestamp;
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getOrguuid() {
		return orguuid;
	}

	public void setOrguuid(String orguuid) {
		this.orguuid = orguuid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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
	

}
