package spoonapps.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import spoonapps.util.notification.Notify;
import spoonapps.web.json.ObjectMapperProvider;

public abstract class JsonResultServlet extends JSONServlet{

	protected static final long serialVersionUID = 1L;
	
	
	@Override
	protected ObjectMapper getMapper(ServletConfig config){

		return ObjectMapperProvider.createDefaultMapper();
	}

	@Override
	protected Object doTaskJSONService(HttpServletRequest request,
                                       HttpServletResponse response) {
		
		try {
			Object data=getData(request,response);
			
			return Result.success(data);
		} catch (Throwable e) {
			Notify.error(getURI(request), e);
			return Result.exception(e);
		}
	}

	 
	protected abstract Object getData(HttpServletRequest request,
			HttpServletResponse response) throws Throwable;

}
