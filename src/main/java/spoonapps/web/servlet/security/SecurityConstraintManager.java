package spoonapps.web.servlet.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.web.servlet.info.ServletInfo;

/**
 * This is one security constrain to be implemented
 */
public class SecurityConstraintManager extends AbstractModule{

	public static final SecurityConstraintManager MANAGER=new SecurityConstraintManager();

	private final ConcurrentHashMap<String,SecurityConstraintInterface> cache=new ConcurrentHashMap<String,SecurityConstraintInterface>();
			
	private SecurityConstraintManager(){
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
	
	public void subscrive(SecurityConstraintInterface constraint){
		cache.put(constraint.getId(),constraint);
	}
	
	public SecurityConstraintInterface get(String id,
										   SecurityConstraintInterface defaultValue){
		
		SecurityConstraintInterface ret=cache.get(id);

		if (ret==null){
			return defaultValue;
		} else {
			return ret;
		}		
	}

	public Set<SecurityConstraintInterface> get(Set<String> list){
		
		Set<SecurityConstraintInterface> ret=list.stream()
				.map( id -> get(id,null))
				.filter(elt -> elt != null)
				.collect(Collectors.toSet());
		
		return ret;
	}

	public Set<SecurityConstraintInterface> get(String[] constraints) {
		if (constraints == null){
			return Collections.emptySet();
		} else {
			Set<SecurityConstraintInterface> ret=new HashSet<SecurityConstraintInterface>(constraints.length);
			for (String id:constraints){
				SecurityConstraintInterface constraint=get(id,null);
				
				if (constraint!=null){
					ret.add(constraint);
				} else {
					log.error("Security Constraint not fount:"+id);
				}
			}
			
			return ret;
		}
	}

	public SecurityConstraintInterface checkSecurityConstraints(ServletInfo info,
										 String userId,
										 HttpServletRequest request,
										 HttpServletResponse response,
										 Set<SecurityConstraintInterface> securityConstraints) throws IOException, ApplicationException {
		
		for (SecurityConstraintInterface constraint:securityConstraints){
			try {
				if (!constraint.check(userId, request,response)){
					log.warn("Servlet:"+info.name+", Security constaint FAILS:"+constraint+", user:"+userId);
					return constraint;
				}
			}catch (ApplicationException e) {
				throw new ApplicationException("While checking constraint:"+constraint+" for user:"+userId,e);
			}
		}
		return null;
		
	}

}
