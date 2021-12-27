
package com.cassendra.betterreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cassendra.betterreads.service.AuthorService;
import com.cassendra.betterreads.service.BookService;

@RestController
public class MyAuthorController {

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@GetMapping(path = "/loadAuthorsOpenLibrary")
	public void loadAuthorsOpenLibrary(@RequestParam("q") String query,
			@RequestParam("start") int start, @RequestParam("end") int end) {
		authorService.loadAuthorsOpenLibrary(query, start, end);
	}

	@GetMapping(path = "/loadAuthors")
	public void loadAuthors() {
		authorService.loadAuthors();
	}

	@GetMapping(path = "/loadBooks")
	public void loadBooks() {
		bookService.loadBooks();
	}

	@GetMapping(path = "/loadBooksOpenLibrary")
	public void loadBooksOpenLibrary(@RequestParam("q") String query,
			@RequestParam("start") int start, @RequestParam("end") int end) {
		bookService.loadBooksOpenLibrary(query, start, end);
	}
}
