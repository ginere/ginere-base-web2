package spoonapps.web.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import spoonapps.util.exception.ApplicationException;

@SuppressWarnings("serial")
public abstract class JSONServlet extends MainServlet {
	protected ObjectMapper mapper;

	public static final String CHARSET = "UTF-8";
	public static final String CONTENT_TYPE_JSON = "application/json"; // application/json  // text/javascript
	public static final String CONTENT_TYPE_JAVASCRIPT = "text/javascript"; // application/json  // text/javascript

	/**
	 * Iniciación del servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mapper=getMapper(config);
	}
	


	protected  abstract ObjectMapper getMapper(ServletConfig config);

	/**
	 * Para configurar los adaptadores que sean necesarios
	 * @param gsonBuilder
	 */
	//	protected abstract void configureGsonBuilder(GsonBuilder gsonBuilder);
	

	protected void doService(HttpServletRequest request,
							 HttpServletResponse response) throws ServletException, IOException,ApplicationException {
		
		Object obj = doTaskJSONService(request, response);

        writeObject(request,response,obj);
        
//		response.setCharacterEncoding(getCharset());
//		response.setContentType(getContentType());
//
//		//		ServletOutputStream output = response.getOutputStream();
//		PrintWriter writer = response.getWriter();
//		if (obj!=null){
//			// If no object to write this writes nothing ...
//			mapper.writeValue(writer, obj);
//		}
//
//		writer.flush();
//		writer.close();
	}

	
	protected void writeObject(HttpServletRequest request,
                               HttpServletResponse response,
                               Object obj) throws IOException {
		response.setCharacterEncoding(getCharset());
		response.setContentType(getContentType());

		// ServletOutputStream output = response.getOutputStream();
		PrintWriter writer = response.getWriter();
		if (obj != null) {
			// If no object to write this writes nothing ...
			mapper.writeValue(writer, obj);
		}

		writer.flush();
		writer.close();
	}
    
	private String getContentType() {
		return CONTENT_TYPE_JSON;
	}

	private String getCharset() {
		return CHARSET;
	}



	abstract protected Object doTaskJSONService(HttpServletRequest request,
												HttpServletResponse response) throws ServletException, IOException,ApplicationException;

}
