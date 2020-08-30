package com.joniks.loyalty.api.model;

import java.util.Collection;
import java.util.List;

public class ListResult {

	public List<?> results;
	public long totalResults;

	public ListResult(List<?> results, long resultsCount) {
		this.results = results;
		totalResults = resultsCount;
	}

	public Collection<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}

}
