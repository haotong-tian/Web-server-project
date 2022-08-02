package edu.upenn.cis.cis455.m1.server;

import java.util.*;
import java.net.Socket;

import edu.upenn.cis.cis455.m1.interfaces.Request;

public class HttpRequest extends Request {
	
	String remoteIp;
	Map<String, String> headers;
	Map<String, List<String>> params;
	String uri;
	
	public HttpRequest() {
		
	};
	
	public HttpRequest(String ip, Map<String, String> h, Map<String, List<String>> p, String u) {
		// TODO Auto-generated constructor stub
		this.remoteIp = ip;
		this.headers = h;
		this.params = p;
		this.uri = u;
	}

	@Override
	public String requestMethod() {
		// TODO Auto-generated method stub
		return headers.get("Method");
	}

	@Override
	public String host() {
		// TODO Auto-generated method stub
		return headers.get("host");
	}

	@Override
	public String userAgent() {
		// TODO Auto-generated method stub
		return headers.get("user-agent");
	}

	@Override
	public int port() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String pathInfo() {
		// TODO Auto-generated method stub
		return "www" + this.uri();
	}

	@Override
	public String url() {
		// TODO Auto-generated method stub
		return "http://" + host() + uri();
	}

	@Override
	public String uri() {
		// TODO Auto-generated method stub
		return this.uri;
	}

	@Override
	public String protocol() {
		// TODO Auto-generated method stub
		return headers.get("protocolVersion");
	}

	@Override
	public String contentType() {
		// TODO Auto-generated method stub
		return headers.get("accept");
	}

	@Override
	public String ip() {
		// TODO Auto-generated method stub
		return this.remoteIp;
	}

	@Override
	public String body() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int contentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String headers(String name) {
		// TODO Auto-generated method stub
		return headers.get(name);
	}

	@Override
	public Set<String> headers() {
		// TODO Auto-generated method stub
		return headers.keySet();
	}

}
