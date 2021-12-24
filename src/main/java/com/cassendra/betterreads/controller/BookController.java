
package com.cassendra.betterreads.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cassendra.betterreads.model.Book;
import com.cassendra.betterreads.service.AuthorService;
import com.cassendra.betterreads.service.BookService;

@RestController
public class BookController {

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;

	@GetMapping(path = "/books/{id}")
	public ModelAndView getBooks(@PathVariable String id, Model model) {
		List<Book> books = new ArrayList<>();
		if (id != null && !id.isEmpty()) {
			books = bookService.getBooks(id);
		}
		if (books != null && !books.isEmpty()) {
			model.addAttribute("books", books);
			ModelAndView mav = new ModelAndView("books");
			mav.addObject("books", books);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("book-not-found");
			return mav;
		}
	}

}
