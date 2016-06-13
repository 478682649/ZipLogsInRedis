package com.geye.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.JedisPoolConfig;

public class ReadConfigurationRedis {
	
	protected static JedisPoolConfig config = null;

	protected static String REIDSHOST = "127.0.0.1";

	protected static boolean NEEDPASS = false;

	protected static int REIDSPORT = 6379;

	protected static int TIMEOUT = 300;

	protected static Integer MAXACTIVE = 100;

	protected static Integer MAXIDLE = 20;

	protected static Integer MAXWAIT = 1000;

	protected static boolean TestOnBorrow = true;

	protected static String REDISPASSWORD = "";

	public static boolean SetRedisConfFromProperty() {
		Properties pro = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream("/home/soft/ziplogs/Redisconf.properties");
			//in = new FileInputStream("E:/ziplogs/Redisconf.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Redis Config not exits!!!!!!");
			return false;
		}

		try {
			pro.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	

		if (pro.containsKey("REIDSHOST"))
			REIDSHOST = pro.getProperty("REIDSHOST");

		if (pro.containsKey("REDISPASSWORD")) 
			{
				NEEDPASS = true;
				REDISPASSWORD = pro.getProperty("REDISPASSWORD");
			}
		if (pro.containsKey("REIDSPORT"))
			REIDSPORT = Integer.parseInt(pro.getProperty("REIDSPORT"));

		if (pro.containsKey("TIMEOUT"))
			TIMEOUT = Integer.parseInt(pro.getProperty("TIMEOUT"));

		if (pro.containsKey("MAXACTIVE"))
			MAXACTIVE = Integer.parseInt(pro.getProperty("MAXACTIVE"));

		if (pro.containsKey("MAXIDLE"))
			MAXIDLE = Integer.parseInt(pro.getProperty("MAXIDLE"));
		
		if (pro.containsKey("MAXWAIT"))
			MAXWAIT = Integer.parseInt(pro.getProperty("MAXWAIT"));
		
		if (pro.containsKey("TestOnBorrow"))
			TestOnBorrow = pro.getProperty("TestOnBorrow").equalsIgnoreCase("true");

		config = new JedisPoolConfig();
//		config.setMaxActive(MAXACTIVE);
//		config.setMaxIdle(MAXIDLE);
//		config.setMaxWait(MAXWAIT);
		config.setTestOnBorrow(TestOnBorrow);
		
		return true;

	}

}
