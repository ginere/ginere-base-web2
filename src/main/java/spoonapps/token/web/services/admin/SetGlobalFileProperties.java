package spoonapps.token.web.services.admin;

import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.properties.impl.FilePropertiesEditor;
import spoonapps.util.properties.impl.PropertyDefinition;
import spoonapps.web.servlet.ActionServlet;
import spoonapps.web.servlet.MainServletUtils;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/admin/set-file-properties",description="This sets the value of one property: name, description, value",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class SetGlobalFileProperties extends ActionServlet {

	private static final long serialVersionUID = "$Header$".hashCode();

	protected Map<String, String> properties=null;
	
	public SetGlobalFileProperties(){	
	}
		
	@Override
	protected void doTaskService(HttpServletRequest request, HttpServletResponse response) throws ApplicationException, ServletException {
		String name=MainServletUtils.getMandatoryStringParameter(request, "name");
		String description=MainServletUtils.getStringParameter(request, "description",null);
		String value=MainServletUtils.getMandatoryStringParameter(request, "value");
		
		PropertyDefinition prop=new PropertyDefinition(name, description, value);
		
		FilePropertiesEditor.SINGLETON.setProperties(Collections.singleton(prop));
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return GlobalProperties.getLastModified();
	}
}
