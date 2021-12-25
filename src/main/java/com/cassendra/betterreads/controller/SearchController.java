
package com.cassendra.betterreads.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.cassendra.betterreads.model.SearchResultItem;
import com.cassendra.betterreads.model.SearchResults;

import reactor.core.publisher.Mono;

@RestController
public class SearchController {
	private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	private final WebClient webClient = WebClient.builder()
			.baseUrl("https://openlibrary.org/search.json")
			.defaultHeader(HttpHeaders.CONTENT_TYPE,
					MediaType.APPLICATION_JSON_VALUE)
			.build();

	@GetMapping(path = "/search")
	public ModelAndView search(@RequestParam("query") String query) {
		System.out.println(query);
		Mono<SearchResults> resultsMono = this.webClient.get().uri(
				"?fields=key,title,author_name,first_publish_year,cover_i&limit=10&page=1&q="
						+ query)
				.retrieve().bodyToMono(SearchResults.class);
		SearchResults result = resultsMono.block();
		System.out.println(result);
		List<SearchResultItem> books = result.getDocs().stream().limit(10)
				.map(bookResult -> {
					bookResult
							.setKey(bookResult.getKey().replace("/works/", ""));
					String coverId = bookResult.getCover_i();
					if (StringUtils.hasText(coverId)) {
						coverId = COVER_IMAGE_ROOT + coverId + "-M.jpg";
					} else {
						coverId = "/images/no-image.png";
					}
					bookResult.setCover_i(coverId);
					return bookResult;
				}).collect(Collectors.toList());

		ModelAndView model = new ModelAndView("search");
		System.out.println(books);
		model.addObject("searchResults", books);
		return model;
	}

}
