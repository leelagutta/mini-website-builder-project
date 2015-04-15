package com.dhanya.mini.commonlib.cache;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * Redis LRU cache.
 * 
 * @author Leela
 */
public class RedisCache {

	private Jedis jedisNode;
	
	public static final int ONE_MONTH_TTL = 2592000;

	public RedisCache() {
		synchronized (RedisCache.class) {
			jedisNode = new Jedis("localhost");
		}
	}

	//TODO: Cluster seems to be not supported for Jedis client lib. 
//	private void buidCacheCluster() {
//		List<HostAndPort> cacheClusterNodes = redisClusterProperties.getCacheClusterNodes();
//		if (!CollectionUtils.isEmpty(cacheClusterNodes)) {
//			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//			jedisClusterNodes.addAll(cacheClusterNodes);
//			cacheCluster = new JedisCluster(jedisClusterNodes);
//		} else {
//			throw new RuntimeException("No Redis Cache instances found during init");
//		}
//	}
//	
	public Jedis getInstance() {
		return jedisNode;
	}
	
	public static boolean isEmptyCacheValue(String cacheValue) {
		if (StringUtils.isEmpty(cacheValue) || cacheValue.equals("-1")) {
			return true;
		}
		return false;
	}
}