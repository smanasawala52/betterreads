package com.cassendra.betterreads.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResultItem {
	private String title = "";
	private String key = "";
	private List<String> author_name = new ArrayList<String>();
	private List<String> author_key = new ArrayList<String>();
	private List<String> publish_date = new ArrayList<String>();

	private String name = "";
	private String cover_i = "";
	// key,title,author_name,cover_i

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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the author_key
	 */
	public List<String> getAuthor_key() {
		return author_key;
	}
	/**
	 * @param author_key
	 *            the author_key to set
	 */
	public void setAuthor_key(List<String> author_key) {
		this.author_key = author_key;
	}
	/**
	 * @return the publish_date
	 */
	public List<String> getPublish_date() {
		return publish_date;
	}
	/**
	 * @param publish_date
	 *            the publish_date to set
	 */
	public void setPublish_date(List<String> publish_date) {
		this.publish_date = publish_date;
	}
	@Override
	public String toString() {
		return "SearchResultItem [title=" + title + ", key=" + key
				+ ", author_name=" + author_name + ", author_key=" + author_key
				+ ", publish_date=" + publish_date + ", name=" + name
				+ ", cover_i=" + cover_i + "]";
	}

}
