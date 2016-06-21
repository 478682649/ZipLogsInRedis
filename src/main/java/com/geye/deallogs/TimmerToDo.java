package com.geye.deallogs;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.geye.model.XmlModel;

import redis.clients.jedis.Jedis;

public class TimmerToDo {

	/**
	 * 把message put到redis中压缩通道
	 * 
	 * @param messages
	 * @param starttime
	 * @param xmlModelinfo
	 * @param jedis
	 */
	public void setmessage(String messages, XmlModel xmlModelinfo, Jedis subJedis) {
		// Map<String,String> map = ZipFunction.AggregateTwoList(CSVNAME,
		// messages);
		String[] strMessage = messages.split("\\^");
		String dnscsv = "";
		for (Integer numIndex : xmlModelinfo.getIntInfo()) {
			try {
				dnscsv += strMessage[numIndex] + "^";
			} catch (Exception e) {
				dnscsv += "^";
				System.out.println("Num out of bound!" + messages + "   bound=" + numIndex);
			}
		}
		// System.out.println(dnscsv);
		try {
			subJedis.sadd(xmlModelinfo.getPrefixName(), dnscsv);
			
		} catch (Exception e) {
			System.out.println(new Date() + "jedis异常 重新发送数据");
			subJedis.sadd(xmlModelinfo.getPrefixName(), dnscsv);
		}
		// System.out.println("子类数据库id="+subJedis.getDB()+subJedis.toString());
		try {
			subJedis.incr(dnscsv);
		} catch (Exception e) {
			System.out.println(new Date() + "jedis异常 重新提交数据");
			subJedis.incr(dnscsv);
		}
		// jedis.incrBy(key, integer)
	}

	/**
	 * 提交到chennenl通道
	 * 
	 * @param endtime
	 * @param xmlModelinfo
	 * @param jedis
	 */
	public void putmessage(Long starttime, Long endtime, XmlModel xmlModelinfo, Jedis oldjedis) {

		@SuppressWarnings("rawtypes")
		Set keys = oldjedis.smembers(xmlModelinfo.getPrefixName());

		if (keys.isEmpty())
			return;

		@SuppressWarnings("rawtypes")
		Iterator it = keys.iterator();
		try {
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = oldjedis.get(key);
				// 8 count 7 截止时间
				String endvalue = starttime + "^" + endtime + "^" + key + "^" + value;
				// System.out.println(endvalue);
				oldjedis.publish(xmlModelinfo.getChannelAfterName(), endvalue);
				oldjedis.del(key);
			}
			oldjedis.del(xmlModelinfo.getPrefixName());
		} catch (Exception e) {
			System.out.println("jedis->chennel异常 ");
		}
	}

}
