package com.cassendra.betterreads.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("author_temp")
public class AuthorTemp implements Serializable {
	private static final long serialVersionUID = 305978904352461063L;

	@Column("id")
	@CassandraType(type = Name.TEXT)
	private String id;

	@Column("name")
	@CassandraType(type = Name.TEXT)
	private String name;

	public AuthorTemp(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author: id: " + id + " Name: " + name;
	}
}
