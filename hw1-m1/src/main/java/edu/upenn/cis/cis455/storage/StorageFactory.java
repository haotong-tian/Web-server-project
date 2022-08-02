package edu.upenn.cis.cis455.storage;

public class StorageFactory {
	
	public static StorageInterface instance;
	
    public static StorageInterface getDatabaseInstance(String directory) throws Exception {
        // TODO: factory object, instantiate your storage server
    	
    	instance = new CrawlerStorage(directory);
    	return instance;
    	
    }
}
