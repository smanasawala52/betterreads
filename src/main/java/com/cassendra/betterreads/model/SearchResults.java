package com.cassendra.betterreads.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {
	private int numFound = 0;
	private List<SearchResultItem> docs = new ArrayList<>();
	private int start = 0;

	@Override
	public String toString() {
		return "SearchResults [numFound=" + numFound + ", searchResultItems="
				+ docs + ", start=" + start + "]";
	}
	/**
	 * @return the numFound
	 */
	public int getNumFound() {
		return numFound;
	}
	/**
	 * @param numFound
	 *            the numFound to set
	 */
	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}
	/**
	 * @return the searchResultItems
	 */
	public List<SearchResultItem> getDocs() {
		return docs;
	}
	/**
	 * @param searchResultItems
	 *            the searchResultItems to set
	 */
	public void setDocs(List<SearchResultItem> searchResultItems) {
		this.docs = searchResultItems;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
}
