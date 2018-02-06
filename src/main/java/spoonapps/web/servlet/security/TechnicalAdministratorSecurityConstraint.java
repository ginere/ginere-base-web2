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
public class TechnicalAdministratorSecurityConstraint extends AbstractSecurityConstraint{

	public static final String ID="TECHNICAL_ADMIN";

	public static final SecurityConstraintInterface CONSTRAINT=new TechnicalAdministratorSecurityConstraint();
	
	private TechnicalAdministratorSecurityConstraint(){
		super(ID,"Technical Admistrator","Technical Administrator or super User");
	}


	@Override
	public boolean check(String userId,
						 HttpServletRequest request,
						 HttpServletResponse response) throws IOException{
		if (userId == null){
			MainServletUtils.sendError(request, response, HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
			return false;
		} else {
			if (RoleManager.MANAGER.hasRole(ID,userId)){
				return true;
			} else {
				MainServletUtils.sendError(request, response, HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
				return false;
			}
		}
	}


	@Override
	public void check(RuntimeCheckResult ret) throws ApplicationException {
		ret.add("RoleManager", RoleManager.MANAGER);		
	}		
}
