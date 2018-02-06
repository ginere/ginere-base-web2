package spoonapps.token.web.services.admin;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.token.listener.AbstractContextListener;
import spoonapps.util.exception.ApplicationException;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/admin/config",description="This returns the values of the global properties",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class ConfigurationInformation extends JsonResultServlet implements GlobalProperties.PropertiesChangedListener{

	private static final long serialVersionUID = "$Header$".hashCode();

	protected Map<String, String> properties=null;
	protected long lastModified=AbstractContextListener.getStartTime();
	
	public ConfigurationInformation(){
		GlobalProperties.addListener(this, true);
	}
	
	@Override
	public void propertiesChanged(long lastModifiedTime, Map<String, String> newCache){
		properties=newCache;
		lastModified=System.currentTimeMillis();
	}
	
	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response) {
		return properties;
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return lastModified;
	}
}
