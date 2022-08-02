package edu.upenn.cis.cis455.crawler;

import java.util.ArrayList;

public class CrawlerTaskQueue {
	
	ArrayList<CrawlerTask> queue;
	
	public CrawlerTaskQueue() {
		queue = new ArrayList<CrawlerTask>();
	}
	
	public void addTask(CrawlerTask task) {
		queue.add(task);
	}
	
	public CrawlerTask getTask() {
		CrawlerTask task = null;
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
