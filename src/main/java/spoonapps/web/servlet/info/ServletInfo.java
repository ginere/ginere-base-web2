package spoonapps.web.servlet.info;

import java.io.IOException;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.stats.TimeInformation;
import spoonapps.web.servlet.MainServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.SecurityConstraintInterface;
import spoonapps.web.servlet.security.SecurityConstraintManager;

public class ServletInfo extends TimeInformation{
    	
	private static final long serialVersionUID = 1L;

	public final String name;    	    	
	private final String description;
	private final String url;

	private long runningCall=0;
	private long constraintFails=0;
	

	private final Set<SecurityConstraintInterface> securityConstraints;


	public ServletInfo(MainServlet servlet){
		Class<? extends MainServlet> clazz=servlet.getClass();
		
		// @WebServlet("/admin/version")

		WebServlet ws=(WebServlet)clazz.getAnnotation(WebServlet.class);
		if (ws != null){
			
			String description=ws.description();
			
			if (StringUtils.isBlank(description)){
				description="";
				description+="Name: "+ws.name();
				description+="Displayname: "+ws.displayName();
				description+="Description: "+ws.description();
				description+="AsyncSupported: "+ws.asyncSupported();
			}
			
			this.description=description;
			this.url=StringUtils.join(ws.value());
			
			this.name=(StringUtils.isBlank(ws.displayName()))?this.url:ws.displayName();
			
			
		} else {
//			this.url=servlet.getUri();
//			this.description=servlet.getDescription();
			this.url="";
			this.description=clazz.getName();
			this.name=clazz.getSimpleName();
		}	

		Security sec=(Security)clazz.getAnnotation(Security.class);
		if (sec != null){
			this.securityConstraints=SecurityConstraintManager.MANAGER.get(sec.constraints());
		} else {
			this.securityConstraints=SecurityConstraintManager.MANAGER.get(servlet.constraints());		
		}
		
		ServletInfoContainer.SINGLETON.subscrive(servlet, this);
	}

	public synchronized void startCall() {
		this.runningCall++;	
	}

	public synchronized void endCall(long time) {
		this.runningCall--;	
		super.add(time);
	}
	
	public synchronized void clean(){
		super.clean();
		constraintFails=0;
	}

	private synchronized void addConstrintFails(SecurityConstraintInterface checkSecurityConstraints) {
		constraintFails++;		
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public long getRunningCall() {
		return runningCall;
	}

	public long getConstraintFails() {
		return constraintFails;
	}
	
	public Set<SecurityConstraintInterface> getSecurityConstraints() {
		return securityConstraints;
	}

	public SecurityConstraintInterface checkSecurityConstraints(String userId, 
										    HttpServletRequest request,
										    HttpServletResponse response) throws IOException,ApplicationException {
		
		SecurityConstraintInterface checkSecurityConstraints = SecurityConstraintManager.MANAGER.checkSecurityConstraints(this,userId,request,response,securityConstraints);
		if (checkSecurityConstraints == null){
			return null;
		} else {
			addConstrintFails(checkSecurityConstraints);
			return checkSecurityConstraints;
		}
		
	}

}
