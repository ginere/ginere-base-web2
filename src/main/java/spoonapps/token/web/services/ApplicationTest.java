package spoonapps.token.web.services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.token.listener.AbstractContextListener;
import spoonapps.util.exception.ApplicationException;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet("/admin/test")
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class ApplicationTest extends JsonResultServlet {

	private static final long serialVersionUID = "$Header$".hashCode();

	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response) {
		return AbstractContextListener.check();
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return AbstractContextListener.getStartTime();
	}
}
