package edu.upenn.cis.cis455.storage;

import java.io.Serializable;

public class WebpageInfo implements Serializable {
	
	String url;
	String rawContent;
	Object lastAccess = null;
	
	public WebpageInfo(String u, String r) {
		this.url = u;
		this.rawContent = r;
	}
	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String u) {
		this.url = u;
	}
	
	public String getRawContent() {
		return this.rawContent;
	}
	
	public Object getLassAccess() {
		return this.lastAccess;
	}
	
	public void setRawContent(String r) {
		this.rawContent = r;
	}
	
	public void setLastAccess(Object l) {
		this.lastAccess = l;
	}
	
}