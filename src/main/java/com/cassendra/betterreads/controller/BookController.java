
package com.cassendra.betterreads.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cassendra.betterreads.model.Book;
import com.cassendra.betterreads.model.UserBooks;
import com.cassendra.betterreads.model.UserBooksPrimaryKey;
import com.cassendra.betterreads.repository.UserBooksRepository;
import com.cassendra.betterreads.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserBooksRepository userBooksRepository;

	@GetMapping(path = "/books/{id}")
	public ModelAndView getBooks(@AuthenticationPrincipal OAuth2User principal,
			@PathVariable String id, Model model) {
		List<Book> books = new ArrayList<>();
		if (id != null && !id.isEmpty()) {
			books = bookService.getBooks(id);
		}
		if (books == null || (books != null && !books.isEmpty())) {
			Arrays.asList(id.split(",")).stream().forEach(x -> {
				bookService.loadBooksOpenLibrary(id, 0, 10);
			});
			books = bookService.getBooks(id);
		}
		if (books != null && !books.isEmpty()) {
			model.addAttribute("books", books);
			ModelAndView mav = new ModelAndView("books");
			mav.addObject("loginId", null);
			if (principal != null && principal.getAttributes() != null
					&& principal.getAttributes().get("login") != null) {
				mav.addObject("loginId",
						String.valueOf(principal.getAttributes().get("login")));
				// get users book info
				books = books.stream().map(book -> {
					UserBooksPrimaryKey userBooksInput = new UserBooksPrimaryKey();
					userBooksInput.setBookId(book.getId());
					userBooksInput.setUserId(String
							.valueOf(principal.getAttributes().get("login")));
					Optional<UserBooks> tempUserBooks = userBooksRepository
							.findById(userBooksInput);
					if (tempUserBooks.isPresent()) {
						book.setUserBookInfo(tempUserBooks.get());
					} else {
						UserBooks userBook = new UserBooks();
						userBook.setKey(userBooksInput);
						userBook.setStatus(3);
						userBook.setRating(0);
						book.setUserBookInfo(userBook);
					}
					return book;
				}).collect(Collectors.toList());
			}
			System.out.println(books);
			mav.addObject("books", books);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("book-not-found");
			return mav;
		}
	}

}
