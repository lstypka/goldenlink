package pl.jsolve.goldenlink.dto
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.Immutable
import org.joda.time.LocalDateTime
import pl.jsolve.goldenlink.api.author.Author
import pl.jsolve.goldenlink.api.category.Category
import pl.jsolve.goldenlink.service.datetime.LocalDateTimeDeserializer
import pl.jsolve.goldenlink.service.datetime.LocalDateTimeSerializer

class Link {
    String publicId
    String link
    String title
    String comment
    Category category
    List<Tag> tags

    @JsonSerialize(using = LocalDateTimeSerializer)
    @JsonDeserialize(using = LocalDateTimeDeserializer)
    LocalDateTime date
    @JsonSerialize(using = LocalDateTimeSerializer)
    @JsonDeserialize(using = LocalDateTimeDeserializer)
    LocalDateTime expirationDate

    Author author
    boolean isMarked
}
