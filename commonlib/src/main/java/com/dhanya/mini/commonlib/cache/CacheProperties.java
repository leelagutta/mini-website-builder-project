package com.dhanya.mini.commonlib.cache;

import java.util.List;
import redis.clients.jedis.HostAndPort;

/**
 * website's cache cluster properties.
 *  
 *  @author Leela
 */
public class CacheProperties {

	private List<HostAndPort> cacheClusterNodes;

	public CacheProperties(List<HostAndPort> cacheClusterNodes) {
		this.setCacheClusterNodes(cacheClusterNodes);
	}

	public List<HostAndPort> getCacheClusterNodes() {
		return cacheClusterNodes;
	}

	public void setCacheClusterNodes(List<HostAndPort> cacheClusterNodes) {
		this.cacheClusterNodes = cacheClusterNodes;
	}
}