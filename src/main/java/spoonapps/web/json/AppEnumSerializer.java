package spoonapps.web.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import spoonapps.util.enumeration.AppEnum;

public class AppEnumSerializer extends JsonSerializer<AppEnum> {

	protected AppEnumSerializer() {
	}

	@Override
	public void serialize(AppEnum obj,
						  JsonGenerator jgen,
						  SerializerProvider provider) throws IOException, JsonProcessingException {

		if (obj==null){
			jgen.writeObject(null);
		} else {
			jgen.writeString(obj.getId());
		}
		
	}

}
