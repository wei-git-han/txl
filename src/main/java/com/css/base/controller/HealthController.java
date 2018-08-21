package com.css.base.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@ResponseBody
	@RequestMapping("/health.noauth")
	public void health(){
		dbStatusJDBC();
	}

	private void dbStatusJDBC(){
		JSONObject json=new JSONObject();
		String result="";//
		String msg="";//
		String failedMsg="数据库异常！";
		long time=new Date().getTime();
		try {
			Connection conn = dataSource.getConnection();
			if(conn!=null){
				result="success";
				msg="APP健康正常！";
				conn.close();
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
}
