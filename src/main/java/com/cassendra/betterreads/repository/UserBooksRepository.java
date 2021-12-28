package com.cassendra.betterreads.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cassendra.betterreads.model.UserBooks;
import com.cassendra.betterreads.model.UserBooksPrimaryKey;

@Repository
public interface UserBooksRepository
		extends
			CassandraRepository<UserBooks, UserBooksPrimaryKey> {

}
