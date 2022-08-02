package edu.upenn.cis.cis455.m2.server;

public class Cookie {
	
	String name;
	String value;
	
	int maxAge = -1;
	String path;
	boolean secure;
	boolean httpOnly = false;
	
	public Cookie(String n, String v) {
		this.name = n;
		this.value = v;
	}

	public void setPath(String p) {
		// TODO Auto-generated method stub
		this.path = p;
	}

	public void setMaxAge(int m) {
		// TODO Auto-generated method stub
		this.maxAge = m;
	}

	public void setSecure(boolean s) {
		// TODO Auto-generated method stub
		this.secure = s;
	}

	public void httpOnly(boolean h) {
		// TODO Auto-generated method stub
		this.httpOnly = h;
	}

}
