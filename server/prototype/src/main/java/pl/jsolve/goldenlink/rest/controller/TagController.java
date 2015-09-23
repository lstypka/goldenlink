package pl.jsolve.goldenlink.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.goldenlink.rest.service.TagService;

@RestController
public class TagController {

	@Autowired
	private TagService tagService;

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public List<Tag> getTags() {
		return tagService.getTags();
	}

}
