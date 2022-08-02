package edu.upenn.cis.cis455.m1.server;
import java.util.*;

/**
 * Stub class for implementing the queue of HttpTasks
 */
public class HttpTaskQueue {
	
	ArrayList<HttpTask> queue;
	
	public HttpTaskQueue() {
		queue = new ArrayList<HttpTask>();
	}
	
	public void addTask(HttpTask task) {
		queue.add(task);
	}
	
	public HttpTask getTask() {
		HttpTask task = null;
		while (task == null) {
			try {
				task = queue.remove(0);
			} catch (Exception e) {}
		}
		return task;
	}
	
	public boolean isEmpty() {
		return queue.size() == 0;
	}
	
}

// Thread in thread pool will keep running