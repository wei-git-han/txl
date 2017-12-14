package com.css;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class TxlApplication extends SpringBootServletInitializer  implements WebApplicationInitializer{


//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(TxlApplication.class);
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TxlApplication.class, args);
    }
}