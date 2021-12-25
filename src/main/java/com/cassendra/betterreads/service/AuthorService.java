package com.cassendra.betterreads.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cassendra.betterreads.model.Author;
import com.cassendra.betterreads.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired(required = false)
	private AuthorRepository authorRepository;

	@Value(value = "${author.file.path}")
	private String authorPath;

	private final WebClient webClient = WebClient.builder()
			.baseUrl("https://openlibrary.org/search.json")
			.defaultHeader(HttpHeaders.CONTENT_TYPE,
					MediaType.APPLICATION_JSON_VALUE)
			.build();

	public void loadAuthors() {
		System.out.println("Reached here!");

		Path path = Paths.get(authorPath);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> {
				try {
					String json = line.substring(line.indexOf("{"));
					JSONObject jObj = new JSONObject(json);
					Author author = new Author();
					String id = jObj.optString("key")
							.substring(
									jObj.optString("key").lastIndexOf("/") + 1)
							.replace("/", "");
					String name = jObj.optString("name").replace("/", "");
					String pName = jObj.optString("personal_name").replace("/",
							"");
					System.out.println("id: " + id + " Name: " + name
							+ " Personal Name: " + pName);
					author.setId(id);
					author.setName(name);
					author.setPersonalName(pName);
					authorRepository.save(author);
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
}
