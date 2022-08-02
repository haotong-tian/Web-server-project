package edu.upenn.cis.cis455.m1.server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.server.HttpTaskQueue;

/**
 * Stub class for a thread worker that handles Web requests
 */
public class HttpWorker implements Runnable { // extend thread?
	final static Logger logger = LogManager.getLogger(WebService.class);

	public String status;
	
//	HttpTaskQueue queue;
	ThreadPool threadPool;
	HttpTaskQueue queue;
	public boolean running = true;
	
	public HttpWorker(ThreadPool p) {
		this.threadPool = p;
		queue = threadPool.queue;
	}
	
//	public void start() {
//		run();
//	}
		
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	while(running) { 
    		try {
    			readFromQueue();
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			logger.info("Interrupted Exception");
    		}
    	}
    }
    	
    public boolean readFromQueue() throws InterruptedException {
    	while(running) {
    		synchronized (queue) {
        		if (queue.isEmpty()) {
        			//If the queue is empty, we push the current thread to waiting state. Way to avoid polling.
        			status = "Waiting";
        			if (running == false) {
        				return false;
        			}
        			queue.wait(); 
        			logger.info("Worker waiting.");
        		} else {
        			status = "Running";
        			HttpTask task = this.threadPool.queue.getTask();
        			if (task != null) {
        		  		HttpIoHandler handler = new HttpIoHandler(task, threadPool.ip, threadPool, null);
        		   		try {
        		    		int result = handler.handle();
        		    		if (result == -1) {
        		    			threadPool.shutdown();
        		    			return false;
        		    		}
        		    		queue.notifyAll();
        		    	} catch (Exception e) {}
        		    }
        		   	logger.info("Worker handled a task from queue!");
        		   	return true;	
        		}
       			
        	}
    	}
    	return false;
    }
    
    public void shutdown() {
    	running = false;
    }
    	
}
