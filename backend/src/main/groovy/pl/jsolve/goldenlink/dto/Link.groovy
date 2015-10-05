package pl.jsolve.goldenlink.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.Immutable
import org.joda.time.LocalDateTime
import pl.jsolve.goldenlink.util.LocalDateTimeDeserializer
import pl.jsolve.goldenlink.util.LocalDateTimeSerializer

@Immutable(knownImmutableClasses = [LocalDateTime])
class Link {
    String publicId
    String link
    String title
    String comment
    Category category
    List<Tag> tags

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime date
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime expirationDate

    Author author
    boolean isMarked
}
