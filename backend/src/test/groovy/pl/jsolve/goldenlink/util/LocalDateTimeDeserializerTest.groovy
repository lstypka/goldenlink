package pl.jsolve.goldenlink.util
import com.fasterxml.jackson.core.JsonParser
import spock.lang.Specification

class LocalDateTimeDeserializerTest extends Specification {

    def jsonParser = Mock(JsonParser)

    LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer()

    def 'Should deserialize date time'() {
        given:
        jsonParser.getText() >> '2015-09-25T12:50:00Z'

        when:
        def result = localDateTimeDeserializer.deserialize(jsonParser, null)

        then:
        result.year == 2015
        result.monthOfYear == 9
        result.dayOfMonth == 25
        result.hourOfDay == 12
        result.minuteOfHour == 50
        result.secondOfMinute == 0
    }

    def 'Should not deserialize invalid date format'() {
        given:
        def expectedMessage = 'invalid date format'
        jsonParser.getText() >> expectedMessage

        when:
        println "test"
        localDateTimeDeserializer.deserialize(jsonParser, null)

        then:
        def illegalAccessException = thrown(IllegalArgumentException)
        illegalAccessException.message == "Invalid format: \"$expectedMessage\""
    }
}
