package pl.jsolve.goldenlink.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notifications extends PaginationWrapper {

	private final List<Notification> notifications;

	@JsonCreator
	public Notifications(@JsonProperty("notifications") List<Notification> notifications, @JsonProperty("page") Integer page, @JsonProperty("resultsPerPage") Integer resultsPerPage,
			@JsonProperty("totalResults") Integer totalResults) {
		super(page, resultsPerPage, totalResults);
		this.notifications = notifications;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

}
