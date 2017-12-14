package com.css.base.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.css.base.utils.Response;

/**
 * APP健康检查API
 * 
 * @author 中软信息系统工程有限公司
 * @email  
 * @date 2017-9-22 13:24:11
 */
@Controller
@RequestMapping("/api/status")
public class HealthController {
	
	@Autowired
	private DataSource dataSource;
	
	//@Autowired
	//private TTaskCategoryService tTaskCategoryService;
	@Value("${druid.url}")
	private String url;
	@Value("${druid.username}")
	private String username;
	@Value("${druid.password}")
	private String password;
	
	@ResponseBody
	@RequestMapping("/health")
	public void health(){
		//dbStatus();//数据库检查
		dbStatusJDBC();
	}

	private void dbStatusJDBC(){
		JSONObject json=new JSONObject();
		String result="";//
		String msg="";//
		String failedMsg="数据库异常！";
		long time=new Date().getTime();
		try {
			Connection conn=DriverManager.getConnection(url, username, password);
			if(conn!=null){
				result="success";
				msg="APP健康正常！";
			}else{
				result="failed";
				msg=failedMsg;
				System.out.println("数据库连接失败！");
			}
		} catch (SQLException e) {
			result="failed";
			msg=failedMsg;//+e.getMessage();
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}finally {
			json.put("result", result);
			json.put("msg", msg);
			json.put("time", time);
			Response.json(json);
		}
	}
	
	@SuppressWarnings("unused")
	private void dbStatus_bak() {
		JSONObject json=new JSONObject();
		String result="";//
		String msg="";//
		String failedMsg="数据库异常！";
		long time=new Date().getTime();
		try {
		Connection conn=	dataSource.getConnection();
			result=conn!=null?"success":"failed";
			msg=conn!=null?"APP健康正常！":failedMsg;
			if(conn!=null&&!conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			result="failed";
			msg=e.getMessage();
		}finally {
			json.put("result", result);
			json.put("msg", msg);
			json.put("time", time);
			Response.json(json);
		}
	}
	/**
	 * 1、结合具体的业务类，检查数据能否正常连接。
	 */
	@SuppressWarnings("unused")
	private void dbStatus(){
		JSONObject json=new JSONObject();
		String result="";//
		String msg="";//
		String failedMsg="数据库异常！";
		long time=new Date().getTime();
		try {
			//tTaskCategoryService.queryObject("-1");
			result="success";
			msg="APP健康正常！";
		} catch (Exception e) {
			//e.printStackTrace();
			result="failed";
			msg=failedMsg;//+e.getMessage();
		}finally {
			json.put("result", result);
			json.put("msg", msg);
			json.put("time", time);
			Response.json(json);
		}
	}
}
