package edu.upenn.cis.cis455.m2.server;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Session;

public class HttpRequest extends Request {
	
	public HttpSession session = null;
	public boolean validSession = false;
	String remoteIp;
	Map<String, String> headers;
	Map<String, List<String>> params;
	String uri;
	

	@Override
	public Session session() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public Session session(boolean create) {
		// If original ID exists, then no need to pass session
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> params() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryParams(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> queryParamsValues(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> queryParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attribute(String attrib, Object val) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object attribute(String attrib) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> attributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> cookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestMethod() {
		// TODO Auto-generated method stub
		return headers.get("method");
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
