package spoonapps.web.json;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import spoonapps.util.enumeration.AppEnum;

public class AppEnumSerializer extends JsonSerializer<AppEnum> {

//    public AppEnumSerializer() {
//        this(null);
//    }
//    
//	public AppEnumSerializer(Class<AppEnum> t) {
//		super(t);
//	}

//	@Override
//	public void serialize(AppEnum obj, JsonGenerator jgen, SerializerProvider sp)
//			throws IOException, JsonGenerationException {
//		
//		jgen.writeString(obj.getId());
//	}

	@Override
	public void serialize(AppEnum obj, org.codehaus.jackson.JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(obj.getId());
		
	}

}
