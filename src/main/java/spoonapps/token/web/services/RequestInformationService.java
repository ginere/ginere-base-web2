package spoonapps.token.web.services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.MainServletUtils;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/services/request-information",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class RequestInformationService extends JsonResultServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response)
		throws ApplicationException, ServletException {
		return MainServletUtils.getRequestShortInfo(request);
	}

}
