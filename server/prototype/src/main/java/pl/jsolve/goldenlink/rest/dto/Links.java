package pl.jsolve.goldenlink.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Links extends PaginationWrapper {

	private final List<Link> links;

	@JsonCreator
	public Links(@JsonProperty("links") List<Link> links,
			@JsonProperty("page") Integer page,
			@JsonProperty("resultsPerPage") Integer resultsPerPage,
			@JsonProperty("totalResults") Integer totalResults) {
		super(page, resultsPerPage, totalResults);
		this.links = links;
	}

	public List<Link> getLinks() {
		return links;
	}

}
