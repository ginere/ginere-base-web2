package spoonapps.token.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.config.FileConfig;
import spoonapps.util.exception.ApplicationException;
import spoonapps.util.notification.Notify;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.util.version.VersionUtils;


public abstract class AbstractContextListener implements ServletContextListener{

	private static Logger log = LoggerFactory.getLogger(AbstractContextListener.class);

	private static final String SVN_HEADER_STRING = "$Header$";
	private static final String SVN_DATE_STRING = "$Date$";

	private static String version;
	private static String versionDate;

	private static AbstractContextListener CONTEXT = null;

	protected static long startTime;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		String appName = sce.getServletContext().getContextPath();
		Notify.info("Destroying the application:" + appName+ " Version:"+getVersion());

		CONTEXT = null;
	}

	abstract protected void initializeContext(ServletContextEvent sce,String appName) throws ApplicationException;

	
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
			// this is conmarly CATALINA_HOME/conf
			FileConfig.SINGLETON.addPath("./conf");
			
            initializeContext(sce,appName);

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
			version=VersionUtils.getVersion(getSvnHeaderString(),"trunk");
		}

		return version;
	}

	protected static String getSvnHeaderString() {
		return SVN_HEADER_STRING;
	}

	protected static String getSvnDateString() {
		return SVN_DATE_STRING;
	}

	public static String getVersionDate() {
		if (versionDate == null) {
			versionDate=VersionUtils.getDate(getSvnDateString());
		}

		return versionDate;
	}
	public static RuntimeCheckResult check() {
		RuntimeCheckResult ret = new RuntimeCheckResult(AbstractContextListener.class,getVersion());
		if (CONTEXT == null) {
			ret.addError("The application is not initialized.");
		} else {			
			ret.add(Notify.check());
			ret.add(GlobalProperties.check());						
//			ret.add(CONTEXT.innerRuntimeCheck(ret));
			CONTEXT.innerRuntimeCheck(ret);
		}
		return ret;
	}

	protected abstract RuntimeCheckResult innerRuntimeCheck(RuntimeCheckResult result);
	

	public static long getStartTime() {
		return startTime;
	}

}
