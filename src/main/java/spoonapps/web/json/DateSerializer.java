package spoonapps.web.json;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import spoonapps.util.date.ThreadlocalDateFormat;

public class DateSerializer extends JsonSerializer<Date> {

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public void serialize(Date obj, JsonGenerator jgen, SerializerProvider provider)
		throws IOException, JsonProcessingException {
		
		jgen.writeString(ThreadlocalDateFormat.format(DATE_FORMAT,obj,null));
	}

}
