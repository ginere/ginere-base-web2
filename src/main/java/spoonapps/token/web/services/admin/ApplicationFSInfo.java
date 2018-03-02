package spoonapps.token.web.services.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spoonapps.util.exception.ApplicationException;
import spoonapps.web.servlet.JsonResultServlet;
import spoonapps.web.servlet.security.Security;
import spoonapps.web.servlet.security.TechnicalAdministratorSecurityConstraint;

@WebServlet(value="/services/admin/fs-info",loadOnStartup=1)
@Security(constraints=TechnicalAdministratorSecurityConstraint.ID)
public class ApplicationFSInfo extends JsonResultServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object getData(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException, ServletException {
		return getFSInformation();
	}

	public static class FileSystemInformation {

		final public File name;
		final public long totalSpace;
		final public long usableSpace;
		final public long freeSpace;

		FileSystemInformation(File file){
			this.name=file.getAbsoluteFile();
			this.totalSpace = file.getTotalSpace(); //total disk space in bytes.
	    	this.usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
	    	this.freeSpace = file.getFreeSpace(); //unallocated / free disk space in bytes.
		}
	}

	
	public static Collection<FileSystemInformation> getFSInformation() {

		File[] listRoots = File.listRoots();
		Collection<FileSystemInformation> ret=new ArrayList<FileSystemInformation>(listRoots.length);
			
		for (File file:listRoots){
			FileSystemInformation info=new FileSystemInformation(file);

			ret.add(info);
	    	
		}
		
		return ret;
	}
	
	public static FileSystemInformation getMainFSInformation() {

		File[] listRoots = File.listRoots();
		FileSystemInformation ret=null;
		for (File file:listRoots){
			FileSystemInformation info=new FileSystemInformation(file);

			if (ret == null || info.totalSpace>ret.totalSpace){
				ret=info;
			}
		}
		
		return ret;
	}

}
