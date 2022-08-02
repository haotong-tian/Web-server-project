//package edu.upenn.cis.cis455.crawler;
//
//
//public class CrawlerWorker implements Runnable {
//	
//	public ThreadPool threadPool;
//	public CrawlerTaskQueue queue;
//	public boolean running = true;
//	public String status;
//	
//	public CrawlerWorker(ThreadPool pool) {
//		this.threadPool = pool;
//		this.queue = pool.queue;
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while(running) { 
//    		try {
//    			readFromQueue();
//    			Thread.sleep(100);
//    		} catch (InterruptedException e) {
//    			//logger.info("Interrupted Exception");
//    		}
//    	}
//	}
//	
//	public boolean readFromQueue() throws InterruptedException {
//    	while(running) {
//    		synchronized (queue) {
//        		if (queue.isEmpty()) {
//        			//If the queue is empty, we push the current thread to waiting state. Way to avoid polling.
//        			status = "Waiting";
//        			if (running == false) {
//        				return false;
//        			}
//        			queue.wait(); 
//        		} else {
//        			status = "Running";
//        			CrawlerTask task = this.threadPool.queue.getTask();
//        			if (task != null) {
//        		  		CrawledFileHandler handler = new CrawledFileHandler(task, threadPool);
//        		   		try {
////        		    		int result = handler.handle();
////        		    		if (result == -1) {
////        		    			threadPool.shutdown();
////        		    			return false;
////        		    		}
//        		   			handler.handle();
//        		    		queue.notifyAll();
//        		    	} catch (Exception e) {}
//        		    }
//        		   	return true;	
//        		}
//       			
//        	}
//    	}
//    	return false;
//    }
//    
//    public void shutdown() {
//    	running = false;
//    }
//
//}
