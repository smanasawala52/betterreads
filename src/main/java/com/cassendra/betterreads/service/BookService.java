package com.cassendra.betterreads.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cassendra.betterreads.model.AuthorTemp;
import com.cassendra.betterreads.model.Book;
import com.cassendra.betterreads.repository.AuthorRepository;
import com.cassendra.betterreads.repository.BookRepository;

@Service
public class BookService {

	final String COVER_IMG_ROOT = "https://covers.openlibrary.org/b/id/";
	@Autowired(required = false)
	private AuthorRepository authorRepository;

	@Autowired(required = false)
	private BookRepository bookRepository;

	@Value(value = "${book.file.path}")
	private String bookPath;

	private DateTimeFormatter dateFormat = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

	public void loadBooks() {
		System.out.println("Reached here Books Loading!");

		Path path = Paths.get(bookPath);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> {
				try {
					String json = line.substring(line.indexOf("{"));
					JSONObject jObj = new JSONObject(json);
					Book book = new Book();
					String id = jObj.optString("key")
							.substring(
									jObj.optString("key").lastIndexOf("/") + 1)
							.replace("/", "");
					String name = jObj.optString("title").replace("/", "");
					JSONObject description = jObj.optJSONObject("description");
					JSONObject publishedObj = jObj.optJSONObject("created");
					JSONArray coversObj = jObj.optJSONArray("covers");
					JSONArray authorsObj = jObj.optJSONArray("authors");
					// System.out.println("id: " + id + " Name: " + name
					// + " description: " + description + " publishedObj: "
					// + publishedObj + " covers" + coversObj + " autors"
					// + authorsObj);
					book.setId(id);
					book.setName(name);
					if (description != null) {
						book.setDescription(description.optString("value"));
					}
					if (publishedObj != null) {
						LocalDate publishedDate = LocalDate.parse(
								publishedObj.optString("value"), dateFormat);
						book.setPublishedDate(publishedDate);
					}
					if (coversObj != null) {
						List<String> coverIds = new ArrayList<>();
						for (int i = 0; i < coversObj.length(); i++) {
							coverIds.add(coversObj.optString(i));
						}
						book.setCoverIds(coverIds);
					}
					if (authorsObj != null) {
						List<String> authorsIds = new ArrayList<>();
						for (int i = 0; i < authorsObj.length(); i++) {
							JSONObject temp = authorsObj.optJSONObject(i);
							if (temp != null
									&& temp.optJSONObject("author") != null) {
								String author = authorsObj.optJSONObject(i)
										.optJSONObject("author")
										.optString("key");
								if (author != null && !author.isEmpty()) {
									authorsIds
											.add(author.replace("/authors/", "")
													.replace("/", ""));
								}
							}
						}
						if (!authorsIds.isEmpty()) {
							Map<String, AuthorTemp> tempAuthors = authorsIds
									.stream()
									.map(x -> authorRepository.findById(x))
									.map(optionalAuth -> {
										if (optionalAuth.isPresent()) {
											return new AuthorTemp(
													optionalAuth.get().getId(),
													optionalAuth.get()
															.getName());
										}
										return new AuthorTemp("OL00000000W",
												"Unknown Author.");
									}).filter(x -> x != null)
									.collect(Collectors.toMap(AuthorTemp::getId,
											Function.identity()));
							List<String> tempAuthorsNames = authorsIds.stream()
									.map(x -> authorRepository.findById(x))
									.map(optionalAuth -> {
										if (optionalAuth.isPresent()) {
											return optionalAuth.get().getName();
										}
										return "Unknown Author.";
									}).collect(Collectors.toList());
							System.out.println("tempAuthors: " + tempAuthors);
							if (tempAuthors != null && !tempAuthors.isEmpty()) {
								book.setAuthors(
										new ArrayList<>(tempAuthors.values()));
								// book.setAuthors(tempAuthors.get(0));
								book.setAuthorsIds(authorsIds);
								book.setAuthorsNames(tempAuthorsNames);
							}
						}

					}
					bookRepository.save(book);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Book> getBooks(String id) {
		List<Book> temp = bookRepository
				.findAllById(Arrays.asList(id.split(",")));
		temp.stream().forEach(x -> {
			if (x.getCoverIds() != null && !x.getCoverIds().isEmpty()) {
				x.setCoverImage(
						COVER_IMG_ROOT + x.getCoverIds().get(0) + ".jpg");
			} else {
				x.setCoverImage("/images/no-image.png");
			}
		});
		return temp;
	}
}
