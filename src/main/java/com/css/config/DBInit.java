package com.css.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DBInit {

//    @Autowired
//    private DruidProperties properties;
    @Autowired
    private Environment env ;
    public DBInit(){
	        
    }
//    @Bean
//	public String InitDatabase(){
//		try{
//	    	   creatUser();
//	       } catch(Exception e){
//	    	   e.printStackTrace();;
//	       }
//	        
//		return "create user ";
//		
//	}
    public void creatUser(){
    	String initialize = env.getProperty("spring.datasource.initialize");
    	
    	Statement st = null;
		Connection conn = null;
    	if("true".equals(initialize)){
    		String url = env.getProperty("spring.datasource.url");
        	String username = env.getProperty("spring.datasource.username");
        	String password = env.getProperty("spring.datasource.password");
        	String driverClassName = env.getProperty("spring.datasource.driver-class-name");
//        	String creatName = properties.getUsername();
//        	String creatPassword = properties.getPassword();
        	String sql  = env.getProperty("css.creatUserSQL");
        	String[] sqlArray = sql.split(";");
    		try {
    			//加载驱动
				Class.forName(driverClassName);
				//建立连接
				 conn =DriverManager.getConnection(url, username, password);
				//创建执行对象
				 st = conn.createStatement();
				//执行SQL语句
				for(String sqlScript : sqlArray){
					st.execute(sqlScript);
				}					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		DBClose(st,conn);
    	}	
    }
	public static void close(Connection con) {
	    if (con != null)
	        try {
	            con.close();
	        } catch (SQLException e) {
	            // 不做任何处理，静默处理
	        }
	}

	//public static void close(ResultSet rs) {
//	    if (rs != null)
//	        try {
//	            rs.close();
//	        } catch (SQLException e) {
//	            // 不做任何处理，静默处理
//	        }
	//}

	public static void close(Statement stmt) {
	    if (stmt != null)
	        try {
	            stmt.close();
	        } catch (SQLException e) {
	            // 不做任何处理，静默处理
	        }
	}

	public static void DBClose( Statement stmt, Connection conn) {
	   
	        try {
	            close(stmt);
	        } finally {
	            close(conn);
	        }
	   }
	
}

