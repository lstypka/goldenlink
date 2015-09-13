package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Author;

import com.google.common.collect.Lists;

@RestController
public class AuthorsController {

	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	public List<Author> getFriends() {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Author> friends = Lists.newArrayList();
		for (int i = 0; i < 50; i++) {
			friends.add(new Author(generateId(), "Znajomy nr " + (i + 1)));
		}
		return friends;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
