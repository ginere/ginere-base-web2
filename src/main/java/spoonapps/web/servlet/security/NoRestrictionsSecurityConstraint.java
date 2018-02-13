package spoonapps.web.servlet.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

/**
 * This is one security constrain to be implemented
 */
public class NoRestrictionsSecurityConstraint extends AbstractSecurityConstraint{

	public static final String ID="NO_RESTRICTIONS";

	public static final SecurityConstraintInterface CONSTRAINT=new NoRestrictionsSecurityConstraint();
	
	private NoRestrictionsSecurityConstraint(){
		super(ID,"No restrictions","This service will have no restriction, handle with care");
	}


	@Override
	public boolean check(String userId,
						 HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
			return true;
	}


	@Override
	public void check(RuntimeCheckResult ret) throws ApplicationException {
	}	
	
	@Override
	public void getDefaultReturn(String userId,HttpServletRequest request, HttpServletResponse response) throws IOException {		
	
	}
}
