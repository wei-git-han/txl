package com.css.adminconfig.entity;

import java.io.Serializable;

/**
 * 管理员设置表
 *
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2019-04-19 10:22:04
 */
public class AdminSet implements Serializable {
    private static final long serialVersionUID = 1L;

    //唯一标识
    private String id;
    //用户id
    private String userId;
    //用户姓名
    private String userName;
    //用户部门id
    private String deptId;
    //用户部门
    private String deptName;
    //管理员类型（1：部管理员；2：局管理员）
    private String adminType;
    //备注
    private String remark;
    //单位id
    private String orgId;
    //单位名称
    private String orgName;

    private String editFlag;
    //挂接首长名字
    private String seniorOfficial;
    //挂接首长ID
    private String seniorOfficialId;

    /**
     * 设置：唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：唯一标识
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * 获取：用户id
     */
    public String getUserId() {
        return userId;
    }
    /**
     * 设置：用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * 获取：用户姓名
     */
    public String getUserName() {
        return userName;
    }
    /**
     * 设置：用户部门id
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    /**
     * 获取：用户部门id
     */
    public String getDeptId() {
        return deptId;
    }
    /**
     * 设置：用户部门
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    /**
     * 获取：用户部门
     */
    public String getDeptName() {
        return deptName;
    }
    /**
     * 设置：管理员类型（1：部管理员；2：局管理员）
     */
    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }
    /**
     * 获取：管理员类型（1：部管理员；2：局管理员）
     */
    public String getAdminType() {
        return adminType;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getEditFlag() {
        return editFlag;
    }
    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }
    public String getSeniorOfficial() {
        return seniorOfficial;
    }
    public void setSeniorOfficial(String seniorOfficial) {
        this.seniorOfficial = seniorOfficial;
    }
    public String getSeniorOfficialId() {
        return seniorOfficialId;
    }
    public void setSeniorOfficialId(String seniorOfficialId) {
        this.seniorOfficialId = seniorOfficialId;
    }

}
