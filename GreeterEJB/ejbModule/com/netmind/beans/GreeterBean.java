package com.netmind.beans;

import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class GreeterBean
 */
@Stateless
@Remote (Greeter.class)
public class GreeterBean implements Greeter {
	
	final static public Logger loggger=Logger.getLogger("GreeterBean");

	public String greet(String user) {
		loggger.info("Called!!! GreeterBean with:"+user);
        return "Hello " + user + ", have a pleasant day!";
    }
}
