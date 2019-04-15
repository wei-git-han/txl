package com.css.txl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-21 09:21:03
 */
public class TxlUser implements Serializable {
	private static final long serialVersionUID = 1L;

	// 用户ID
	private String userid;
	// 用户名字
	private String fullname;
	// 登录名
	private String account;
	// 密码
	private String password;
	// 性别
	private String sex;
	// 组织机构ID
	private String organid;
	// 排序ID
	private String orderid;
	//
	private String dn;
	// 是否删除
	private String isdelete;
	//
	private String ca;
	// 是否管理员
	private Integer ismanager;
	//
	private String tokenid;
	//
	private String spid;
	//
	private String sn;
	// IP地址
	private String ip;
	// 开始时间
	private Date startdate;
	// 结束时间
	private Date enddate;
	// 用户UUID
	private String useruuid;
	// 用户邮件
	private String useremail;
	// 密级
	private String seclevel;
	//
	private Integer failedlogincount;
	// 编辑时间
	private Date editpwdtime;
	// 移动电话
	private String mobile;
	//
	private Long timestamp;
	//
	private String type;
	// 职位
	private String post;
	// 座机电话
	private String telephone;
	// 地址
	private String address;
	
	private String dept;
	
	// 组织机构Name
	private String organName;
	private String orgName;
	// 是否收藏
	private String isSc;
	//是否显示
	private String isShow;//1或空显示，0隐藏
	//显示权限代码
	private String rights;
	//备注
	private String remarks;
	
	

	//
	private String pyName;

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsSc() {
		return isSc;
	}

	public void setIsSc(String isSc) {
		this.isSc = isSc;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * 设置：用户ID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 获取：用户ID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 设置：用户名字
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 获取：用户名字
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * 设置：登录名
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取：登录名
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置：组织机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**
	 * 获取：组织机构ID
	 */
	public String getOrganid() {
		return organid;
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
	public void setCa(String ca) {
		this.ca = ca;
	}

	/**
	 * 获取：
	 */
	public String getCa() {
		return ca;
	}

	/**
	 * 设置：是否管理员
	 */
	public void setIsmanager(Integer ismanager) {
		this.ismanager = ismanager;
	}

	/**
	 * 获取：是否管理员
	 */
	public Integer getIsmanager() {
		return ismanager;
	}

	/**
	 * 设置：
	 */
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	/**
	 * 获取：
	 */
	public String getTokenid() {
		return tokenid;
	}

	/**
	 * 设置：
	 */
	public void setSpid(String spid) {
		this.spid = spid;
	}

	/**
	 * 获取：
	 */
	public String getSpid() {
		return spid;
	}

	/**
	 * 设置：
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取：
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * 设置：IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取：IP地址
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置：开始时间
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * 获取：开始时间
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * 设置：结束时间
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * 获取：结束时间
	 */
	public Date getEnddate() {
		return enddate;
	}

	/**
	 * 设置：用户UUID
	 */
	public void setUseruuid(String useruuid) {
		this.useruuid = useruuid;
	}

	/**
	 * 获取：用户UUID
	 */
	public String getUseruuid() {
		return useruuid;
	}

	/**
	 * 设置：用户邮件
	 */
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	/**
	 * 获取：用户邮件
	 */
	public String getUseremail() {
		return useremail;
	}

	/**
	 * 设置：密级
	 */
	public void setSeclevel(String seclevel) {
		this.seclevel = seclevel;
	}

	/**
	 * 获取：密级
	 */
	public String getSeclevel() {
		return seclevel;
	}

	/**
	 * 设置：
	 */
	public void setFailedlogincount(Integer failedlogincount) {
		this.failedlogincount = failedlogincount;
	}

	/**
	 * 获取：
	 */
	public Integer getFailedlogincount() {
		return failedlogincount;
	}

	/**
	 * 设置：编辑时间
	 */
	public void setEditpwdtime(Date editpwdtime) {
		this.editpwdtime = editpwdtime;
	}

	/**
	 * 获取：编辑时间
	 */
	public Date getEditpwdtime() {
		return editpwdtime;
	}

	/**
	 * 设置：移动电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：移动电话
	 */
	public String getMobile() {
		return mobile;
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

	/**
	 * 设置：职位
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * 获取：职位
	 */
	public String getPost() {
		return post;
	}

	/**
	 * 设置：座机电话
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取：座机电话
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}
}
