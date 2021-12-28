package com.cassendra.betterreads.model;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "book_by_user")
public class UserBooks {
	@PrimaryKey
	UserBooksPrimaryKey key;

	@Column(value = "start_date")
	@CassandraType(type = Name.DATE)
	private LocalDate startDate;

	@Column(value = "end_date")
	@CassandraType(type = Name.DATE)
	private LocalDate endDate;

	@Column(value = "status")
	@CassandraType(type = Name.INT)
	private int status;

	@Column(value = "rating")
	@CassandraType(type = Name.INT)
	private int rating;

	/**
	 * @return the key
	 */
	public UserBooksPrimaryKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(UserBooksPrimaryKey key) {
		this.key = key;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "UserBooks [key=" + key + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", status=" + status + ", rating="
				+ rating + "]";
	}

}
