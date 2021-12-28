
package com.cassendra.betterreads.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cassendra.betterreads.model.BooksByUser;
import com.cassendra.betterreads.repository.BooksByUserRepository;

@RestController
public class HomeController {

	final String COVER_IMG_ROOT = "https://covers.openlibrary.org/b/id/";
	@Autowired
	private BooksByUserRepository booksByUserRepository;

	@GetMapping(path = "/")
	public ModelAndView getHome(@AuthenticationPrincipal OAuth2User principal) {
		if (principal != null && principal.getAttributes() != null
				&& principal.getAttributes().get("login") != null) {
			ModelAndView model = new ModelAndView("home");
			String userId = String
					.valueOf(principal.getAttributes().get("login"));
			model.addObject("loginId", userId);
			Slice<BooksByUser> temp = booksByUserRepository.findAllById(userId,
					CassandraPageRequest.of(0, 10));

			List<BooksByUser> userBooks = temp.getContent();
			List<BooksByUser> books = userBooks.stream().map(x -> {
				if (x.getCoverIds() != null && !x.getCoverIds().isEmpty()) {
					x.setCoverUrl(
							COVER_IMG_ROOT + x.getCoverIds().get(0) + ".jpg");
				} else {
					x.setCoverUrl("/images/no-image.png");
				}
				return x;
			}).filter(x -> x != null).collect(Collectors.toList());
			model.addObject("books", books);
			return model;
		} else {
			ModelAndView model = new ModelAndView("index");
			return model;
		}
	}

}
