package spoonapps.token.web.services.admin;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.properties.impl.FilePropertiesEditor;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/admin/file-properties",description="This returns the values of the global properties file if any",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class GetGlobalFileProperties extends JsonResultServlet {

	private static final long serialVersionUID = "$Header$".hashCode();

	protected Map<String, String> properties=null;
	
	public GetGlobalFileProperties(){	
	}
		
	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return FilePropertiesEditor.SINGLETON.getProperties();
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return GlobalProperties.getLastModified();
	}
}
