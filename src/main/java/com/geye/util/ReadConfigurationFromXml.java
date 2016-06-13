package com.geye.util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.geye.connect.JedisPoolClient;
import com.geye.deallogs.ZipDnsLogs;
import com.geye.model.XmlModel;

public class ReadConfigurationFromXml implements Runnable{
	
	private XmlModel xmlModelinfo;
		
	public ReadConfigurationFromXml(XmlModel xmlModelinfo){
		this.xmlModelinfo = xmlModelinfo;
	}
	
	public ReadConfigurationFromXml(){
	}
	
	/**
	 * 从XML中读取配置文件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean ReadConfFromXml(){

		  SAXReader reader = new SAXReader();
		  File file = new File("/home/soft/ziplogs/conf.xml");
		  //File file = new File("E:/ziplogs/conf.xml");
		  Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("conf.xml not exist!");
			return false;
		}
		  Element root = document.getRootElement();
		  List<Element> childElements = root.elements();
		  for(Element ele : childElements)
		  {

			  ReadConfigurationFromXml myThread = new ReadConfigurationFromXml();
			  XmlModel xmlmoder = new XmlModel();
			  xmlmoder.setTimmer(Long.parseLong(ele.elementText("timmer")));
			  xmlmoder.setChannelName(ele.elementText("channelName"));
			  xmlmoder.setChannelAfterName(ele.elementText("channelAfterName"));
			  xmlmoder.setCsvName(ele.elementText("csvName"));
			  xmlmoder.setZipName(ele.elementText("zipName"));
			  xmlmoder.setPrefixName(ele.elementText("prefixName"));
			  //判空
			  if(!xmlmoder.getTestStatus())
				  return false;
			  xmlmoder.setIntInfo(ZipFunction.GetIndexByZipName(xmlmoder.getCsvName(),xmlmoder.getZipName()));
			  myThread.setXmlModelinfo(xmlmoder);
			  Thread thread = new Thread(myThread); 
			  thread.start(); 
		  }
		  return true;  
	}
	public void run() {
	    JedisPoolClient client = new JedisPoolClient();
        try {        
            client.doit();
        } catch (Exception e){
        	System.out.println("ERROR with jedispPool");
        }
		ZipDnsLogs zipDnsLogs =  new ZipDnsLogs();
		zipDnsLogs.startZip(xmlModelinfo);
	}
	public XmlModel getXmlModelinfo() {
		return xmlModelinfo;
	}
	public void setXmlModelinfo(XmlModel xmlModelinfo) {
		this.xmlModelinfo = xmlModelinfo;
	}
}
