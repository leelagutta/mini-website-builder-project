package com.dhanya.mini.websiteviewer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dhanya.mini.websiteviewer.controller.PageController;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
import com.leela.mini.weeblycommonlib.dao.JdbcConnectionPool;
import com.leela.mini.weeblycommonlib.dao.PageDao;
import com.leela.mini.weeblycommonlib.dao.PageDaoJDBCImpl;
/**
 * Weebly-viewer application beans (singletons).
 *
 * @author Leela
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:common.properties")
@ComponentScan(basePackages = { "com.leela.mini.*" })
public class ViewerAppBeans extends WebMvcConfigurerAdapter implements EnvironmentAware {

	@Autowired
    private static Environment environment;
	
	@Bean
	public PageController pageController() {
		return new PageController(pageDao(),weeblyRedisCache());
	}

	@Bean
	public WeeblyRedisCache weeblyRedisCache(){
		WeeblyRedisCache weeblyRedisCache = new WeeblyRedisCache();
		return weeblyRedisCache;
	}
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource(
				environment.getProperty("dataSource.url"),
				environment.getProperty("dataSource.user"),
				environment.getProperty("dataSource.password"));

		dataSource.setDriverClassName(environment.getProperty("dataSource.driverClass"));
		return dataSource;
	}

	@Bean
	public JdbcConnectionPool jdbcConnectionPool() {
		JdbcConnectionPool pool = new JdbcConnectionPool(dataSource());
		return pool;
	}

	@Bean(name="pageDao")
	public PageDao pageDao() {
		PageDao pageDao = new PageDaoJDBCImpl(jdbcConnectionPool());
		return pageDao;
	}

	public void setEnvironment(Environment environment) {
		ViewerAppBeans.environment = environment; 	
	}
	 
}