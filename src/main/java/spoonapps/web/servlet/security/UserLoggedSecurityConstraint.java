package spoonapps.web.servlet.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.web.servlet.MainServletUtils;
import spoonapps.web.servlet.role.RoleManager;

/**
 * This is one security constrain to be implemented
 */
public class UserLoggedSecurityConstraint extends AbstractSecurityConstraint{

	public static final String ID="USER_LOGGED";

	public static final SecurityConstraintInterface CONSTRAINT=new UserLoggedSecurityConstraint();
	
	private UserLoggedSecurityConstraint(){
		super(ID,"Technical Admistrator","Technical Administrator or super User");
	}


	@Override
	public boolean check(String userId,
						 HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
		if (userId == null){
			return false;
		} else {
			return true;
		}
	}


	@Override
	public void check(RuntimeCheckResult ret) throws ApplicationException {
		ret.add("RoleManager", RoleManager.MANAGER);		
	}	
	
	@Override
	public void getDefaultReturn(String userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
		MainServletUtils.sendError(request, response, HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");		
	}
}
