package spoonapps.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.exception.ApplicationException;
import spoonapps.util.notification.Notify;
import spoonapps.web.servlet.info.ServletInfo;
import spoonapps.web.servlet.security.SecurityConstraintInterface;

/**
 * Main classs for http services with utils to handle request, languages, users,
 * cache, etc ...
 *
 */
@SuppressWarnings("serial")
public abstract class MainServlet extends HttpServlet {

	public static Logger log = LoggerFactory.getLogger(MainServlet.class);

	/**
	 * Status code (503) indicating that the HTTP server is temporarily
	 * overloaded, and unable to handle the request.
	 */
	public static final int HTTP_SERVICE_UNAVAILABLE = HttpServletResponse.SC_SERVICE_UNAVAILABLE;

	/**
	 * Status code (403) indicating the server understood the request but
	 * refused to fulfill it.
	 */
	public static final int HTTP_CODE_FORBIDDEN = HttpServletResponse.SC_FORBIDDEN;

	/**
	 * This is the main child entry point
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	abstract protected void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ApplicationException;

	public ServletConfig getServletConfig() {
		return super.getServletConfig();
	}

	protected static final String METHOD_GET = "GET";
	protected static final String METHOD_OPTIONS = "OPTIONS";
	protected static final String METHOD_DELETE = "DELETE";
	protected static final String METHOD_HEAD = "HEAD";
	protected static final String METHOD_POST = "POST";
	protected static final String METHOD_PUT = "PUT";
	protected static final String METHOD_TRACE = "TRACE";

	protected static final String HEADER_LASTMOD = "Last-Modified";
	protected static final String HEADER_IFMODSINCE = "If-Modified-Since";

	// This stores the servlet information
	protected final ServletInfo servletInfo=new ServletInfo(this);

	
	/**
	 *
	 * Returns the time the <code>HttpServletRequest</code> object was last
	 * modified, in milliseconds since midnight January 1, 1970 GMT. If the time
	 * is unknown, this method returns a negative number (the default).
	 *
	 * <p>
	 * Servlets that support HTTP GET requests and can quickly determine their
	 * last modification time should override this method. This makes browser
	 * and proxy caches work more effectively, reducing the load on server and
	 * network resources.
	 *
	 * @param req
	 *            the <code>HttpServletRequest</code> object that is sent to the
	 *            servlet
	 *
	 * @return a <code>long</code> integer specifying the time the
	 *         <code>HttpServletRequest</code> object was last modified, in
	 *         milliseconds since midnight, January 1, 1970 GMT, or -1 if the
	 *         time is not known
	 */
	protected long getLastModifiedException(HttpServletRequest req) throws ServletException, ApplicationException {
		return -1;
	}

	/*
	 * Sets the Last-Modified entity header field, if it has not already been
	 * set and if the value is meaningful. Called before doGet, to ensure that
	 * headers are set before response data is written. A subclass might have
	 * set this header already, so we check.
	 */
	private void maybeSetLastModified(HttpServletResponse resp) {
		if (resp.containsHeader(HEADER_LASTMOD)) {
			return;
		}
		long lastModified = System.currentTimeMillis();
		
		if (log.isDebugEnabled()){
			SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss SSS");
			log.debug("Setting last Modified:'"+sdf.format(new Date(lastModified))+"' last Modified:"+lastModified);
		}
		resp.setDateHeader(HEADER_LASTMOD, lastModified);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void service(HttpServletRequest request,
						HttpServletResponse response) throws ServletException, IOException {

		long time = System.currentTimeMillis();
		servletInfo.startCall();
		

		String uri = MainServletUtils.getURI(request);
		String method = MainServletUtils.getMethod(request, null);

		try {
			if (checkSecurityConstraints(request,response)){
				// TRY THE LAST UPDATE FOR THE GET METHOD
				if (method.equals(METHOD_GET)) {
					long lastModified = getLastModifiedException(request);
					if (lastModified >= 0) {
	
						long ifModifiedSince = request.getDateHeader(HEADER_IFMODSINCE);
	
						if (ifModifiedSince <= lastModified) {
							// If the servlet mod time is later, call doGet()
							// Round down to the nearest second for a proper compare
							// A ifModifiedSince of -1 will always be less
							maybeSetLastModified(response);
							// Se sigue con la query normal
						} else {
							response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
							return;
						}
					}
				} else if (method.equals(METHOD_HEAD)) {
	//				long lastModified = getLastModifiedException(request);
					maybeSetLastModified(response);
				}
				if (method.equals(METHOD_OPTIONS)) {
					// The OPtion just to spetail functions
					doOptions(request, response);
				} else {
					if (this instanceof Jsp) {
						Jsp jsp = (Jsp) this;
						
						jsp._jspService(request, response);
					} else {
						doService(request, response);
					}
				}
			} else {
				// The security constraint will handle the retour code
				if (log.isInfoEnabled()){				
					log.warn("Security Check FAILS for Servlet:"+uri+" in "+(System.currentTimeMillis()-time)+" ms");
				}
				return ;
			}
		} catch (Throwable e) {
			Notify.error("Servlet Exception" + uri, e);
			throw new ServletException("Servlet Exception" + uri, e);
		} finally {
			if (log.isInfoEnabled()){				
				log.info(String.format("Servlet:%s in %s ms", uri,(System.currentTimeMillis()-time)));
			}
			
			servletInfo.endCall(System.currentTimeMillis()-time);
		}
	}

	public boolean checkSecurityConstraints(HttpServletRequest request,HttpServletResponse response) throws IOException,ApplicationException {
		String userId=MainServletUtils.getUserId(request,null);
		SecurityConstraintInterface securityConstraint = servletInfo.checkSecurityConstraints(userId,request,response);
		
		if (securityConstraint == null){
			return true;
		} else {
			getReturnCode(userId,request, response,securityConstraint);
			return false;
		}
	
	}

	public String getRequestShortInfo(HttpServletRequest request){
		return MainServletUtils.getRequestShortInfo(request);
	}

	protected void getReturnCode(String userId, HttpServletRequest request, HttpServletResponse response,
			SecurityConstraintInterface securityConstraint) throws IOException, ApplicationException {
		securityConstraint.getDefaultReturn(userId,request, response);		
	}

	public String[] constraints() {
		return null;
	}

}
