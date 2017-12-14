package com.css.base.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class ResponseBase {
	private HttpServletResponse response=null;
	ServletOutputStream os=null;
	protected ResponseBase(){
		response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected ResponseBase setHeader(String headerName,String headerVal){
		response.setHeader(headerName,headerVal);
		return this;
	}
	
	protected ResponseBase setContentType(String contentType){
		response.setContentType(contentType);
		return this;
	}
	protected void sendRedirect(String url){
		try {
			response.setStatus(302);
			response.addHeader("location", url);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void write(InputStream is){
		try {
			int length = 0;
			byte[] buffer = new byte[2048];
			while ((length = is.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void write(byte[] bytes){
			try {
				os.write(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	protected void write(String message){
		write(message.getBytes());
	}
}
