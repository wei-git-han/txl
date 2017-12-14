package com.css.base.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 异常处理器
 * 
 * @author 中软信息系统工程有限公司
 * @email  
 * @date 2016年10月27日 下午10:16:19
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//记录异常日志
		logger.error(ex.getMessage(), ex);
		try {
//			response.setContentType("application/json;charset=utf-8");
//			response.setCharacterEncoding("utf-8");
			
			if (ex instanceof RRException) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("code", ((RRException) ex).getCode());
				map.put("msg", ((RRException) ex).getMessage());
				Response.json(map);
			}else if(ex instanceof DuplicateKeyException){
				Response.error("数据库中已存在该记录");
			}else if(ex instanceof AuthorizationException){
				Response.error("没有权限，请联系管理员授权");
			}else{
				Response.error();
			}
			
			
			
//			String json = JSON.toJSONString(r);
//			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
