package com.geye.deallogs;

import java.util.Timer;
import java.util.TimerTask;

import com.geye.connect.JedisPoolClient;
import com.geye.connect.Listener;
import com.geye.model.XmlModel;

import redis.clients.jedis.Jedis;

public class ZipDnsLogs extends  TimerTask{
	
	private XmlModel xmlModelinfo ;
	
	private Long startTime = System.currentTimeMillis();
	
	private Long endTime = System.currentTimeMillis();
	
	//onmessage的通道
	protected Jedis jedis = JedisPoolClient.getPool().getResource();
	
	private Integer chennelNum = 0; 
	
	public ZipDnsLogs(XmlModel xmlModelinfo,Jedis subJedis){
		this.subJedis = subJedis;
		this.xmlModelinfo = xmlModelinfo;
		
	}
	public ZipDnsLogs(){
	}
	
	
	//固定时间 换一个通道 用另一个jedis对象处理
	protected Jedis subJedis ;
	
	public  void  startZip(XmlModel xmlModelinfos){

	
		subJedis= JedisPoolClient.getPool().getResource();
		System.out.println("Start Thread");
		xmlModelinfo = xmlModelinfos;
		Long period = xmlModelinfos.getTimmer();
		TimerTask  task = new ZipDnsLogs(xmlModelinfo,subJedis);  
		Timer timer = new Timer();
		//默认通道
		subJedis.select(0);
		
		Listener listener = new Listener(xmlModelinfo,subJedis);
		
		timer.scheduleAtFixedRate(task,period*1000, period*1000);
		
		//可以订阅多个频道  
		//订阅得到信息在lister的onMessage(...)方法中进行处理  
		//jedis对象线程唯一      线程安全
		jedis.subscribe(listener, xmlModelinfo.getChannelName());
		
		
		
	}

	@Override
	public void run() {
		
		//保存当前对象
		Jedis oldsubJedis = JedisPoolClient.getPool().getResource();
		
		oldsubJedis.select(chennelNum%2);
		
		//线程安全
		chennelNum = ++chennelNum%2;
		
		subJedis.select(chennelNum);
		//System.out.println("父类数据库id="+subJedis.getDB()+subJedis.toString());
		TimmerToDo timeToDo = new TimmerToDo();
		endTime = System.currentTimeMillis();
		
		timeToDo.putmessage(startTime,endTime, xmlModelinfo, oldsubJedis);
		
		startTime = System.currentTimeMillis();
		
		//放回jedis对象
		JedisPoolClient.getPool().returnResource(oldsubJedis);
	}
	
	
	

	
}
