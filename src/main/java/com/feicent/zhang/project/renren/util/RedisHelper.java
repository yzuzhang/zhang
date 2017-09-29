package com.feicent.zhang.project.renren.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisHelper{
    
	static Log log = LogFactory.getLog(RedisHelper.class);
	
	//本地Redis的端口号
    private static int PORT = 6379;
    //本地Redis服务器IP
    private static String ADDR = "127.0.0.1";//192.168.172.97
    
    //可用连接实例的最大数目，默认值为8
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 8;//1000
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 2;//
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    static {
    	init();
    }
    
    public static void init() {
    	try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);//可用连接实例的最大数目
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            System.out.println("Redis Pool ["+ ADDR +":"+ PORT +"] init OK...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ADDR+" JedisPool------>"+e.getMessage());
        }
	}
    
    /**
     * @return 获取Jedis实例
     */
    public static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            }
        } catch (Exception e) {
        	System.out.println("getJedis------>"+ e.getMessage());
            log.error("getJedis------>"+ e.getMessage());
        }
        return null;
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    @SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }
    
    /**
     * Delete all the keys of the currently selected DB
     */
    public static void flushRedisKey(){
		try {
			Jedis redis = getJedis();
			redis.flushDB();
			returnResource(redis);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("flushRedisKey------>"+e.getMessage());
		}
	}
    
    @SuppressWarnings("static-access")
	public static void main(String[] args){
    	  RedisHelper redisHelper = new RedisHelper();
    	  Jedis redis = redisHelper.getJedis();
    	  /*redis.set("111", "hello redis");
    	  log.info("Redis保存[111]成功!");
    	  log.info("redis.get(\"111\")="+redis.get("111"));
    	  redis.del("111");
    	  log.info("删除111成功!");
    	  log.info(redis.get("111"));*/
    	  
    	  redis.hset("zhang", "sdk_0", "1212");
    	  log.info("Redis保存[sdk_0]成功!");
    	  log.info("sdk_0---->"+redis.hget("zhang", "sdk_0"));
    	  
    	  redis.hset("zhang", "sdk_1", "3434");
    	  log.info("Redis保存[sdk_1]成功!");
    	  log.info("sdk_1---->"+redis.hget("zhang", "sdk_1"));
    	  redis.del("zhang");
    	  RedisHelper.returnResource(redis);
    }
}