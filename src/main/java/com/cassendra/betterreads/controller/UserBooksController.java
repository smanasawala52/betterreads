
package com.cassendra.betterreads.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cassendra.betterreads.model.Book;
import com.cassendra.betterreads.model.BooksByUser;
import com.cassendra.betterreads.model.UserBooks;
import com.cassendra.betterreads.model.UserBooksPrimaryKey;
import com.cassendra.betterreads.repository.BookRepository;
import com.cassendra.betterreads.repository.BooksByUserRepository;
import com.cassendra.betterreads.repository.UserBooksRepository;

@RestController
public class UserBooksController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserBooksRepository userBooksRepository;

	@Autowired
	private BooksByUserRepository booksByUserRepository;

	@PostMapping(path = "/addUserBook")
	public ModelAndView addBook(@AuthenticationPrincipal OAuth2User principal,
			@RequestBody MultiValueMap<String, String> formData) {
		if (principal != null && principal.getAttributes() != null
				&& principal.getAttributes().get("login") != null) {

			String bookId = formData.getFirst("bookId");
			Optional<Book> optionalBook = bookRepository.findById(bookId);
			if (!optionalBook.isPresent()) {
				return new ModelAndView("redirect:/");
			}
			Book book = optionalBook.get();

			UserBooks userBooks = new UserBooks();
			UserBooksPrimaryKey key = new UserBooksPrimaryKey();
			String userId = String
					.valueOf(principal.getAttributes().get("login"));
			key.setUserId(userId);
			key.setBookId(book.getId());

			userBooks.setKey(key);

			int rating = Integer.parseInt(formData.getFirst("rating"));
			userBooks.setStartDate(
					LocalDate.parse(formData.getFirst("startDate")));
			userBooks.setEndDate(LocalDate.parse(formData.getFirst("endDate")));
			userBooks.setRating(rating);
			userBooks.setStatus(Integer.parseInt(formData.getFirst("status")));

			userBooksRepository.save(userBooks);

			BooksByUser booksByUser = new BooksByUser();
			booksByUser.setAuthorNames(book.getAuthorsNames());
			booksByUser.setBookId(bookId);
			booksByUser.setBookName(book.getName());
			booksByUser.setCoverIds(book.getCoverIds());
			booksByUser.setId(userId);
			booksByUser.setRating(rating);
			booksByUser.setReadingStatus(String
					.valueOf(Integer.parseInt(formData.getFirst("status"))));

			booksByUserRepository.save(booksByUser);
			return new ModelAndView("redirect:/books/" + book.getId());
		}
		return null;
	}

}
