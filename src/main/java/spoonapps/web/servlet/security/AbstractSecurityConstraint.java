package spoonapps.web.servlet.security;

import javax.servlet.http.HttpServletRequest;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

/**
 * This is one security constrain to be implemented
 */
public abstract class AbstractSecurityConstraint implements SecurityConstraintInterface{

	protected final String id;
	protected final String name;
	protected final String description;
	
	protected AbstractSecurityConstraint(String id,
										 String name,
										 String description){
		this.id=id;
		this.name=name;
		this.description=description;		
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public abstract boolean check(String userId, HttpServletRequest request) throws ApplicationException;

	public abstract void check(RuntimeCheckResult ret) throws ApplicationException;

	@Override
	public RuntimeCheckResult check() {
		RuntimeCheckResult ret=new RuntimeCheckResult(getClass());

		try {
			check(ret);
		}catch (Exception e) {
			ret.addError("", e);
		}
		
		return ret;
	}

	@Override
	public String toString(){
		return id;
	}
}
