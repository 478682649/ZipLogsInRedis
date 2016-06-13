package com.geye.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFunction {
	
	private static final Logger logger = LoggerFactory.getLogger(ZipFunction.class);
	/**
	 * 实现python zip 功能
	 * @param before_arg 
	 * @return
	 */
	public static Map<String,String> AggregateTwoList (String before_Name,String before_value){
		
		if(before_Name.isEmpty()||before_value.isEmpty())
			return null;
		Map<String,String> map = new HashMap<String,String>();
		//cat exception
		String[] listName= before_Name.split("\\,",-1);
		
		String[] listValue= before_value.split("\\^",-1);
		
		if (listName.length != listValue.length)
			{
			logger.error("Is not the same as the length of Name and value .   data="+before_value);
			return null;
			}
		
		for (int i = 0 ;i<listName.length;i++)
		{
			map.put(listName[i], listValue[i]);
		}
		return map;
	}
	
	/**
	 * 根据zipName和csvName获取位置 
	 * @return 数组
	 */
	public static List<Integer> GetIndexByZipName(String csvName,String zipName){
		if(csvName.isEmpty()||zipName.isEmpty())
			return null;
		String[] listName= csvName.split("\\,",-1);

		String[] listZipName= zipName.split("\\,",-1);
		
		List<Integer> intInfo = new ArrayList<Integer>();
		
		for (int i = 0 ;i<listZipName.length;i++)
		{	
			for(int j = 0 ;j<listName.length;j++)
			{
				if(listName[j].equals(listZipName[i]))
				{
					intInfo.add(j);
					break;
				}
			}
		}

		return intInfo;
	}
	
}
