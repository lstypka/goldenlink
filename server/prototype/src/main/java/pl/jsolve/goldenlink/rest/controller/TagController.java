package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Tag;

import com.google.common.collect.Lists;

@RestController
public class TagController {

	@RequestMapping(value = "/tags/", method = RequestMethod.GET)
	public List<Tag> getTags() {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Tag> tags = Lists.newArrayList();
		for (int i = 0; i < 50; i++) {
			tags.add(new Tag(generateId(), "Znajomy nr " + (i + 1)));
		}
		return tags;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
