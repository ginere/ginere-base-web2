package spoonapps.web.servlet.security;

import javax.servlet.http.HttpServletRequest;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckInterface;

/**
 * This is one security constrain to be implemented
 */
public interface SecurityConstraintInterface extends RuntimeCheckInterface{
	public String getId();
	public String getName();
	public String getDescription();	
	
	// This check if the user has the roles
	public boolean check(String userId,HttpServletRequest request) throws ApplicationException;	
	
}
