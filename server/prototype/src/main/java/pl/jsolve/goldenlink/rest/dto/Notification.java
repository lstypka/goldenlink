package pl.jsolve.goldenlink.rest.dto;

import org.joda.time.LocalDateTime;

import pl.jsolve.goldenlink.rest.util.LocalDateTimeDeserializer;
import pl.jsolve.goldenlink.rest.util.LocalDateTimeSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Notification {

	private final String publicId;
	private final Link link;
	private final String categoryGroup;
	private final Author author;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private final LocalDateTime date;

	@JsonCreator
	public Notification(@JsonProperty("publicId") String publicId, @JsonProperty("link") Link link, @JsonProperty("author") Author author, @JsonProperty("categoryGroup") String categoryGroup,
			@JsonProperty("date") LocalDateTime date) {
		this.publicId = publicId;
		this.link = link;
		this.author = author;
		this.categoryGroup = categoryGroup;
		this.date = date;
	}

	public String getPublicId() {
		return publicId;
	}

	public Link getLink() {
		return link;
	}

	public Author getAuthor() {
		return author;
	}

	public String getCategoryGroup() {
		return categoryGroup;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Notification [publicId=" + publicId + ", link=" + link + ", author=" + author + ", date=" + date + "]";
	}

}