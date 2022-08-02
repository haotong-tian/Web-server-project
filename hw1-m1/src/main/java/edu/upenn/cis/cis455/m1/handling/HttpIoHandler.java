package edu.upenn.cis.cis455.m1.handling;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileTypeDetector;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.server.HttpRequest;
import edu.upenn.cis.cis455.m2.server.HttpResponse;
import edu.upenn.cis.cis455.m1.server.HttpTask;
import edu.upenn.cis.cis455.m1.server.HttpWorker;
import edu.upenn.cis.cis455.m1.server.ThreadPool;
import edu.upenn.cis.cis455.m2.server.WebService;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m2.server.RouteImpl;
import edu.upenn.cis.cis455.m1.handling.HttpParsing;

/**
 * Handles marshaling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);
    
    Socket socket;
    String ip;
    HttpRequest request;
    ThreadPool threadPool;
    WebService webService;
    
    /**
     * Constructor: construct new handler on the given request socket   
     */
    public HttpIoHandler(HttpTask task, String ip, ThreadPool p, WebService ws) {
    	this.request = new HttpRequest();
    	this.socket = task.getSocket();
    	this.ip = ip;
    	this.threadPool = p;
    	this.webService = ws;
    }
    
    /**
     * Handle the request
     * @throws Exception 
     */
    public int handle() throws Exception {
    	parseRequest();
    	HttpResponse response = new HttpResponse();
    	if (request.uri().equals("/shutdown")) {
    		// -1 means shut down the listener
    		socket.close();
    		return -1;
    	} else if (request.uri().equals("/control")) {
    		sendControl(socket, threadPool);
    		logger.info("Control Info sent.");
    	} else if (requestToResponse(request, response)) {
    		sendResponse(socket, request, response);
    		logger.info("File Response sent!");
    	} else {
    		sendException(socket, this.request, new HaltException(404, "File Does Not Exist"));
    		logger.info("Exception sent.");
    	}
    	return 0;
    	
    }

    /**
     * Sends an exception back, in the form of an HTTP response code and message.
     * Returns true if we are supposed to keep the connection open (for persistent
     * connections).
     * @throws IOException 
     */
    public static boolean sendException(Socket socket, Request request, HaltException except) throws IOException {
        OutputStream stream = socket.getOutputStream();
        stream.write(("HTTP/1.1 " + except.statusCode() + " " + except.body() + "\r\n").getBytes());
        stream.write("ContentType: text/html\r\n".getBytes());
        stream.write("\r\n".getBytes());
        stream.write((except.statusCode() + " " + except.body() + "\r\n \r\n").getBytes());
        stream.flush();
        socket.close();
    	
    	return true;
    }
    
    // need request?

    /**
     * Sends data back. Returns true if we are supposed to keep the connection open
     * (for persistent connections).
     * @throws IOException 
     */
    public static boolean sendResponse(Socket socket, Request request, Response response) throws IOException {
    	if (request.requestMethod().equals("GET")) {
    		OutputStream stream = socket.getOutputStream();
        	stream.write(("HTTP/1.1 200 OK\r\n").getBytes());
            stream.write(response.getHeaders().getBytes());
            stream.write("\r\n".getBytes());
            stream.write(response.returnBodyRaw());
            stream.write("\r\n\r\n".getBytes());
            stream.flush();
            socket.close();
    	} else if (request.requestMethod().equals("HEAD")) {
    		OutputStream stream = socket.getOutputStream();
        	stream.write(("HTTP/1.1 200 OK\r\n").getBytes());
            stream.write(response.getHeaders().getBytes());
            stream.write("\r\n\r\n".getBytes());
            stream.flush();
            socket.close();
    	}
    	
    	return true;
    }
    
    
    /**
     * Parse the socket info
     * @throws IOException 
     */
    public void parseRequest() throws IOException {
    	InputStream inputStream = socket.getInputStream();
    	Map<String, String> headers = new TreeMap<String, String>();
    	Map<String, List<String>> params = new TreeMap<String, List<String>>();
    	String uri = HttpParsing.parseRequest(this.ip, inputStream, headers, params);
    	
//    	logger.info("The parsed uri is: " + uri);
//    	for (String key : headers.keySet()) {
//    		System.out.println("Headers: " + key + " matched to " + headers.get(key));
//    	}
//    	for (String key : params.keySet()) {
//    		System.out.println("Params: " + key + " matched to " + headers.get(key));
//    	}
    	
//    	this.request = new HttpRequest(this.ip, headers, params, uri);
    }
    
    
    // Parse request from HttpParsing.java
    
    /**
     * Look for the content file and headers from parsed request
     * Fill the contents of response
     * @throws Exception 
     * @throws IOException 
     */
    public boolean requestToResponse(HttpRequest request, HttpResponse response) throws Exception {
    	try {
    		logger.info("The file directory is: " + request.pathInfo());
        	
    		RouteImpl route = matchRoute(request.uri());
    		String content = route.handle(request, response).toString();
    		byte[] fileContents = content.getBytes();
    		
    		Path path = Paths.get(request.pathInfo());
        	//byte[] fileContents = Files.readAllBytes(path);
        	
//        	Scanner myReader = new Scanner(file);
//            while (myReader.hasNextLine()) {
//              String data = myReader.nextLine();
//              fileContent += data + "\r\n";
//            }
//        	myReader.close();
        	response.bodyRaw(fileContents);
        	response.setPath(path);
        	try {
        		String type = Files.probeContentType(path);
        		response.type(type);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
        	logger.info("the file is not found..........");
        	return false;
        }
//    	System.out.print(fileContent);
//    	response.setBody(fileContent);
    	
    	String headers = "";
    	headers += request.protocol() + " 200 OK\r\n";
    	headers += "Date: " + getServerTime() + "\r\n";
    	headers += "Content-Type: " + response.type() + "\r\n";
    	headers += "Content-Length: " + response.bodyLength() + "\r\n";
    	headers += "Connection: close\r\n";
    	response.setHeaders(headers);

    	return true;
    }
   

	public String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
    
    public static boolean sendControl(Socket socket, ThreadPool threadPool) throws IOException {
    	OutputStream stream = socket.getOutputStream();
    	stream.write(("HTTP/1.1 200 OK\r\n").getBytes());
        stream.write("ContentType: text/html\r\n".getBytes());
        stream.write("\r\n".getBytes());
        int index = 0;
        for (HttpWorker w : threadPool.workers) {
        	index ++;
        	stream.write(("Thread " + index + " is " + w.status + "\r\n").getBytes());
        }
        stream.write("\r\n\r\n".getBytes());
        stream.flush();
        socket.close();
    	
    	return true;
    }
    
    public RouteImpl matchRoute(String pathInfo) {
    	Map<String, RouteImpl> map = webService.routeMap;
    	RouteImpl route = map.get(pathInfo);
    	return route;
    }
    
    
        
}
