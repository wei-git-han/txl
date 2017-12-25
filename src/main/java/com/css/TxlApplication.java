package com.css;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;
/**
 * jar包配置
 */
@SpringBootApplication
public class TxlApplication {

	public static void main(String[] args) {

		SpringApplication.run(TxlApplication.class, args);
		
	}
}
/**
 * war包配置
 */
//@SpringBootApplication
//public class TxlApplication extends SpringBootServletInitializer  implements WebApplicationInitializer{
//
//	  @Override
//	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	        return application.sources(TxlApplication.class);
//	    }
//	
//	public static void main(String[] args) {
//
//		SpringApplication.run(TxlApplication.class, args);
//	}
//}