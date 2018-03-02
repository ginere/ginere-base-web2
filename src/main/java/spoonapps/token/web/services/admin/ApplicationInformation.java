package spoonapps.token.web.services.admin;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import spoonapps.token.listener.AbstractContextListener;
import spoonapps.util.properties.GlobalProperties;
import spoonapps.web.servlet.JsonResultServlet;

@SuppressWarnings("serial")
public abstract class ApplicationInformation extends JsonResultServlet implements GlobalProperties.PropertiesChangedListener {

	private Map<String, Object> properties=new ConcurrentHashMap<String, Object>();
	
	@Override
	public void propertiesChanged(long lastModifiedTime, Map<String, String> newCache){
		properties.put("PropertiesChanged", new Date(lastModifiedTime));
	}
	
	public ApplicationInformation(){
		GlobalProperties.addListener(this, true);
		properties.put("ApplicationStarted", new Date(AbstractContextListener.getStartTime()));
	}
	
	protected Map<String, Object> getInformation() {

		/* Total number of processors or cores available to the JVM */
		properties.put("AvailableProcessors",Runtime.getRuntime().availableProcessors());
		
		/* Total amount of free memory available to the JVM */
		properties.put("FreeMemory",Runtime.getRuntime().freeMemory());
		
		/* This will return Long.MAX_VALUE if there is no preset limit */
		long maxMemory = Runtime.getRuntime().maxMemory();
		properties.put("MaxMemory",(maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
		
		/* Total memory currently in use by the JVM */
		properties.put("TotalMemory",Runtime.getRuntime().totalMemory() );
	    
		/* The CPU INfo */
		ThreadMXBean t = ManagementFactory.getThreadMXBean();
		
		properties.put("getDaemonThreadCount",t.getDaemonThreadCount());
		properties.put("getPeakThreadCount",t.getPeakThreadCount());
		properties.put("getThreadCount",t.getThreadCount());
		properties.put("getCurrentThreadCpuTime",t.getCurrentThreadCpuTime());
		
		return properties;
	}		
}
