package edu.upenn.cis.cis455.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Parser;

import com.google.common.hash.Hashing;

import edu.upenn.cis.cis455.storage.StorageInterface;

import java.io.UnsupportedEncodingException;

public class CrawlerTask implements Runnable {
	
	String urlString;
	ThreadPoolExecutor threadPool;
	StorageInterface db;
	
	public CrawlerTask(String url, ThreadPoolExecutor pool, StorageInterface db) {
		this.urlString = url;
		this.threadPool = pool;
		this.db = db;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// Store webapge contents as a string into the database instance
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String content = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
				String hash = Hashing.sha256().hashString(content, StandardCharsets.UTF_8).toString();
				if (! db.checkHash(hash)) {
					db.addHash(hash);
					db.addDocument(urlString, content);
				}
				
			// Extract the HTML urls from the connected webpage	
			Document doc = Jsoup.connect(urlString).get();
			
			//Get links from document object. 
			Elements links = doc.select("a[href]");
		 
			//Iterate links and create new tasks for the thread pool.
			for (Element link : links) {
				CrawlerTask newTask  = new CrawlerTask(link.attr("href"), threadPool, db);
				threadPool.execute(newTask);
			}
			
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
	

}
