//package com.css.base.log.fillter;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.css.base.controller.AbstractController;
//import com.css.base.entity.SysUser;
//import com.css.base.log.entity.SysLogOperator;
//import com.css.base.log.service.OperatorLogService;
//import com.css.base.utils.ShiroUtils;
//
///* 
// * 
// * */
//@WebFilter(urlPatterns="/*")
//public class OperatorFillter implements Filter{
//	@Autowired
//	private OperatorLogService operatorLogService;
//	
//    private Log log = LogFactory.getLog(this.getClass());
//    private String filterName;
//    public void init(FilterConfig config) throws ServletException {
//          filterName = config.getFilterName();//获取 Filter的 name，启动Filter
//          //log.info("启动 Filter: " + filterName);//
//          //System.out.println("启动 Filter: " + filterName);
//          
//    }
//    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain)
//                 throws IOException, ServletException {
//    	
//    	
//          HttpServletRequest request = (HttpServletRequest) req;
//          HttpSession session = request.getSession();
//          
//          HttpServletResponse response = (HttpServletResponse) res;
//          long startTime = System.currentTimeMillis();                                   //运行前的时间
//          String requestURI = request.getRequestURI();                                   //获取访问的URI
//          requestURI = request.getQueryString() == null ? requestURI                    //所有的地址栏参数对比
//                      : (requestURI + "?" + request.getQueryString());
//          long endTime = System.currentTimeMillis();
//          //消耗的总时间
//          //log.info(request.getRemoteAddr() + " 访问了 " + requestURI + ", 总用时 "+ (endTime - startTime) + " 毫秒。");
//          //拿到user对象
//          
//          //SysUserEntity user=ShiroUtils.getUserEntity();
//          String user = "aa";
//        /*  if(user==null){
//        	  Object attribute2 = session.getAttribute("url");
//        	  session.removeAttribute("url");
//        	  if(attribute2==null) {
//        		  attribute2="";
//        	  }
//        	  String url = attribute2.toString()+requestURI+",";
//        	  session.setAttribute("url", url);
//        	  System.out.println(session.getAttribute("url").toString());
//        	  
//          }else{*/
//          
//	          if(!requestURI.endsWith(".jpg")&&!requestURI.endsWith(".css")&&!requestURI.endsWith(".js")) {
//	          
//		        	  SysLogOperator entity = new SysLogOperator();
//		        	  entity.setOpTime(new Date());
//		        	  //entity.setOperator(session.getAttribute("url").toString());
//		        	  entity.setOperator(requestURI);
//		        	  entity.setUserId(".."); //用户id
//		        	  entity.setSystemCode(request.getRemoteAddr());
//		        	  operatorLogService.save(entity);
//		        	  session.removeAttribute("url");
//          }
//	      chain.doFilter(request, response);   
//    }
//    public void destroy() { //销毁时记录日志
//    }
//}