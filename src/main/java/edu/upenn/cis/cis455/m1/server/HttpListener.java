package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;

/**
 * Stub for your HTTP server, which listens on a ServerSocket and handles
 * requests
 */
public class HttpListener implements Runnable {
	final static Logger logger = LogManager.getLogger(WebService.class);
	
	public int port;
	public ThreadPool threadPool;
	public boolean running = true;
	
	public HttpListener(int p, ThreadPool pool) {
		port = p;
		threadPool =  pool;
		threadPool.listener = this;
	}

    @Override
    public void run() {
        // TODO Auto-generated method stub
    	try (ServerSocket serverSocket = new ServerSocket(this.port)) {
    		while(running) {
    			try {
    				Socket socket = serverSocket.accept();
    				logger.info("A new socket is heard and sent to the thread Pool!");
       				threadPool.handle(socket);
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
   					e.printStackTrace();
				}
   			}
   		} catch (IOException e) {}
    	
    		
    }
    
    public void shutdown() {
    	for (HttpWorker w : threadPool.workers) {
    		w.shutdown();
    	}
    	this.running = false;
    	logger.info("The Server and Wokers are Shutdown!");
    }
    
}
