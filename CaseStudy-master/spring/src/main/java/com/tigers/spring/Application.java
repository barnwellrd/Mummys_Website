package com.tigers.spring;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.tigers.services.UserService;

@SpringBootApplication
@ComponentScan(basePackages="com.tigers")
public class Application {

	//<bean class="org.springframework.context.annotation.CommonAnnotationBeanPostProcessor"/>
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
		
	//addresourcehandler to change location of view pages (from webapp to something else, etc.)

	
	/*@Bean
	public CommonAnnotationBeanPostProcessor BeanPostProcessor() {
		CommonAnnotationBeanPostProcessor BeanPostProcessor = new CommonAnnotationBeanPostProcessor();
		return BeanPostProcessor;
	}*/
	
	

	

}
