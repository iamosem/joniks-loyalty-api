package com.joniks.loyalty.api.model.params;

public class PaginationParams {

	private int size;
	private int page;
	private String sortBy;
	private String sortOrder; // asc ; desc
	private boolean disablePagination;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean getDisablePagination() {
		return disablePagination;
	}

	public void setDisablePagination(boolean disablePagination) {
		this.disablePagination = disablePagination;
	}

}
