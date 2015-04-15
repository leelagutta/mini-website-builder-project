package com.dhanya.mini.websitebuilder.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.dhanya.mini.websitebuilder.controller.AuthController;
import com.dhanya.mini.websitebuilder.controller.HomeController;
import com.dhanya.mini.websitebuilder.controller.PageController;
import com.dhanya.mini.websitebuilder.utils.GoogleOauthProperties;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
import com.leela.mini.weeblycommonlib.dao.JdbcConnectionPool;
import com.leela.mini.weeblycommonlib.dao.PageDao;
import com.leela.mini.weeblycommonlib.dao.PageDaoJDBCImpl;
import com.leela.mini.weeblycommonlib.dao.UserDao;
import com.leela.mini.weeblycommonlib.dao.UserDaoJDBCImpl;
/**
 * Configuring application beans (singletons).
 * 
 * @author Leela
 */
@Configuration
@PropertySource("classpath:common.properties")
@ComponentScan(basePackages = { "com.leela.mini.*" })
public class AppConfig implements EnvironmentAware {

	@Autowired
    private static Environment environment;
	
	@Bean 
	public GoogleOauthProperties googleOauthProps() {
		String tokenRequestUrl = environment.getProperty("oauth.google.token.request.url");
		String authUrl = environment.getProperty("oauth.google.auth.url");
		String apiScope = environment.getProperty("oauth.google.api.scope");
		String userProfileResourceApiUrl = environment.getProperty("oauth.google.api.userprofile.url");
			
		GoogleOauthProperties googleOuthProperties = new GoogleOauthProperties();
		googleOuthProperties.setClientId(environment.getProperty("oauth.google.client.id"));
		googleOuthProperties.setClientSecret(environment.getProperty("oauth.google.client.secret"));
		googleOuthProperties.setRedirectUri(environment.getProperty("oauth.google.client.redirect.url"));
		googleOuthProperties.setTokenRequestUrl(tokenRequestUrl);
		googleOuthProperties.setAuthenticationUrl(authUrl);
		googleOuthProperties.setApiScope(apiScope);
		googleOuthProperties.setUserProfileApiUrl(userProfileResourceApiUrl);
		googleOuthProperties.setGrantType("authorization_code");
		return googleOuthProperties;
	}
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(
				environment.getProperty("dataSource.url"),
				environment.getProperty("dataSource.user"),
				environment.getProperty("dataSource.password"));
		dataSource.setDriverClassName(environment
				.getProperty("dataSource.driverClass"));
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
	
	@Bean(name="userDao")
	public UserDao userDao() {
		UserDao userDao = new UserDaoJDBCImpl(jdbcConnectionPool());
		return userDao;
	}
	
	@Bean
	public AuthController authController() {
		return new AuthController(googleOauthProps(),userDao(), weeblyRedisCache());
	}
	
	@Bean
	public HomeController homeController() {
		return new HomeController(googleOauthProps());
	}
	
	@Bean
	public PageController pageController() {
		return new PageController(pageDao(), weeblyRedisCache());
	}
	
	@Bean
	public WeeblyRedisCache weeblyRedisCache(){
		WeeblyRedisCache weeblyRedisCache = new WeeblyRedisCache();
		return weeblyRedisCache;
	}
	
	public void setEnvironment(Environment environment) {
		AppConfig.environment = environment; 	
	}
}