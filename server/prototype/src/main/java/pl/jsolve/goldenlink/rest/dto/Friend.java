package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Friend {

	private final String publicId;
	private final String name;
	private final String thumbnail;
	private final String sex;
	private final Integer numberOfSharedLinks;
	private final Boolean isGroup;

	@JsonCreator
	public Friend(@JsonProperty("publicId") String publicId,
			@JsonProperty("name") String name,
			@JsonProperty("thumbnail") String thumbnail,
			@JsonProperty("sex") String sex,
			@JsonProperty("numberOfSharedLinks") Integer numberOfSharedLinks,
			@JsonProperty("isGroup") Boolean isGroup) {
		this.publicId = publicId;
		this.name = name;
		this.thumbnail = thumbnail;
		this.sex = sex;
		this.numberOfSharedLinks = numberOfSharedLinks;
		this.isGroup = isGroup;
	}

	public String getPublicId() {
		return publicId;
	}

	public String getName() {
		return name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getSex() {
		return sex;
	}

	public Integer getNumberOfSharedLinks() {
		return numberOfSharedLinks;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

}
