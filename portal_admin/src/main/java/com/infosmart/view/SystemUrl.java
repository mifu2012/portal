package com.infosmart.view;

/**
 * 系统URL
 * 
 * @author infosmart
 * 
 */
public class SystemUrl {
	private String systemUrl;
	private String systemName;

	public SystemUrl(String systemUrl, String systemName) {
		super();
		this.systemUrl = systemUrl;
		this.systemName = systemName;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
