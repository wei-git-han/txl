package com.css.addbase.orgservice;

import java.util.List;

import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;

/**
 * 组织机构同步信息类
 * @author gengds
 *
 */
public class SyncOrgan {

	/**
	 * 返回码
	 */
	private Integer  rsltcode;
	/**
	 * 返回结果说明
	 */
	private String  rsltmsg;
	/**
	 * 时间戳（服务器返回结果时的时间，各个应用可自行保存，待下次获取增量数据时作为请求的起始时间）
	 */
	private Long  timestamp;
	/**
	 * 组织机构变更数据
	 */
	private List<Organ>  org;
	/**
	 * 用户变更数据
	 */
	private List<UserInfo>  user;
	 
	public Integer getRsltcode() {
		return rsltcode;
	}
	public void setRsltcode(Integer rsltcode) {
		this.rsltcode = rsltcode;
	}
	public String getRsltmsg() {
		return rsltmsg;
	}
	public void setRsltmsg(String rsltmsg) {
		this.rsltmsg = rsltmsg;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public List<Organ> getOrg() {
		return org;
	}
	public void setOrg(List<Organ> org) {
		this.org = org;
	}
	public List<UserInfo> getUser() {
		return user;
	}
	public void setUser(List<UserInfo> user) {
		this.user = user;
	}
	 
	 
}
