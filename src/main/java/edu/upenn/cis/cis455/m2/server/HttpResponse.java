package edu.upenn.cis.cis455.m2.server;

import java.util.*;

import edu.upenn.cis.cis455.m2.interfaces.Response;

public class HttpResponse extends Response {
	
	Map<String, Cookie> cookies;
	String headers;
	int statusCode;
	
	public HttpResponse() {
		cookies = new TreeMap<String, Cookie>();
	}
	
	@Override
	public void header(String header, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redirect(String location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redirect(String location, int httpStatusCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cookie(String name, String value) {
		// TODO Auto-generated method stub
		cookie("", name, value, -1, false, false);
	}

	@Override
	public void cookie(String name, String value, int maxAge) {
		// TODO Auto-generated method stub
		cookie("", name, value, maxAge, false, false);
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured) {
		// TODO Auto-generated method stub
		cookie("", name, value, maxAge, secured, false);
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		// TODO Auto-generated method stub
		cookie("", name, value, maxAge, secured, httpOnly);
	}

	@Override
	public void cookie(String path, String name, String value) {
		// TODO Auto-generated method stub
		cookie(path, name, value, -1, false, false);
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge) {
		// TODO Auto-generated method stub
		cookie(path, name, value, maxAge, false, false);
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured) {
		// TODO Auto-generated method stub
		cookie(path, name, value, maxAge, secured, false);
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(secured);
		cookie.httpOnly(httpOnly);
		addCookie(cookie);
		// TODO Auto-generated method stub
	}
	
	public void addCookie(Cookie cookie) {
		cookies.put(cookie.name, cookie);
	}

	@Override
	public void removeCookie(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Cookie res = cookies.remove(name);
		if (res == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void removeCookie(String path, String name) {
		// TODO Auto-generated method stub
		Cookie c = cookies.remove(name);
		if (c == null) {
			throw new IllegalArgumentException();
		} else if (c.path != path) {
			addCookie(c);
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}
	
	public void setHeaders(String h) {
		this.headers = h;
	}

}
