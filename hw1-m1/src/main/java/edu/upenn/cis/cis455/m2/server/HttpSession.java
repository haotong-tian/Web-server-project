package edu.upenn.cis.cis455.m2.server;

import java.time.Instant;
import java.util.*;

import edu.upenn.cis.cis455.m2.interfaces.Session;

public class HttpSession extends Session {
	
	long creation;
	long lastAccessed;
	String id;
	boolean valid;
	Map<String, Object> attributes;
	int maxInterval;
	
	
	public void HttpSession() {
		creation = Instant.now().toEpochMilli();
		lastAccessed = creation;
		maxInterval = 0;
		id = UUID.randomUUID().toString();
		attributes = new TreeMap<String, Object>();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long creationTime() {
		// TODO Auto-generated method stub
		return creation;
	}

	@Override
	public long lastAccessedTime() {
		// TODO Auto-generated method stub
		return lastAccessed;
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		valid = false;
	}

	@Override
	public int maxInactiveInterval() {
		// TODO Auto-generated method stub
		return maxInterval;
	}

	@Override
	public void maxInactiveInterval(int interval) {
		// TODO Auto-generated method stub
		maxInterval = interval;
	}

	@Override
	public void access() {
		lastAccessed = Instant.now().toEpochMilli();
		// TODO Auto-generated method stub

	}

	@Override
	public void attribute(String name, Object value) {
		// TODO Auto-generated method stub
		attributes.put(name, value);
	}

	@Override
	public Object attribute(String name) {
		// TODO Auto-generated method stub
		return attributes.get(name);
	}

	@Override
	public Set<String> attributes() {
		// TODO Auto-generated method stub
		return attributes.keySet();
	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		attributes.remove(name);
	}
	
	
	
	
	
}
