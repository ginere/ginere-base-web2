package spoonapps.web.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import spoonapps.util.enumeration.AppEnum;

public class AppEnumDeserializer extends StdDeserializer<AppEnum> {

	private static final long serialVersionUID = "$Id:$".hashCode();
	
	public AppEnumDeserializer(Class<? extends AppEnum> vc) {
		super(vc);
	}
	

	public AppEnumDeserializer() {
		super(AppEnumDeserializer.class);
	}


	@SuppressWarnings("unchecked")
	@Override
	public AppEnum deserialize(JsonParser jp,
											  DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		String value=jp.getValueAsString();
		
        
		return AppEnum.fromString((Class<? extends AppEnum>) _valueClass, value);
	}

}
