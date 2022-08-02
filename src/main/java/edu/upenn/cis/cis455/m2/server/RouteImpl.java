package edu.upenn.cis.cis455.m2.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;

public abstract class RouteImpl implements Route {
	final static Logger logger = LogManager.getLogger(WebService.class);
	String path;
	HttpMethod method;
	
	public RouteImpl(String p, HttpMethod m) {
		this.path = p;
		this.method = m;
	}

	@Override
	public abstract Object handle(Request request, Response response) throws Exception;
	
	public void setPath(String p) {
		this.path = p;
	}
	
	public static RouteImpl create(String p, Route r, HttpMethod m) {
		return new RouteImpl(p, m) {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return r.handle(request, response);
            }
        };
	}

}
