package spoonapps.web.json;

import java.util.Date;

import org.codehaus.jackson.Version;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.DateSerializer;

import spoonapps.token.listener.AbstractContextListener;
import spoonapps.util.enumeration.AppEnum;

/**
 * https://jersey.java.net/documentation/latest/media.html
 *
 */
public class ObjectMapperProvider {

	private static  ObjectMapper mapper;

//	public ObjectMapperProvider() {
//		mapper = createDefaultMapper();
//	}
//
//	public ObjectMapper getContext() {
//		return mapper;
//	}

	public static ObjectMapper createDefaultMapper() {
		if (mapper==null){
			final ObjectMapper result = new ObjectMapper();
	
	//		// Create the module
			SimpleModule mod = new SimpleModule("Trilogue", new Version(0, 0, 0, AbstractContextListener.getVersion()));
	
			// Add the custom serializer to the module
			mod.addSerializer(AppEnum.class,new AppEnumSerializer());
			mod.addSerializer(Date.class,new DateSerializer());
	
			result.enable(SerializationConfig.Feature.INDENT_OUTPUT);
			
			result.registerModule(mod);
			
			
			mapper=result;
		}
		
		return mapper;
	}

	// ...
}
