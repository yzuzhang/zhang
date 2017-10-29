package com.feicent.zhang.util.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 * @author yzuzhang
 * @date 2017年10月29日 下午4:38:27
 */
public class RedisPool {
    private String serverIp;
    private int port;
    private JedisPool pool;
    private JedisPoolConfig config; 

    public void init() {
        pool = new JedisPool(config, serverIp, port, 4000);
    }

    public Jedis getInstance() {
        return pool.getResource();
    }

    public void returnResource(Jedis jedis) {
    	if (jedis != null) {
    		jedis.close();
		}
    }

    @SuppressWarnings("deprecation")
	public void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public JedisPoolConfig getConfig() {
        return config;
    }

    public void setConfig(JedisPoolConfig config) {
        this.config = config;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
