package edu.upenn.cis.cis455.crawler.handlers;
//
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.crawler.WebInterface;
import edu.upenn.cis.cis455.storage.StorageInterface;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class RegistrationHandler implements Route {
	
	final static Logger logger = LogManager.getLogger(WebInterface.class);
	public StorageInterface db;
	
	public RegistrationHandler(StorageInterface db) {
		this.db = db;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		
		String user = request.queryParams("username");
        String pass = request.queryParams("password");
        
        logger.info("A new registration is handled with Username: " + user + " and Password: " + pass + ".");

        System.err.println("Login request for " + user + " and " + pass);
        if (! db.getSessionForUser(user, pass)) {
        	logger.info("Username is open for registration!");
            Session session = request.session();
            session.attribute("user", user);
            session.attribute("password", pass);
            db.addUser(user, pass);
            logger.info("New username and password added to Database");
            response.redirect("/index.html");
        } else {
            logger.info("Username is already occupied!");
            response.redirect("/login-form.html");
        }

        return "";
	}

}
