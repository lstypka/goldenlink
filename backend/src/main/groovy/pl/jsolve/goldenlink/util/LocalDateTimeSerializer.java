package pl.jsolve.goldenlink.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider ignored) throws IOException {

		jsonGenerator.writeString(formatter.print(dateTime));
	}
}