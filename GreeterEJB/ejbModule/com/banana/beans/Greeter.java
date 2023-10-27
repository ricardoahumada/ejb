package com.banana.beans;

import javax.ejb.Remote;

@Remote
public interface Greeter {
	String greet(String user);
}
