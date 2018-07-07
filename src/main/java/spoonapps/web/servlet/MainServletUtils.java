package spoonapps.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import spoonapps.util.enumeration.AppEnum;


/**
 * Static method of the MainServlet
 */
public class MainServletUtils {
	
	static Logger log = MainServlet.log;


	private static final String[] EMPTY_STRING_ARRAY = new String[0];


	public static String getUserId(HttpServletRequest request,String defaultValue) {
		String userId=request.getRemoteUser();
		if (userId==null){
			return defaultValue;
		} else {
			return userId;
		}
	}

	public static boolean isPost(HttpServletRequest request) {
		return "POST".equals(request.getMethod());
	}
	
	public static String getMethod(HttpServletRequest request, String defaultValue) {
		String method = request.getMethod();
		if (StringUtils.isEmpty(method)) {
			return defaultValue;
		} else {
			return method;
		}
	}

	public static Long getMandatoryLongParameter(HttpServletRequest request,
										  String parameterName) throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);

		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			throw new ServletException("The parameter:'" + parameterName + "' with value :'" + value
									   + "' can not be transforned into a long ", e);
		}
	}

	public static boolean getMandatoryBooleanParameter(HttpServletRequest request,
												String parameterName) throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);

		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			throw new ServletException("The parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be transformed into a boolean. ", e);
		}
	}

	public static int getMandatoryIntParameter(HttpServletRequest request,
										String parameterName) throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);

		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			throw new ServletException("The parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be transformed into an int ", e);
		}
	}

	public static String getMandatoryStringParameter(HttpServletRequest request,
													 String parameterName) throws ServletException {
		String value = request.getParameter(parameterName);
		if (value == null) {
			throw new ServletException("The mandatory parameter:'" + parameterName + "'  does not exist.");
		} else if ("".equals(value.trim())) {
			throw new ServletException("The mandatory parameter:'" + parameterName + "' does not exist.");
		} else {
			return value.trim();
		}
	}

	public static String getStringParameter(HttpServletRequest request,
											String parameterName, String defaultValue) {
		String value = request.getParameter(parameterName);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			return value.trim();
		}
	}

	public static String getStringParameter(HttpServletRequest request,
									 String parameterName,
									 String notDefinedValue,
									 String emptyDefaultValue) {
		String value = request.getParameter(parameterName);
		if (value == null) {
			return notDefinedValue;
		} else if ("".equals(value.trim())) {
			return emptyDefaultValue;
		} else {
			return value.trim();
		}
	}

	public static String[] getStringParameterArray(HttpServletRequest request,
											String parameterName,
											String separator) {
		String values[] = request.getParameterValues(parameterName);
		String value = getStringParameter(request, parameterName, null);

		// For get requests
		if (values != null && values.length > 0) {
			return values;
		} else {
			if (value == null || "".equals(value)) {
				return EMPTY_STRING_ARRAY;
			} else {
				return StringUtils.split(value, separator);
			}
		}
	}

	public static String[] getStringParameterArray(HttpServletRequest request,
											String parameterName) {
		
		return getStringParameterArray(request, parameterName, ",");
	}

	public static double[] getDoubleParameterArray(HttpServletRequest request,
											String parameterName,
											String separator,
											double defaultValue) {
		
		String array[] = getStringParameterArray(request, parameterName, separator);
		double ret[] = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			String value = array[i];

			if (value == null || "".equals(value.trim())) {
				ret[i] = defaultValue;
			} else {
				try {
					ret[i] = new Double(value);
				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug("The parameter:'" + parameterName + "' with value :'" + value
								+ "' can not be transformed into a Double ", e);
					}
					ret[i] = defaultValue;
				}
			}
		}

		return ret;
	}

	public static double[] getDoubleParameterArray(HttpServletRequest request, String parameterName, double defaultValue) {
		return getDoubleParameterArray(request, parameterName, ",", defaultValue);
	}

	public static long[] getLongParameterArray(HttpServletRequest request, String parameterName, String separator,
			long defaultValue) {
		String array[] = getStringParameterArray(request, parameterName, separator);
		long ret[] = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			String value = array[i];

			if (value == null || "".equals(value.trim())) {
				ret[i] = defaultValue;
			} else {
				try {
					ret[i] = Long.parseLong(value);
				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug("the parameter:'" + parameterName + "' with value :'" + value
								+ "' can not be transformed into a Long ", e);
					}
					ret[i] = defaultValue;
				}
			}
		}

		return ret;
	}

	public static long[] getLongParameterArray(HttpServletRequest request, String parameterName, long defaultValue) {
		return getLongParameterArray(request, parameterName, ",", defaultValue);
	}

	public static Long getLongParameter(HttpServletRequest request, String parameterName, Long defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				// return new Long(value);
				return Long.parseLong(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into a long ", e);
				}
				return defaultValue;
			}
		}
	}

	public static int[] getIntParameterArray(HttpServletRequest request, String parameterName, String separator,
			int defaultValue) {
		String array[] = getStringParameterArray(request, parameterName, separator);
		int ret[] = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			String value = array[i];

			if (value == null || "".equals(value.trim())) {
				ret[i] = defaultValue;
			} else {
				try {
					ret[i] = Integer.parseInt(value);
				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug("The parameter:'" + parameterName + "' with value :'" + value
								+ "' can not be transformed into one int ", e);
					}
					ret[i] = defaultValue;
				}
			}
		}

		return ret;
	}

	public static int[] getIntParameterArray(HttpServletRequest request, String parameterName, int defaultValue) {
		return getIntParameterArray(request, parameterName, ",", defaultValue);
	}

	public static int getIntParameter(HttpServletRequest request, String parameterName, int defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one intero ", e);
				}
				return defaultValue;
			}
		}
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String parameterName, boolean defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return Boolean.parseBoolean(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one boolean ", e);
				}
				return defaultValue;
			}
		}
	}

	public static Float getFloatParameter(HttpServletRequest request, String parameterName, Float defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return new Float(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one Float ", e);
				}
				return defaultValue;
			}
		}
	}

	public static Double getDoubleParameter(HttpServletRequest request, String parameterName, Double defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return new Double(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one Double ", e);
				}
				return defaultValue;
			}
		}
	}

	public static Double getMandatoryDoubleParameter(HttpServletRequest request, String parameterName)
			throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);

		try {
			return new Double(value);
		} catch (Exception e) {
			throw new ServletException("The mandatory parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be transformed into one Double ", e);
		}
	}

	public static Date getDateParameter(HttpServletRequest request, String parameterName, SimpleDateFormat sdf,
			Date defaultValue) throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return sdf.parse(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one Date ", e);
				}

				return defaultValue;
			}
		}
	}

	public static Date getMandatoryDateParameter(HttpServletRequest request, String parameterName, SimpleDateFormat sdf)
			throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);
		try {
			return sdf.parse(value);
		} catch (Exception e) {
			throw new ServletException("The parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be formated as a date. Check the date format ", e);
		}
	}

	public static Integer getIntegerParameter(HttpServletRequest request, String parameterName, Integer defaultValue)
			throws ServletException {
		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into an Integer ", e);
				}
				return defaultValue;
			}
		}
	}

	/**
	 * Forward towards an  uri relative to the application context /uri -> http://localhost:8080/CONTEXT/uri
	 * 
	 * @param request
	 * @param response
	 * @param uri
	 *            The uri relativa al contexto de la aplicacion
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void forward(HttpServletRequest request, HttpServletResponse response, String uri)
			throws ServletException, IOException {
		request.getRequestDispatcher(uri).forward(request, response);
	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		response.sendRedirect(url);
	}

	public static void redirectToContextUri(HttpServletRequest request, HttpServletResponse response,
			String uriRelativeToContext) throws IOException {
		response.sendRedirect(request.getContextPath() + uriRelativeToContext);
	}

	public static void sendError(HttpServletRequest request, HttpServletResponse response, int code, String message)
			throws IOException {

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		response.sendError(code, message);
	}

	public static String getSessionStringValue(HttpServletRequest request, String parameterName, String defaultValue) {
		HttpSession session = request.getSession();
		Object value = session.getAttribute(parameterName);

		if (value == null) {
			return defaultValue;
		} else if (value instanceof String) {
			return (String) value;
		} else {
			return defaultValue;
		}
	}

	public static void setSessionStringValue(HttpServletRequest request, String parameterName, String value) {
		setSessionValue(request, parameterName, value);
	}

	public static void setSessionValue(HttpServletRequest request, String parameterName, Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(parameterName, value);
	}

	/**
	 * return the reuqest uri
	 * 
	 * @param request
	 * @return
	 */
	public static String getURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * This write the content of one input stream into a servlet
	 * 
	 * @param request
	 * @param response
	 * @param input
	 * @param contentLength
	 * @param fileName
	 * @param contentType
	 * @throws IOException
	 */
	public static void printStream(HttpServletRequest request, HttpServletResponse response, InputStream input,
			int contentLength, String fileName, String contentType) throws IOException {
		response.reset();
		response.setContentLength(contentLength);
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "inline; filename=" + fileName + ";");

		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(input, output);

		output.flush();
		input.close();
		output.close();
	}


	public static void printStream(HttpServletRequest request, HttpServletResponse response, byte[] bytes,
			String fileName, String contentType) throws IOException {

		if (bytes == null) {
			response.sendError(MainServlet.HTTP_SERVICE_UNAVAILABLE, "No bytes passed");
		} else {
			response.reset();
			response.setContentLength(bytes.length);
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "inline; filename=" + fileName + ";");

			ServletOutputStream output = response.getOutputStream();
			IOUtils.write(bytes, output);

			output.flush();
			output.close();
		}
	}

	public static String getReferer(HttpServletRequest request, String defaultValue) {
		String ret = request.getHeader("referer");

		if (StringUtils.isEmpty(ret)) {
			return defaultValue;
		} else {
			return ret;
		}
	}

	public static String getUserAgent(HttpServletRequest request, String defaultValue) {
		String ret = request.getHeader("user-agent");

		if (StringUtils.isEmpty(ret)) {
			return defaultValue;
		} else {
			return ret;
		}
	}

	public static String getRemoteAddress(HttpServletRequest request, String defaultValue) {
		
		String val=request.getHeader("x-real-ip");
		
		if (StringUtils.isNotBlank(val)){
			return val;
		}
		
		val=request.getHeader("x-forwarded-for");
		
		if (StringUtils.isNotBlank(val)){
			return val;
		}
				
		val=request.getRemoteAddr();

		if (StringUtils.isEmpty(val)) {
			return defaultValue;
		} else {
			return val;
		}
	}

	public static boolean containsParameter(HttpServletRequest request,
											String parameterName) {
		String value = getStringParameter(request, parameterName, null);

		return (value != null);
	}

	public static boolean containsParameter(HttpServletRequest request,
											Map<String, String> params,
											String parameterName) {
		if (!params.containsKey(parameterName)) {
			return containsParameter(request, parameterName);
		} else {
			return true;
		}
	}

	public static String getStringParameter(HttpServletRequest request,
											Map<String, String> params,
											String parameterName,
											String defaultValue) {

		if (!params.containsKey(parameterName)) {
			return getStringParameter(request, parameterName, defaultValue);
		} else {
			String value = params.get(parameterName);

			if (value == null || "".equals(value.trim())) {
				return defaultValue;
			} else {
				return value.trim();
			}
		}
	}

	public static String getMandatoryStringParameter(HttpServletRequest request,
													 Map<String, String> params,
													 String parameterName) throws ServletException {

		if (!params.containsKey(parameterName)) {
			return getMandatoryStringParameter(request, parameterName);
		} else {
			String value = params.get(parameterName);

			if (value == null || "".equals(value.trim())) {
				throw new ServletException("The mandatory parameter:'" + parameterName + "'  does not exist.");
			} else {
				return value.trim();
			}
		}
	}

	public static boolean getBooleanParameter(HttpServletRequest request,
											  Map<String, String> params,
											  String parameterName,
											  boolean defaultValue) {
		String value = getStringParameter(request, params, parameterName, null);

		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return Boolean.parseBoolean(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one boolean ", e);
				}
				return defaultValue;
			}
		}
	}

	public static double getDoubleParameter(HttpServletRequest request,
											Map<String, String> params,
											String parameterName,
											double defaultValue) {
		String value = getStringParameter(request, params, parameterName, null);

		if (value == null) {
			return defaultValue;
		} else if ("".equals(value.trim())) {
			return defaultValue;
		} else {
			try {
				return new Double(value);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one Double ", e);
				}
				return defaultValue;
			}
		}
	}

	public static double getMandatoryDoubleParameter(HttpServletRequest request,
													 Map<String, String> params,
													 String parameterName) throws ServletException {
		String value = getStringParameter(request, params, parameterName, null);

		try {
			return new Double(value);
		} catch (Exception e) {
			throw new ServletException("The mandatory parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be transformed into one Double ", e);
		}
	}

	public static AppEnum getMandatoryAppEnumParameter(HttpServletRequest request,
												String parameterName,
												Class<? extends AppEnum> enumType) throws ServletException {
		String value = getMandatoryStringParameter(request, parameterName);
		try {
			AppEnum ret = AppEnum.value(enumType, value,null);

			if (ret == null) {
				throw new ServletException("The mandatory parameter:'" + parameterName + "'  does not exist.");
			} else {
				return ret;
			}
		} catch (Exception e) {
			throw new ServletException("The parameter:'" + parameterName + "' with value :'" + value
					+ "' can not be transformed into an SQLEnum ", e);
		}
	}

	public static AppEnum getEnumAppParameter(HttpServletRequest request,
									   String parameterName,
									   Class<? extends AppEnum> enumType,
									   AppEnum defaultValue) throws ServletException {

		String value = getStringParameter(request, parameterName, null);
		if (value == null) {
			return defaultValue;
		} else {
			try {
				AppEnum ret = AppEnum.value(enumType, value,defaultValue);
				return ret;
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("The parameter:'" + parameterName + "' with value :'" + value
							+ "' can not be transformed into one enumerado ", e);
				}
				return defaultValue;
			}
		}
	}

	public static String getRequestShortInfo(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if (session == null){
//			return "NO_SES:"+getRemoteAddress(request, "NO_REM_ADD")+":"+getUserAgent(request, "NO_USER_AGENT");
//		} else {
//			String value = getSessionStringValue(request, "ID", null);
//			
//			if (value == null){
//				value=session.getId()+":"+getRemoteAddress(request, "NO_REM_ADD")+":"+StringUtils.abbreviate(getUserAgent(request, "NO_USER_AGENT"),15);
//				setSessionStringValue(request, "ID", value);
//			} 
//			
//			return value;			
//		}
		
		return getRequestSessionInfo(request);
	}
	
	public static String getRequestSessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null){
			return "NO_SES:"+getRemoteAddress(request, "NO_REM_ADD")+":"+getUserAgent(request, "NO_USER_AGENT");
		} else {
			String value = getSessionStringValue(request, "ID", null);
			
			if (value == null){
				value=session.getId()+":"+getRemoteAddress(request, "NO_REM_ADD")+":"+StringUtils.abbreviate(getUserAgent(request, "NO_USER_AGENT"),15);
				setSessionStringValue(request, "ID", value);
			} 
			
			return value;			
		}
	}

	public static Map <String,String[]> getHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		
	    if (headerNames != null) {
	    	Map <String,String[]> ret=new HashMap<String,String[]>();
			while (headerNames.hasMoreElements()) {
				String header=headerNames.nextElement();
				Enumeration<String> headerValues = request.getHeaders(header);
				String[] array = (String[])EnumerationUtils.toList(headerValues).toArray();
				
				ret.put(header,array);
			}
			
			return ret;
	    } else {
			return Collections.emptyMap();
		}
	}	
	
//	public static String getInformation(HttpServletRequest request) {
//		
//		Map<String, String[]> parameterMap = request.getParameterMap();
//		Map<String, String[]> headers = getHeaders(request);
//		
//		request.getAuthType();
//		request.getCharacterEncoding();
//		request.getContentLength();
//		request.getContextPath();
//		request.getCookies();
//		
//		request.getLocalAddr();
//		request.getLocalName();
//		request.getLocalPort();
//		request.getMethod();
//		
//	}
}
