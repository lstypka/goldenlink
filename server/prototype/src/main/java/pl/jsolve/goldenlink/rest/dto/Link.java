package pl.jsolve.goldenlink.rest.dto;

import java.util.List;

import org.joda.time.LocalDateTime;

import pl.jsolve.goldenlink.rest.util.LocalDateTimeDeserializer;
import pl.jsolve.goldenlink.rest.util.LocalDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Link {

    private final String publicId;
    private final String link;
    private final String title;
    private final String comment;
    private final Category category;
    private final List<Tag> tags;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime date;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime expiryDate;
    private final Author author;
    private final boolean isMarked;

    @JsonCreator
    public Link(@JsonProperty("publicId") String publicId, @JsonProperty("link") String link,
            @JsonProperty("title") String title, @JsonProperty("comment") String comment,
            @JsonProperty("category") Category category, @JsonProperty("tags") List<Tag> tags,
            @JsonProperty("date") LocalDateTime date, @JsonProperty("expireDate") LocalDateTime expiryDate,
            @JsonProperty("author") Author author, @JsonProperty("isMarked") boolean isMarked) {
        super();
        this.publicId = publicId;
        this.link = link;
        this.title = title;
        this.comment = comment;
        this.category = category;
        this.tags = tags;
        this.date = date;
        this.expiryDate = expiryDate;
        this.author = author;
        this.isMarked = isMarked;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public Category getCategory() {
        return category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isMarked() {
        return isMarked;
    }

}
