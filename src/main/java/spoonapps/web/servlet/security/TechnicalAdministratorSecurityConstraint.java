package spoonapps.web.servlet.security;

import javax.servlet.http.HttpServletRequest;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
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
	public boolean check(String userId, HttpServletRequest request) {
		if (userId == null){
			return false;
		} else {
			return RoleManager.MANAGER.hasRole(ID,userId);
		}
	}


	@Override
	public void check(RuntimeCheckResult ret) throws ApplicationException {
		ret.add("RoleManager", RoleManager.MANAGER);		
	}		
}
