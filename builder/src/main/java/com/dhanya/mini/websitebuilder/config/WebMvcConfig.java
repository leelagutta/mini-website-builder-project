package com.dhanya.mini.websitebuilder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.dhanya.mini.websitebuilder.interceptor.AuthorizationInterceptor;
/**
 * Configuring WebMVC beans (singletons) and view resolvers.

 * @author Leela
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.leela.mini.*" })
public class WebMvcConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {

	@Autowired
	private static Environment environment;

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	   public void addInterceptors(InterceptorRegistry registry) {  
		AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/page");
	}

	@Bean
	public ServletContextTemplateResolver thymeleafTemplateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(thymeleafTemplateResolver());
		return engine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(thymeleafTemplateEngine());
		return resolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}
	
	public void setEnvironment(Environment environment) {
		WebMvcConfig.environment = environment;
	}
}