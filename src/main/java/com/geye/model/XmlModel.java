package com.geye.model;

import java.io.Serializable;
import java.util.List;

public class XmlModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679104674323669122L;

	private Long timmer;

	private String channelName;

	private String channelAfterName;

	private String csvName;

	private String zipName;

	private List<Integer> intInfo;

	private String prefixName;

	private Long timestatus = (long) 0;

	public boolean getTestStatus()
	{
		if(timmer==null || channelName == null || channelAfterName == null 
			  ||csvName == null||zipName == null||prefixName==null)
			return false;
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getChannelName() {
		return channelName;
	}

	public String getChannelAfterName() {
		return channelAfterName;
	}

	public String getCsvName() {
		return csvName;
	}

	public String getZipName() {
		return zipName;
	}

	public List<Integer> getIntInfo() {
		return intInfo;
	}

	public String getPrefixName() {
		return prefixName;
	}



	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setChannelAfterName(String channelAfterName) {
		this.channelAfterName = channelAfterName;
	}

	public void setCsvName(String csvName) {
		this.csvName = csvName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public void setIntInfo(List<Integer> intInfo) {
		this.intInfo = intInfo;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public Long getTimestatus() {
		return timestatus;
	}

	public Long getTimmer() {
		return timmer;
	}

	public void setTimmer(Long timmer) {
		this.timmer = timmer;
	}

	public void setTimestatus(Long timestatus) {
		this.timestatus = timestatus;
	}

}
