package spoonapps.web.json;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import spoonapps.util.date.ThreadlocalDateFormat;

public class DateSerializer extends JsonSerializer<Date> {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final TimeZone tz = TimeZone.getTimeZone("UTC");


	/**
	 * <p>Constructor for DateSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public DateSerializer() {
	}


	@Override
	public void serialize(Date date,
						  JsonGenerator jgen,
						  SerializerProvider provider) throws IOException,
		JsonProcessingException {
		
		jgen.writeString(ThreadlocalDateFormat.format(DATE_FORMAT,date,tz,null));
	}

}
