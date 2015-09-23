package pl.jsolve.goldenlink.rest.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.sweetener.collection.Maps;

@Service
public class AuthorService {

	private Map<String, Author> authors = Maps.newHashMap();

	public Author addAuthor(Author author) {

		if (authors.containsKey(author.getName())) {
			return authors.get(author.getName());
		}

		Author newAuthor = new Author(generateId(), author.getName());
		authors.put(author.getName(), newAuthor);

		return newAuthor;
	}

	public List<Author> getAuthors() {
		return Lists.newArrayList(authors.values());
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
