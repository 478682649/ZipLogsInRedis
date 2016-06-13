package com.geye.deallogs;

import com.geye.util.ReadConfigurationFromXml;


public class CompressionLogs {

	public static void main(String[] args) {
		if(!ReadConfigurationFromXml.ReadConfFromXml())
			System.out.print("ERROR");
	}
	
	

}
