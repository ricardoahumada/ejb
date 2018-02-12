package com.netmind.beans;

import javax.ejb.Remote;

@Remote
public interface Greeter {
	String greet(String user);
}
