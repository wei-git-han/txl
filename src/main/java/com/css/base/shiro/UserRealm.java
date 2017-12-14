//package com.css.base.shiro;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.css.base.entity.SysUser;
//
///**
// * 认证
// * 
// * @author 中软信息系统工程有限公司
// * @email  
// * @date 2016年11月10日 上午11:55:49
// */
//@Component
//public class UserRealm extends AuthorizingRealm {
//    @Autowired
//    private RealmDao realmDao;
//    
//    /**
//     * 授权(验证权限时调用)
//     */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		SysUser user = (SysUser)principals.getPrimaryPrincipal();
//		Long userId = user.getUserId();
//		
//		List<String> permsList = null;
//		
//		//系统管理员，拥有最高权限
//		if(userId == 1){
//			permsList =realmDao.queryAllPerms();
//		}else{
//			permsList = realmDao.queryUserPerms(userId);
//		}
//
//		//用户权限列表
//		Set<String> permsSet = new HashSet<String>();
//		for(String perms : permsList){
//			if(StringUtils.isBlank(perms)){
//				continue;
//			}
//			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
//		}
//		
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.setStringPermissions(permsSet);
//		return info;
//	}
//
//	/**
//	 * 认证(登录时调用)
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken token) throws AuthenticationException {
//		String username = (String) token.getPrincipal();
//        String password = new String((char[]) token.getCredentials());
//        
//        //查询用户信息
//        SysUser user = realmDao.queryByUserName(username);
//        //账号不存在
//        if(user == null) {
//            throw new UnknownAccountException("账号或密码不正确");
//        }
//        
//        //密码错误
//        if(!password.equals(user.getPassword())) {
//            throw new IncorrectCredentialsException("账号或密码不正确");
//        }
//        
//        //账号锁定
//        if(user.getStatus() == 0){
//        	throw new LockedAccountException("账号已被锁定,请联系管理员");
//        }
//        
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
//        return info;
//	}
//
//}
