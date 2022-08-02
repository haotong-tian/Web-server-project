package edu.upenn.cis.cis455.storage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import edu.upenn.cis.cis455.crawler.WebInterface;
import edu.upenn.cis.cis455.storage.StorageInterface;

import com.google.common.hash.Hashing;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import java.io.File;

public class CrawlerStorage implements StorageInterface {

	final static Logger logger = LogManager.getLogger(WebInterface.class);
	
	public Environment crawlerDbEnv;
	public Database crawlerDatabase;
	private EntryBinding dataBindingOfWebpage;
	private EntryBinding dataBindingOfLogin;
//	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	public Set<String> hashes = new TreeSet<String>();

	public CrawlerStorage(String directory) throws Exception {
		// Open the environment. Allow it to be created if it does not
    	// already exist.
    	try {
    		// Environment configuration
    	    EnvironmentConfig envConfig = new EnvironmentConfig();
    	    envConfig.setAllowCreate(true);
    	    crawlerDbEnv = new Environment(new File("/Users/haotongtian/Desktop/Spring 2022 CIS 455/555-hw2"), envConfig);

    	    // Database configuration
    	    DatabaseConfig dbConfig = new DatabaseConfig();
    	    dbConfig.setAllowCreate(true);
    	    dbConfig.setSortedDuplicates(false);
    	    crawlerDatabase = crawlerDbEnv.openDatabase(null, "crawlerStorage", dbConfig);
    	    
    	    // Instantiate the class catalog
    	    StoredClassCatalog classCatalog = new StoredClassCatalog(crawlerDatabase);
    	    
    	    // Create the binding and DatabaseEntry for the webpage data
    	    this.dataBindingOfWebpage = new SerialBinding(classCatalog, WebpageInfo.class);
    	    
    	    // Create the binding and DatabaseEntry for the login data
    	    this.dataBindingOfLogin = new SerialBinding(classCatalog, LoginInfo.class);
    	    
    	} catch (DatabaseException dbe) {
    	    // Exception handling goes here.
    	}
		
	}

	public CrawlerStorage getInstance() {
		return this;
	}

	@Override
	public int getCorpusSize() {
		// TODO Auto-generated method stub
		return (int) crawlerDatabase.count();
	}

	@Override
	public int addDocument(String url, String documentContents) {
		// TODO Auto-generated method stub
		
		DatabaseEntry theData = new DatabaseEntry();
		DatabaseEntry theKey = null;
		try {
			theKey = new DatabaseEntry(url.getBytes("UTF-8"));
		} catch (Exception exp) {
			return -1;
		}
		
		WebpageInfo webpage = new WebpageInfo(url, documentContents);
		dataBindingOfWebpage.objectToEntry(webpage, theData);
		crawlerDatabase.put(null, theKey, theData);
		
		return 0;
	}

	@Override
	public String getDocument(String url) {
		
		DatabaseEntry theData = new DatabaseEntry();
		DatabaseEntry theKey = null;
		try {
			theKey = new DatabaseEntry(url.getBytes("UTF-8"));
		} catch (Exception exp) {
			return null;
		}
		
		crawlerDatabase.get(null, theKey, theData, LockMode.DEFAULT);
		WebpageInfo retrievedData = (WebpageInfo) dataBindingOfWebpage.entryToObject(theData);
	    
		return retrievedData.getRawContent();
	}

	@Override
	public int addUser(String username, String password) {
		// TODO Auto-generated method stub
		
		DatabaseEntry theData = new DatabaseEntry();
		DatabaseEntry theKey = null;
		String passwordHash = null;
		try {
			theKey = new DatabaseEntry(username.getBytes("UTF-8"));
			passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
		} catch (Exception exp) {
			return -1;
		}
		
		LoginInfo login = new LoginInfo(username, passwordHash);
		dataBindingOfLogin.objectToEntry(login, theData);
		crawlerDatabase.put(null, theKey, theData);
		
		return 0;
	}

	@Override
	public boolean getSessionForUser(String username, String password) {
		// TODO Auto-generated method stub
		logger.info("A new request at getSessionForUser.");
		DatabaseEntry theData = new DatabaseEntry();
		DatabaseEntry theKey = null;
		String passwordHash = null;
		try {
			theKey = new DatabaseEntry(username.getBytes("UTF-8"));
			passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
		} catch (Exception exp) {
			logger.info("Illegal Key or Password information!");
			return false;
		}
		
		OperationStatus status = crawlerDatabase.get(null, theKey, theData, LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			LoginInfo retrievedData = (LoginInfo) dataBindingOfLogin.entryToObject(theData);
			boolean passwordMatch = passwordHash.equals(retrievedData.getPassword());
			logger.info("The password match status is: " + passwordMatch);
			return passwordMatch;
		} else {
			logger.info("No info related to the key is found in database.");
			return false;
		}
		
	    
	}
	
	@Override
	public boolean checkHash(String hash) {
		return hashes.contains(hash);
	}
	
	@Override
	public void addHash(String hash) {
		hashes.add(hash);
	}
	

	@Override
	public void close() {
		// TODO Auto-generated method stub
		crawlerDbEnv.close();

	}

}
