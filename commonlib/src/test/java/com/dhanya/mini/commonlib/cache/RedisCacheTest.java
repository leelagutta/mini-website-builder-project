package com.dhanya.mini.commonlib.cache;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Redis Cache tests.
 * 
 * @author Leela
 */
public class RedisCacheTest {

	private static RedisCache weeblyCache;
	
	@BeforeClass
	public static void build() {
		weeblyCache = new RedisCache();
	}
	
	@Test
	public void testAuthCache() {
		String apiKey = UUID.randomUUID().toString();
		weeblyCache.getInstance().setex(CacheKey.USER_AUTH_KEY + apiKey, 2592000, "t");
		String string = weeblyCache.getInstance().get(CacheKey.USER_AUTH_KEY + apiKey);
		assertNotNull(string);	
	}
	
	@Test
	public void testPagesCache() {
		String pages = "{'uuid':'jhvjh', 'name':'homepage'}";
		weeblyCache.getInstance().setex(CacheKey.USER_PAGES + pages, 2592000, "t");
		String string = weeblyCache.getInstance().get(CacheKey.USER_PAGES + pages);
		assertNotNull(string);	
	}
	
	@Test
	public void testPageCache() {
		String uuid = UUID.randomUUID().toString();
		weeblyCache.getInstance().setex(CacheKey.USER_PAGE + uuid, 2592000, "t");
		String cacheValue = weeblyCache.getInstance().get(CacheKey.USER_PAGE + uuid);
		assertNotNull(cacheValue);
	}
	
	@Test
	public void testPageCacheExpiry(){
		String uuid = UUID.randomUUID().toString();
		weeblyCache.getInstance().setex(CacheKey.USER_PAGE + uuid, 1, "t");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cacheValue = weeblyCache.getInstance().get(CacheKey.USER_PAGE + uuid);
		assertNull(cacheValue);	
	}
}