package spoonapps.token.web.services.admin;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.token.listener.AbstractContextListener;
import spoonapps.util.exception.ApplicationException;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.info.ServletInfoContainer;

@WebServlet(value="/admin/servletinfo",description="This returns the list of the servlet information")
public class ServletInformation extends JsonResultServlet {

	private static final long serialVersionUID = "$Header$".hashCode();

	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response) {
		return ServletInfoContainer.SINGLETON.information();
	}

	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return -1;
	}
	
	
	Definir los roloes y usuarios 
	
	En el web.xml se pueden definir los roloes y leerlos del tomcat users
	
	En video utilizamos el Global properties para almacenar los roles ...
	
	Escojer ....
}
