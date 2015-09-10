package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginationWrapper {

	private final Integer page;
	private final Integer resultsPerPage;
	private final Integer totalResults;

	@JsonCreator
	public PaginationWrapper(@JsonProperty("page") Integer page,
			@JsonProperty("resultsPerPage") Integer resultsPerPage,
			@JsonProperty("totalResults") Integer totalResults) {
		this.page = page;
		this.resultsPerPage = resultsPerPage;
		this.totalResults = totalResults;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

}
