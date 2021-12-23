package com.cassendra.betterreads.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cassendra.betterreads.model.Book;

@Repository
public interface BookRepository extends CassandraRepository<Book, String> {

}
