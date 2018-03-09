package spoonapps.token.web.services.admin;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.system.properties.SystemPropertiesManager;
import spoonapps.web.servlet.ActionServlet;
import spoonapps.web.servlet.MainServletUtils;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/services/admin/set-system-properties",description="This sets the value of one system property: name, value",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class SetSystemProperties extends ActionServlet {

	private static final long serialVersionUID = "$Header$".hashCode();

	protected Map<String, String> properties=null;
	
	public SetSystemProperties(){	
	}
		
	@Override
	protected void doTaskService(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, ServletException {
		String name=MainServletUtils.getMandatoryStringParameter(request, "name");
		String value=MainServletUtils.getMandatoryStringParameter(request, "value");
		
		SystemPropertiesManager.MANAGER.put(name,value);
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return GlobalProperties.getLastModified();
	}
}
