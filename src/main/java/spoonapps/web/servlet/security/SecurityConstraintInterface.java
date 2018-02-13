package spoonapps.web.servlet.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckInterface;

/**
 * This is one security constrain to be implemented
 */
public interface SecurityConstraintInterface extends RuntimeCheckInterface{
	public String getId();
	public String getName();
	public String getDescription();	
	
	/**
	 * This check if the user has the roles
	 * The response is passed to set the return code if needed
	 */	
	public boolean check(String userId,
						 HttpServletRequest request,
						 HttpServletResponse response) throws IOException,ApplicationException;	
	
	public void getDefaultReturn(String userId,HttpServletRequest request,
			 					 HttpServletResponse response)throws IOException,ApplicationException;
	
}
