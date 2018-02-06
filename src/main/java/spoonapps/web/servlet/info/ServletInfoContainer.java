package spoonapps.web.servlet.info;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckResult;
import spoonapps.web.servlet.MainServlet;

public class ServletInfoContainer extends AbstractModule{

	public static final ServletInfoContainer SINGLETON=new ServletInfoContainer();
	
	private final ConcurrentHashMap<String,ServletInfo> cache=new ConcurrentHashMap<String,ServletInfo>();
	
	private ServletInfoContainer(){		
	}
	
	public void subscrive(MainServlet servlet,ServletInfo info){
		cache.put(servlet.getClass().getName(),info);
	}

	public void clean(){

		cache.entrySet().forEach(e -> {
			String key = e.getKey();
			ServletInfo value = e.getValue();
			
			if (log.isInfoEnabled()){
				log.info("Cleaning:"+key);
				
				value.clean();				
			}
		});
				
	}

	public Collection<ServletInfo> information(){
		return cache.values();		
	}
	
	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {
		return ret;
	}

	@Override
	protected String getSvnHeaderString() {
		return "$Header:$";
	}
    	
}
