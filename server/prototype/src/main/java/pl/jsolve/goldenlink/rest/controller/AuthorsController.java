package pl.jsolve.goldenlink.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.service.AuthorService;

@RestController
public class AuthorsController {

	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	public List<Author> getFriends() {

		return authorService.getAuthors();
	}

}
