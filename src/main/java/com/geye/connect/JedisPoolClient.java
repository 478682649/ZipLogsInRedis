package com.geye.connect;

import com.geye.util.ReadConfigurationRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolClient extends ReadConfigurationRedis{

    
    private static JedisPool pool = null;  
    private static Jedis jedis  = null;
    
    //初始化  进入函数
    public void doit() throws Exception{
        _init();
    }

    //初始化连接池配置参数
    private void _init() throws Exception{
    	//读取配置文件
    	if(!ReadConfigurationRedis.SetRedisConfFromProperty())
    		return;
    	
    	if(NEEDPASS)
    	{
    		pool = new JedisPool(config,REIDSHOST,REIDSPORT,TIMEOUT,REDISPASSWORD);
    		
    	}else if(REIDSPORT != 6379)
    	{
    		pool = new JedisPool(config,REIDSHOST,REIDSPORT);
    	}
    	else if (TIMEOUT != 300)
    	{
    		pool = new JedisPool(config,REIDSHOST,REIDSPORT,TIMEOUT);
    	}
    	else 
    	{
    		pool = new JedisPool(config,REIDSHOST);
    	}
        jedis = pool.getResource();
    }

    //回收连接池资源
    private void _destroy() throws Exception{
        pool.returnResource(jedis);
        pool.destroy();
    }

    public void destroy() throws Exception{
        _destroy();
    }
   
	public static JedisPool getPool() {
		return pool;
	}

	public static Jedis getJedis() {
		return jedis;
	}

	public static void setPool(JedisPool pool) {
		JedisPoolClient.pool = pool;
	}

	public static void setJedis(Jedis jedis) {
		JedisPoolClient.jedis = jedis;
	}
	 
}
