//package com.css.base.log.fillter;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
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
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.css.base.log.entity.SysLogException;
//import com.css.base.log.service.ExceptionService;
//
///*
// * /single_platform 
// * */
//@WebFilter(urlPatterns="/*")
//public class ExceptionFilltter implements Filter {  
//	 @Autowired
//	private ExceptionService  exceptionService;
//	
//    @Override  
//    public void destroy() {  
//        // TODO Auto-generated method stub  
//  
//    }  
//  
//    @Override  
//    public void doFilter(ServletRequest req, ServletResponse res,  
//            FilterChain chain) throws IOException, ServletException {  
//        try {  
//            chain.doFilter(req, res);  
//        } catch (Exception e) {  
//        	 HttpServletRequest request = (HttpServletRequest) req;
//             HttpSession session = request.getSession();
//             
//             HttpServletResponse response = (HttpServletResponse) res;
//             long startTime = System.currentTimeMillis();                                   //运行前的时间
//             String requestURI = request.getRequestURI();                                   //获取访问的URI
//             requestURI = request.getQueryString() == null ? requestURI                    //所有的地址栏参数对比
//                         : (requestURI + "?" + request.getQueryString());
//            Throwable  rootCause = e;//   根异常  
//              
//            while( rootCause.getCause() != null){  
//                // 循环  直到寻找到根异常 为止。  
//                rootCause = rootCause.getCause();  
//            }  
//            String message = rootCause.getMessage();  
//            message = message == null ? "异常：" + rootCause.getClass().getName() : message;  
//            SysLogException entity = new SysLogException();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//            String format = sdf.format(new Date());
//            //时间戳
//            entity.setExId(format);
//            entity.setStackTraceMsg(request.getRemoteAddr());
//            
//            entity.setSystemCode(requestURI);
//            //异常原因
//            entity.setExceptionName(message);
//            //异常详细信息
//            //entity.setStackTraceMsg(e.toString());
//            exceptionService.save(entity);
//            System.out.println(message+":这个是异常");
//            e.printStackTrace();
//            //自定义异常
//            /*if(rootCause instanceof AccountException) {  
//                request.getRequestDispatcher("/accountException.jsp").forward(request, response);  
//            }else if(rootCause instanceof BusinessException) {  
//                request.getRequestDispatcher("/businessException.jsp").forward(request, response);  
//            }else {  
//                request.getRequestDispatcher("/exception.jsp").forward(request,  
//                        response);  
//            }  */
//                      
//              
//        }  
//  
//    }  
//  
//    @Override  
//    public void init(FilterConfig arg0) throws ServletException {  
//        // TODO Auto-generated method stub  
//  
//    }  
//  
//}  