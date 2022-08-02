package edu.upenn.cis.cis455.m1.server;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class ThreadPool {
	final Logger logger = LogManager.getLogger(WebService.class);

	public List<HttpWorker> workers;
	public HttpTaskQueue queue;
	public String ip;
	public HttpListener listener;
	public boolean running = true;
	public WebService webService;
	
	public ThreadPool(int size, String ip, WebService ws) {
		this.workers = new ArrayList<HttpWorker>(size);
		queue = new HttpTaskQueue();
		this.webService = ws;
		
		this.ip = ip;
		for (int i = 0; i < size; i++ ) {
			this.workers.add(i, new HttpWorker(this));
		}
		
		for (HttpWorker w : this.workers) {
			new Thread(w).start();
		}
		logger.info("Task queue and all " + workers.size() + " Http Workers are started!");
		
	}
	
	public void handle(Socket socket) throws InterruptedException {
		while(running) {
			synchronized(queue) {
				HttpTask task = new HttpTask(socket);
				queue.addTask(task);
				queue.notify();
				logger.info("A new task is established and broadcasted to the queue!");
				return;
			}
		}
		
	}
	
	public void shutdown() {
		listener.shutdown();
		this.running = false;
	}
}
