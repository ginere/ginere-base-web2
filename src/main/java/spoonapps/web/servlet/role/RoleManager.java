package spoonapps.web.servlet.role;

import java.util.concurrent.ConcurrentHashMap;

import spoonapps.util.module.AbstractModule;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.web.servlet.security.SecurityConstraintInterface;

/**
 * This is one security constrain to be implemented
 */
public class RoleManager extends AbstractModule{

	public static final RoleManager MANAGER=new RoleManager();

	private final ConcurrentHashMap<String,SecurityConstraintInterface> cache=new ConcurrentHashMap<String,SecurityConstraintInterface>();
			
	private RoleManager(){
	}
	
	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {
		for (SecurityConstraintInterface constraint:cache.values()){
			ret.add(constraint.getId(),constraint);
		}
		return ret;
	}

	@Override
	protected String getSvnHeaderString() {
		return "$Header:$";
	}
	
	public boolean hasRole(String roleId,String userId) {
		String propertyName=roleId+"-"+userId;
		return GlobalProperties.getBooleanValue(getClass(), propertyName, false);
	}

}
