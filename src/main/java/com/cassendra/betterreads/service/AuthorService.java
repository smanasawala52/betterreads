package com.cassendra.betterreads.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.cassendra.betterreads.model.Author;
import com.cassendra.betterreads.model.SearchResults;
import com.cassendra.betterreads.repository.AuthorRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthorService {

	@Autowired(required = false)
	private AuthorRepository authorRepository;

	@Value(value = "${author.file.path}")
	private String authorPath;

	private final WebClient webClient = WebClient.builder()
			.baseUrl("https://openlibrary.org/search/authors.json")
			.defaultHeader(HttpHeaders.CONTENT_TYPE,
					MediaType.APPLICATION_JSON_VALUE)
			.build();

	private final WebClient webClientAuthorDetails = WebClient.builder()
			.baseUrl("https://openlibrary.org")
			.defaultHeader(HttpHeaders.CONTENT_TYPE,
					MediaType.APPLICATION_JSON_VALUE)
			.build();

	public void loadAuthorsOpenLibrary(String query, int start, int end) {
		System.out.println(query);
		for (int i = start; i <= end; i += 10) {
			System.out.println("start: " + i + " end: " + (i + 10));
			Mono<SearchResults> resultsMono = this.webClient.get()
					.uri("?fields=key&limit=10&offset=" + i + "&q=" + query)
					.retrieve().bodyToMono(SearchResults.class);
			SearchResults result = resultsMono.block();
			// System.out.println(result);
			if (result != null && !CollectionUtils.isEmpty(result.getDocs())) {
				List<Author> authors = result.getDocs().stream()
						.map(authorResult -> {
							String temp = "/authors/" + authorResult.getKey()
									+ ".json";
							// System.out.println(temp);
							String responseJson = webClientAuthorDetails.get()
									.uri(temp).exchange().block()
									.bodyToMono(String.class).block();
							// System.out.println(responseJson);
							return responseJson;
						}).map(line -> {
							return getAuthor(line);
						}).filter(x -> x != null).collect(Collectors.toList());
				// System.out.println("authors" + authors);
				if (!CollectionUtils.isEmpty(authors)) {
					authorRepository.saveAll(authors);
				}
			}
		}
	}

	public void loadAuthorsOpenLibraryOneAuthor(String query) {
		if (query != null && !query.isEmpty()) {
			String temp = "/authors/" + query + ".json";
			String responseJson = webClientAuthorDetails.get().uri(temp)
					.exchange().block().bodyToMono(String.class).block();
			Author author = getAuthor(responseJson);
			if (author != null && author.getName() != null
					&& !author.getName().isEmpty()) {
				authorRepository.save(author);
			}

		}
	}
	
	public void loadAuthors() {
		System.out.println("Reached here!");

		Path path = Paths.get(authorPath);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> {
				try {
					String json = line.substring(line.indexOf("{"));
					Author author = getAuthor(json);
					if (author != null) {
						authorRepository.save(author);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Author getAuthor(String json) {
		try {
			JSONObject jObj = new JSONObject(json);
			String id = jObj.optString("key")
					.substring(jObj.optString("key").lastIndexOf("/") + 1)
					.replace("/", "");
			String name = jObj.optString("name").replace("/", "");
			String pName = jObj.optString("personal_name").replace("/", "");
			// System.out.println("id: " + id + " Name: " + name
			// + " Personal Name: " + pName);
			Author author = new Author();
			author.setId(id);
			author.setName(name);
			author.setPersonalName(pName);
			return author;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
