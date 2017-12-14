//package com.css.config;
//
//import java.util.LinkedHashMap;  
//import java.util.Map;  
//  
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;  
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;  
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;  
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;  
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;  
//import org.springframework.context.annotation.Bean;  
//import org.springframework.context.annotation.Configuration;
//
//import com.css.base.shiro.UserRealm;  
//  
//  
//@Configuration  
//public class ShiroConfig {  
//  
//    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
//  
//    @Bean(name = "UserRealm")  
//    public UserRealm getUserRealm() {  
//        return new UserRealm();  
//    }  
//  
//    @Bean(name = "lifecycleBeanPostProcessor")  
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {  
//        return new LifecycleBeanPostProcessor();  
//    }  
//  
//    @Bean  
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {  
//        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
//        daap.setProxyTargetClass(true);  
//        return daap;  
//    }  
//  
//    @Bean(name = "securityManager")  
//    public DefaultWebSecurityManager getDefaultWebSecurityManager() {  
//        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();  
//        dwsm.setRealm(getUserRealm());  
////        dwsm.setCacheManager(getEhCacheManager());  
//        return dwsm;  
//    }  
//  
//    @Bean(name="AuthorizationAttributeSourceAdvisor")
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {  
//        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();  
//        aasa.setSecurityManager(getDefaultWebSecurityManager());  
//        return new AuthorizationAttributeSourceAdvisor();  
//    }  
//  
//    @Bean(name = "shiroFilter")  
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {  
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
//        shiroFilterFactoryBean  
//                .setSecurityManager(getDefaultWebSecurityManager());  
//        shiroFilterFactoryBean.setLoginUrl("/login.html");  
//        shiroFilterFactoryBean.setSuccessUrl("/index.html");  
//        filterChainDefinitionMap.put("/statics/**", "anon");  
//        filterChainDefinitionMap.put("/page/**", "anon");  
//        filterChainDefinitionMap.put("/sys/**", "anon");  
//        filterChainDefinitionMap.put("/captcha.jpg", "anon");  
//        filterChainDefinitionMap.put("/sys/user/names", "anon");  
////        filterChainDefinitionMap.put("", "anon");  
////        filterChainDefinitionMap.put("/**", "authc");  
////        filterChainDefinitionMap.put("/**", "anon");  
//        shiroFilterFactoryBean  
//                .setFilterChainDefinitionMap(filterChainDefinitionMap);  
//        return shiroFilterFactoryBean;  
//    }  
//  
//}  