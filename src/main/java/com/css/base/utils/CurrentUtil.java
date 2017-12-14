package com.css.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentUtil {
	public static final ThreadLocal<HttpServletResponse> responseLocal=new ThreadLocal<HttpServletResponse>();
	public static final ThreadLocal<HttpServletRequest> requestLocal=new ThreadLocal<HttpServletRequest>();
	
	public static HttpServletResponse getResponse(){
		return responseLocal.get();
	}
	public static void setResponse(HttpServletResponse response){
		responseLocal.set(response);
	}
	
	public static HttpServletRequest getRequest() {
		return requestLocal.get();
	}
	public static void setRequest(HttpServletRequest request){
		requestLocal.set(request);
	}
}
