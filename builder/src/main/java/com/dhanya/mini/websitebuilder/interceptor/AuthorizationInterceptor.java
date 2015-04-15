package com.dhanya.mini.websitebuilder.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.leela.mini.weeblycommonlib.cache.WeeblyCacheKey;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
/**
 * Filter that intercepts weebly-builder api endpoits for authentication of the user's authApiKey cookie.
 * 
 * @author Leela
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	private WeeblyRedisCache weeblyRedisCache = new WeeblyRedisCache();
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length < 1) {
           response.sendRedirect("/login");
           return false;
       }
       
       for(Cookie cookie: cookies) {
    	   if(cookie.getName().equals("authApiKey")) {
    		   String cookieVal = cookie.getValue();
    		   if(!StringUtils.isEmpty(cookieVal)) {
    			   String string = weeblyRedisCache.getInstance().get(WeeblyCacheKey.USER_AUTH_KEY + cookieVal);
    			   if(!WeeblyRedisCache.isEmptyCacheValue(string)) {
    				   return true;
    			   }
    		   }
    	   }
       }
       response.sendRedirect("/login");		
       return false;
	}
}