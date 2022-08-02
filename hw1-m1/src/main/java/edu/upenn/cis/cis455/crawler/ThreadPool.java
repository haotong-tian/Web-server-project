//package edu.upenn.cis.cis455.crawler;
//import java.util.*;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.net.Socket;
//
//public class ThreadPool {
//
//	public List<CrawlerWorker> workers;
//	public CrawlerTaskQueue queue;
//	public boolean running = true;
//	public Crawler crawler;
//	
//	public ThreadPool(int size, Crawler c) {
//		this.workers = new ArrayList<CrawlerWorker>(size);
//		this.crawler = c;
//		queue = new CrawlerTaskQueue();
//		
//		for (int i = 0; i < size; i++ ) {
//			this.workers.add(i, new CrawlerWorker(this));
//		}
//		
//		for (CrawlerWorker w : this.workers) {
//			new Thread(w).start();
//		}
//		
//	}
//	
//	public void handle(Socket socket) throws InterruptedException {
//		while(running) {
//			synchronized(queue) {
//				CrawlerTask task = new CrawlerTask("");
//				queue.addTask(task);
//				queue.notify();
//				return;
//			}
//		}
//		
//	}
//	
//	public void shutdown() {
//		this.running = false;
//	}
//}
