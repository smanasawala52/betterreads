package com.cassendra.betterreads.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.cassendra.betterreads.model.BooksByUser;

@Repository
public interface BooksByUserRepository
		extends
			CassandraRepository<BooksByUser, String> {

	Slice<BooksByUser> findAllById(String userId, Pageable page);

}
