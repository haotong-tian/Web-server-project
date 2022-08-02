package edu.upenn.cis.cis455.storage;

import java.io.Serializable;

public class LoginInfo implements Serializable {
	
	String username;
	String passwordHash;
	
	public LoginInfo(String u, String p) {
		this.username = u;
		this.passwordHash = p;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.passwordHash;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public void setPassword(String p) {
		this.passwordHash = p;
	}
	
}
