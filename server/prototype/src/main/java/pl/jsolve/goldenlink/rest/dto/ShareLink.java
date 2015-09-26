package pl.jsolve.goldenlink.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareLink {

	private final Link link;
	private final List<Friend> friends;

	@JsonCreator
	public ShareLink(@JsonProperty("link") Link link, @JsonProperty("friends") List<Friend> friends) {
		this.link = link;
		this.friends = friends;
	}

	public Link getLink() {
		return link;
	}

	public List<Friend> getFriends() {
		return friends;
	}

}
