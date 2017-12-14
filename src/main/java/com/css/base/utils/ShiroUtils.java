//package com.css.base.utils;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//
//import com.css.base.entity.SysUser;
//
///**
// * Shiro工具类
// * 
// * @author 中软信息系统工程有限公司
// * @email  
// * @date 2016年11月12日 上午9:49:19
// */
//public class ShiroUtils {
//
//	public static Session getSession() {
//		return SecurityUtils.getSubject().getSession();
//	}
//
//	public static Subject getSubject() {
//		return SecurityUtils.getSubject();
//	}
//
//	public static SysUser getUserEntity() {
//		return (SysUser)SecurityUtils.getSubject().getPrincipal();
//	}
//
//	public static Long getUserId() {
//		return getUserEntity().getUserId();
//	}
//	
//	public static void setSessionAttribute(Object key, Object value) {
//		getSession().setAttribute(key, value);
//	}
//
//	public static Object getSessionAttribute(Object key) {
//		return getSession().getAttribute(key);
//	}
//
//	public static boolean isLogin() {
//		return SecurityUtils.getSubject().getPrincipal() != null;
//	}
//
//	public static void logout() {
//		SecurityUtils.getSubject().logout();
//	}
//	
//	public static String getKaptcha(String key) {
//		getSessionAttribute(key);
//		String kaptcha = getSessionAttribute(key).toString();
//		getSession().removeAttribute(key);
//		return kaptcha;
//	}
//
//}
