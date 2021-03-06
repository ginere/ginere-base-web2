package spoonapps.web.json;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import spoonapps.util.enumeration.AppEnum;

/**
 * https://jersey.java.net/documentation/latest/media.html
 *
 */
public class ObjectMapperProvider {

	private static  ObjectMapper mapper;

	/**
	 * <p>Constructor for ObjectMapperProvider.</p>
	 */
	public ObjectMapperProvider() {
		mapper = createDefaultMapper();
	}

	public static ObjectMapper createDefaultMapper() {
		if (mapper==null){
			final ObjectMapper result = new ObjectMapper();
	
			SimpleModule mod = new SimpleModule("SimpleModule");

			
			// Add the custom serializer to the module
			mod.addSerializer(AppEnum.class,new AppEnumSerializer());
			mod.addSerializer(Date.class,new DateSerializer());
			
			// Needs to add the rea class
			// mod.addDeserializer(AppEnum.class, new AppEnumDeserializer(AppEnum.class));
			
			result.registerModule(mod);
			
			
			mapper=result;
		}
		
		return mapper;
	}
}
