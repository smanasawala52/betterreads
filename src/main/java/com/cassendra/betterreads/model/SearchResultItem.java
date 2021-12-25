package com.cassendra.betterreads.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResultItem {
	private String title = "";
	private String key = "";
	private List<String> author_name = new ArrayList<String>();
	private String cover_i = "";
	// key,title,author_name,cover_i

	@Override
	public String toString() {
		return "SearchResultItem [title=" + title + ", key=" + key
				+ ", author_name=" + author_name + ", cover_i=" + cover_i + "]";
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the author_name
	 */
	public List<String> getAuthor_name() {
		return author_name;
	}
	/**
	 * @param author_name
	 *            the author_name to set
	 */
	public void setAuthor_name(List<String> author_name) {
		this.author_name = author_name;
	}
	/**
	 * @return the cover_i
	 */
	public String getCover_i() {
		return cover_i;
	}
	/**
	 * @param cover_i
	 *            the cover_i to set
	 */
	public void setCover_i(String cover_i) {
		this.cover_i = cover_i;
	}

}
