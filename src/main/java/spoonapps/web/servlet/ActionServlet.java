package spoonapps.web.servlet;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;

/**
 * This that just do something and return nothing ...
 */
@SuppressWarnings("serial")
public abstract class ActionServlet extends MainServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
		

	protected void doService(HttpServletRequest request,
							 HttpServletResponse response) throws ServletException, IOException,ApplicationException {
		
		doTaskService(request, response);
	}
	
	abstract protected void doTaskService(HttpServletRequest request,
										  HttpServletResponse response) throws ServletException, IOException,ApplicationException;
	
}
