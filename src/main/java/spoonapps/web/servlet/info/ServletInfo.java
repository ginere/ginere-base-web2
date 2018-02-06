package spoonapps.web.servlet.info;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.StringUtils;

import spoonapps.util.stats.TimeInformation;
import spoonapps.web.servlet.MainServlet;

public class ServletInfo extends TimeInformation{
    	
	private static final long serialVersionUID = 1L;

	public final String name;    	    	
	private final String description;
	private final String url;

	private long runningCall=0;
	

	public ServletInfo(MainServlet servlet){
		Class<? extends MainServlet> clazz=servlet.getClass();
		
		// @WebServlet("/admin/version")

		WebServlet ws=(WebServlet)clazz.getAnnotation(WebServlet.class);
		if (ws != null){
			String description="";
			description+="Name: "+ws.name();
			description+="Displayname: "+ws.displayName();
			description+="Description: "+ws.description();
			description+="AsyncSupported: "+ws.asyncSupported();

			this.description=description;
			this.url=StringUtils.join(ws.value());
			
			this.name=ws.displayName();
			
		} else {
//			this.url=servlet.getUri();
//			this.description=servlet.getDescription();
			this.url="";
			this.description=clazz.getName();
			this.name=clazz.getSimpleName();
		}	

//		try {
//			RightInterface[] rights=servlet.getRights();
//	
//			if (rights == null){
//				this.rightDescription="Public access";
//			} else if (rights.length == 0){
//				this.rightDescription="User logged";
//			} else {
//				this.rightDescription=null;
//				for (RightInterface right:rights){
//					if (this.rightDescription == null){
//						this.rightDescription=right.getName();
//					} else {
//						this.rightDescription+=","+right.getName();
//					}
//				}
//			}
//		}catch(ContextInitializedException e){
//			log.error("",e);
//		}
		
		ServletInfoContainer.SINGLETON.subscrive(servlet, this);
	}

	public synchronized void startCall() {
		this.runningCall++;	
	}

	public synchronized void endCall(long time) {
		this.runningCall--;	
		super.add(time);
	}
	
	public void clean(){
		super.clean();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public long getRunningCall() {
		return runningCall;
	}
}
