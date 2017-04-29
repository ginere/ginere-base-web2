package spoonapps.token.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.notification.Notify;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.web.json.ObjectMapperProvider;


public abstract class AbstractContextListener implements ServletContextListener{

	private static Logger log = LoggerFactory.getLogger(AbstractContextListener.class);

	private static final String SVN_HEADER_STRING = "$Header$";

	private static String version;

	private static AbstractContextListener CONTEXT = null;

	protected static long startTime;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		String appName = sce.getServletContext().getContextPath();
		Notify.info("Destroying the application:" + appName+ " Version:"+getVersion());

		CONTEXT = null;
	}



	abstract protected void initializeContext(ServletContextEvent sce);

	
	/**
	 * Context initialization entry point	
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {		
		CONTEXT = this;
		startTime=System.currentTimeMillis();
		String appName = sce.getServletContext().getContextPath();
		log.warn("--------------------------------------------------------------------------------------");			
		
		log.warn("Starting application:"+appName+" Version:"+getVersion());			
		log.warn("--------------------------------------------------------------------------------------");			

		try {

            initializeContext(sce);

			log.info("Runtime tests started ...");
			RuntimeCheckResult result = check();
	
			if (result.isOK()) {
				log.info("Runtime tests finished OK");
				log.warn("--------------------------------------------------------------------------------------");	
				log.warn("Context initialization finished:" + appName+" VERSION:"+getVersion());		
				log.warn("--------------------------------------------------------------------------------------");			
				Notify.info("Context initialization finished:" + appName+" VERSION:"+getVersion());
			} else {
				log.error("Runtime tests FAILS:" + result.toString());
				log.error("--------------------------------------------------------------------------------------");	
				log.error("Context initialization finished on ERROR:" + appName+" VERSION:"+getVersion());		
				log.error("--------------------------------------------------------------------------------------");			
				Notify.warn("Context initialization finished on ERROR:" + appName+" VERSION:"+getVersion());		

			}
		}catch (Throwable e) {
			log.error("INITIALIZATION FAILS",e);
			log.error("--------------------------------------------------------------------------------------");	
			log.error("Context initialization finished on ERROR:" + appName+" VERSION:"+getVersion(),e);		
			log.error("--------------------------------------------------------------------------------------");			
			Notify.error("Context initialization finished on ERROR:" + appName+" VERSION:"+getVersion(),e);		
		}

		
	}

	/**
	 * Gets the application version from the SVN position.
	 * 
	 * @return the version
	 */
	public static String getVersion() {
		if (version == null) {
			if (SVN_HEADER_STRING.contains("trunk")) {
				version = "t-1.0.0";
			} else if (SVN_HEADER_STRING.contains("tags")) {
				int index = SVN_HEADER_STRING.lastIndexOf("tags");
				String tmp = SVN_HEADER_STRING.substring(index + "tags".length(), SVN_HEADER_STRING.length());
				String array[] = StringUtils.split(tmp, '/');
				if (array.length > 0) {
					version = array[0];
				} else {
					version = "unkownw-tag";
				}
			} else {
				version = "unkownw";
			}
		}

		return version;
	}

	public static RuntimeCheckResult check() {
		RuntimeCheckResult ret = new RuntimeCheckResult(AbstractContextListener.class,getVersion());
		if (CONTEXT == null) {
			ret.addError("The application is not initialized.");
		} else {
			ret.add(CONTEXT.innerRuntimeCheck());
		}
		return ret;
	}

	protected abstract RuntimeCheckResult innerRuntimeCheck();
	

	public static long getStartTime() {
		return startTime;
	}

}
