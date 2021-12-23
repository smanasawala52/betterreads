package com.cassendra.betterreads.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cassendra.betterreads.model.Author;

@Repository
public interface AuthorRepository extends CassandraRepository<Author, String> {

}
