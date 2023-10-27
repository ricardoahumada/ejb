package com;
 
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.banana.beans.Greeter;
import com.banana.beans.GreeterBean;
 
 
public class RemoteEJBClient {
 
    public static void main(String[] args) throws Exception {
        testRemoteEJB();
 
    }
 
    private static void testRemoteEJB() throws NamingException {
 
        final Greeter ejb = lookupRemoteEJB();
        String s = ejb.greet("Ricardo");
        System.out.println(s);
    }
 
    private static Greeter lookupRemoteEJB() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
 
        final Context context = new InitialContext(jndiProperties);
 
 
        final String appName = "";
        final String moduleName = "GreeterEJB";
        final String distinctName = "";
        final String beanName = GreeterBean.class.getSimpleName();
 
        final String viewClassName = Greeter.class.getName();
        System.out.println("Looking EJB via JNDI ");
        System.out.println("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
 
        return (Greeter) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
 
 
    }
 
}