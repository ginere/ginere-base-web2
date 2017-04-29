package spoonapps.web.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.apache.commons.lang3.StringEscapeUtils;

import spoonapps.util.enumeration.AppEnum;
import spoonapps.util.exception.ApplicationException;

/**
 * Mother class for the JSP 
 *
 */
public abstract class Jsp extends MainServlet implements HttpJspPage{
	private static final long serialVersionUID = 1L;
	
//	private static Logger log = LoggerFactory.getLogger(Jsp.class);

	protected long lastModified=-1;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.lastModified=System.currentTimeMillis();
	}
	
    protected long getLastModifiedException(HttpServletRequest req) throws ServletException , ApplicationException{
    	Enumeration<String> paramNames=req.getParameterNames();
    	
    	if (paramNames.hasMoreElements()){
    		return -1;
    	} else {    	
    		return lastModified;
    	}
    }


//    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
//	    @Override
//	    protected SimpleDateFormat initialValue() {
//	        return new SimpleDateFormat("dd/MM/yyyy hh:mm");
//	    }
//    };


	/**
	 * Entry point into service.
	 */
	public abstract void _jspService(HttpServletRequest request,
									 HttpServletResponse response) throws ServletException, IOException;

	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,ApplicationException{
		_jspService(request, response);
	}


	protected static String html(Object section){
		if (section == null){
			return "";
		} else {
			return StringEscapeUtils.escapeHtml4(section.toString());
		}
	}

	protected static String html(AppEnum obj){
		if (obj == null){
			return "";
		} else {
			return StringEscapeUtils.escapeHtml4(obj.getName());
		}
	}
	protected static String html(int i){
		return Integer.toString(i);
	}

	protected static String html(double d){
		return Double.toString(d);
	}
	
	protected static String html(String section){
		if (section == null){
			return "";
		} else {
			return StringEscapeUtils.escapeHtml4(section);
		}
	}

//	protected static String date(Date date){
//		if (date==null){
//			return "";
//		} else {
//			SimpleDateFormat sdf=dateFormat.get();
//	
//			return html(sdf.format(date));
//		}
//	}


}
