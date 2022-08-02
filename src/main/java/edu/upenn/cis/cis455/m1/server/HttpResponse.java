package edu.upenn.cis.cis455.m1.server;

import edu.upenn.cis.cis455.m1.interfaces.Response;

public class HttpResponse extends Response {
	
	String headers;
	
	@Override
	public String getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}
	
	public void setHeaders(String h) {
		this.headers = h;
	}

}
