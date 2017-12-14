//package com.css.base.log.fillter;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.css.base.log.dao.ExceptionDao;
//import com.css.base.log.entity.SysLogException;
//import com.css.base.log.entity.SysLogOperator;
//import com.css.base.log.entity.SysLogPerformance;
//import com.css.base.log.service.OperatorLogService;
//import com.css.base.log.service.PerformanceService;
//
////过滤的精度  
//@WebFilter(urlPatterns="/*")
//public class PerformanceFilter implements Filter {
//	@Autowired
//	private PerformanceService performanceService;
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
//          HttpServletRequest request = (HttpServletRequest) req;
//          HttpServletResponse response = (HttpServletResponse) res;
//          long startTime = System.currentTimeMillis();                                   //运行前的时间
//          String requestURI = request.getRequestURI();                                   //获取访问的URI
//          requestURI = request.getQueryString() == null ? requestURI                    //所有的地址栏参数对比
//                      : (requestURI + "?" + request.getQueryString());
//          chain.doFilter(request, response);
//          long endTime = System.currentTimeMillis();
//          SysLogPerformance entity = new SysLogPerformance();
//          entity.setExecuteTime(new Date());
//          //执行的方法
//          //entity.setMethod(method);
//          //时间戳
//          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//          String format = sdf.format(new Date());
//          entity.setPerId(format);
//          //总用时
//          entity.setUseTime(endTime - startTime);
//          //访问的链接url
//          entity.setUrl(requestURI);
//          //ip地址
//          entity.setSystemCode(request.getRemoteAddr());
//          String method = request.getMethod();
//          //请求方式
//          entity.setMethod(method);
//          //保存操作记录
//          performanceService.save(entity);
//          
//          //消耗的总时间
//          //log.info(request.getRemoteAddr() + " 访问了 " + requestURI + ", 总用时 "+ (endTime - startTime) + " 毫秒。");
//          //System.out.println(request.getRemoteAddr() + " 访问了 " + requestURI + ", 总用时 "+ (endTime - startTime) + " 毫秒。");
//    }
//    public void destroy() { //销毁时记录日志
//    }
//}