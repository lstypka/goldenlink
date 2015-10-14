package pl.jsolve.goldenlink.service.datetime

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {


    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @Override
    LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

        DateTimeFormat
                .forPattern(DATE_TIME_PATTERN)
                .withZone(DateTimeZone.UTC)
                .parseLocalDateTime jsonParser.getText()
    }
}
