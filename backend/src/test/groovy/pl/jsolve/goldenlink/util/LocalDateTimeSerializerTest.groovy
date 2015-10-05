package pl.jsolve.goldenlink.util

import com.fasterxml.jackson.core.JsonGenerator
import org.joda.time.LocalDateTime
import spock.lang.Specification

class LocalDateTimeSerializerTest extends Specification {

    def jsonGenerator = Mock(JsonGenerator)

    LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer()

    def "Should serialize date time"() {
        given:
        def dateTime = new LocalDateTime("2015-09-25T12:50:00")

        when:
        localDateTimeSerializer.serialize(dateTime, jsonGenerator, null)

        then:
        1 * jsonGenerator.writeString("2015-09-25T12:50:00Z")
    }
}
