package com.css.base.utils;

import java.util.List;

import com.css.base.entity.SSOUser;
import com.css.base.filter.SSOAuthFilter;

public class CurrentUser {

	public static SSOUser getSSOUser(){
		return SSOAuthFilter.getSUser();
		
	}
	
	public static String getUserId(){
		return SSOAuthFilter.getSUser().getUserId();
	}
	
	public static String getDepartmentId(){
		return SSOAuthFilter.getSUser().getOrganId();
	}
	
	public static String StringDeptId(){
		return SSOAuthFilter.getSUser().getOrganId();
	}
	
	public static String getUsername(){
		return SSOAuthFilter.getSUser().getFullname();
	}
	
	public static String getOrgName(){
		return SSOAuthFilter.getSUser().getOrgName();
	}
	
	/**
	 * 判断当前登录人是否为管理角色
	 * @return true 为管理员，flase 不是管理员
	 */
	public static boolean getIsManager(String appID,String appSecret){
		try{
			return StringUtils.isNotBlank(appID)?SSOAuthFilter.getRole(appID,appSecret).getIsManager():false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 获取当前登录人是否为管理员角色
	 * @return true 为管理员，flase 不是管理员
	 */
	public static String getManagerType(String appID,String appSecret){
		return SSOAuthFilter.getRole(appID,appSecret).getManagerType();
	}
	public static List<String> getRoleIds(){
		return null ;
	}
}
