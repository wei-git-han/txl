//package com.css.config;
//
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.springframework.boot.context.embedded.FilterRegistrationBean;
//import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import com.css.filters.FilterChainProxy;
//
//@Configuration
//public class SSOConfig {
//
//	/**
//	 * 退出监听器
//	 * @return
//	 */
//	@Bean
//	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> getSingleSignOutHttpSessionListener() {
//		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> registration = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>();
//		SingleSignOutHttpSessionListener ssoListener = new SingleSignOutHttpSessionListener();
//		registration.setListener(ssoListener);
//		return registration;
//
//	}
//
//	/**
//	 * SSOFilter
//	 * @return
//	 */
//	@Bean
//	public FilterRegistrationBean getSSOFilter(Environment env) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		FilterChainProxy ssoFilter = new FilterChainProxy();
//		 ssoFilter.setEnv(env);;
//		registration.setEnabled(true);
//		registration.setFilter(ssoFilter);
//		registration.addUrlPatterns("*");
//		registration.addInitParameter("excludePages", "/tw/.*,/cms/web/.*");
//		registration.setName("ssoFilter");
//		registration.setOrder(1);
//		return registration;
//
//	}
//
//}
