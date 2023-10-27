package com.banana.servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.beans.Greeter;
import com.banana.beans.GreeterBean;
import com.banana.utils.ejb.LookerUp;

@WebServlet("/consumegreets")
public class GreeterClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger("GreeterClientServlet");

	@EJB(mappedName = "ejb:/GreeterEJB//GreeterBean!com.banana.beans.Greeter")
	private Greeter ejb;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultS = "Not message";

		try {
			logger.info("****ejb:" + ejb);
//			resultS = ejb.greet("Dante");

			logger.info("****Programatic lookup...");
			// Client and EJBs are in different Containers
			// --- EJB Lookup in same WAR
			String ejbsServerAddress = "localhost";
			int ejbsServerPort = 8080;
			String earName = ""; // EJB exist in a WAR and not in an EAR.
			String moduleName = "GreeterEJB";
			String deploymentDistinctName = "";
			String beanName = "GreeterBean";
			String interfaceQualifiedName = Greeter.class.getName();

			// UserName and Password are required but are set apart using the file
			// "jboss-ejb-client.xml"
			LookerUp wildf9Lookerup = new LookerUp(ejbsServerAddress, ejbsServerPort);

			// We could instead the following method by giving the exact JNDI name :
			// wildf9Lookerup.findSessionBean("ejb:/ejb3-server-war//QuizBean!com.letsprog.learning.ejb3.server.api.ILocalQuiz?stateful");
			Greeter greeterProxy = (Greeter) wildf9Lookerup.findRemoteSessionBean(LookerUp.SessionBeanType.STATELESS, earName,
					moduleName, deploymentDistinctName, beanName, interfaceQualifiedName);
			
			logger.info("****greeterProxy:" + greeterProxy);
			resultS = greeterProxy.greet("Dante");

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getWriter().append(resultS).append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private Greeter lookupRemoteEJB() throws NamingException {
		final Properties jndiProperties = new Properties();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiProperties.put("org.jboss.ejb.client.scoped.context", "true");

		final Context context = new InitialContext(jndiProperties);

		final String appName = "";
		final String moduleName = "GreeterEJB";
		final String distinctName = "";
		final String beanName = GreeterBean.class.getSimpleName();

		final String viewClassName = Greeter.class.getName();
		logger.info("***Looking EJB via JNDI ");
		logger.info("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);

		Greeter grt = (Greeter) context.lookup(
				"ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);

		logger.info("***lookupRemoteEJB:" + grt);

		context.close();

		return grt;

	}
}
