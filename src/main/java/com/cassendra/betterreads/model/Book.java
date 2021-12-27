package com.cassendra.betterreads.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "book_by_id")
public class Book {
	@Id
	@PrimaryKeyColumn(name = "book_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String id;

	@Column(value = "book_name")
	@CassandraType(type = Name.TEXT)
	private String name;

	@Column(value = "book_description")
	@CassandraType(type = Name.TEXT)
	private String description;

	@Column(value = "published_date")
	@CassandraType(type = Name.DATE)
	private LocalDate publishedDate;

	@Column(value = "cover_ids")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> coverIds;

	@Column(value = "author_ids")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> authorsIds;

	@Column(value = "author_names")
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> authorsNames;

	@Column(value = "authors")
	private List<AuthorTemp> authors = new ArrayList<>();

	private String coverImage = "";

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the publishedDate
	 */
	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	/**
	 * @param publishedDate
	 *            the publishedDate to set
	 */
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	/**
	 * @return the coverIds
	 */
	public List<String> getCoverIds() {
		return coverIds;
	}

	/**
	 * @param coverIds
	 *            the coverIds to set
	 */
	public void setCoverIds(List<String> coverIds) {
		this.coverIds = coverIds;
	}

	/**
	 * @return the authors
	 */
	public List<AuthorTemp> getAuthors() {
		return authors;
	}

	/**
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(List<AuthorTemp> authors) {
		this.authors = authors;
	}

	/**
	 * @return the authorsIds
	 */
	public List<String> getAuthorsIds() {
		return authorsIds;
	}

	/**
	 * @param authorsIds
	 *            the authorsIds to set
	 */
	public void setAuthorsIds(List<String> authorsIds) {
		this.authorsIds = authorsIds;
	}

	/**
	 * @return the authorsNames
	 */
	public List<String> getAuthorsNames() {
		return authorsNames;
	}

	/**
	 * @param authorsNames
	 *            the authorsNames to set
	 */
	public void setAuthorsNames(List<String> authorsNames) {
		this.authorsNames = authorsNames;
	}
	/**
	 * @return the coverImage
	 */
	public String getCoverImage() {
		return coverImage;
	}

	/**
	 * @param coverImage
	 *            the coverImage to set
	 */
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", description="
				+ description + ", publishedDate=" + publishedDate
				+ ", coverIds=" + coverIds + ", authorsIds=" + authorsIds
				+ ", authorsNames=" + authorsNames + ", authors=" + authors
				+ ", coverImage=" + coverImage + "]";
	}

}
