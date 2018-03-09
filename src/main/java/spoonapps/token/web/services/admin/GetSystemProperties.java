package spoonapps.token.web.services.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.system.properties.SystemPropertiesManager;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/services/admin/system-properties",description="This returns the values of the system properties",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class GetSystemProperties extends JsonResultServlet {

	private static final long serialVersionUID = "$Header$".hashCode();
	
	public GetSystemProperties(){	
	}
		
	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return SystemPropertiesManager.MANAGER.getLastModified();
	}

	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return SystemPropertiesManager.MANAGER.getProperties();
	}

}
