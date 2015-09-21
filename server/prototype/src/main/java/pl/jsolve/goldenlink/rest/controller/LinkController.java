package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Links;
import pl.jsolve.goldenlink.rest.service.CategoryService;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@RestController
public class LinkController {

	private List<Link> links = Lists.newArrayList();

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/categories/{categoryId}/links", method = RequestMethod.GET)
	public Links getLinks(
			@PathVariable("categoryId") final String categoryId,
			@RequestParam("page") final Integer page,
			@RequestParam("resultsPerPage") final Integer resultsPerPage,
			@RequestParam(value = "title", required = false) final String title,
			@RequestParam(value = "comment", required = false) final String comment,
			@RequestParam(value = "author", required = false) final String author,
			@RequestParam(value = "tag", required = false) final String tag,
			@RequestParam(value = "date", required = false) final String date) {

		System.out.println("SEARCH -> title: " + title + " comment: " + comment
				+ " author: " + author + " tag: " + tag + " date: " + date);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Link> filteredLinks = Lists.newArrayList(Collections2.filter(
				links, new Predicate<Link>() {

					@Override
					public boolean apply(Link input) {
						return input.getCategory().getPublicId()
								.equals(categoryId);
					}

				}));

		int totalResults = filteredLinks.size();
		if (filteredLinks.size() > resultsPerPage) {
			int fromIndex = page * resultsPerPage;
			int toIndex = (page + 1) * resultsPerPage;
			if (toIndex > filteredLinks.size()) {
				toIndex = filteredLinks.size();
			}
			filteredLinks = filteredLinks.subList(fromIndex, toIndex);
		}

		return new Links(filteredLinks, page, resultsPerPage, totalResults);
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links", method = RequestMethod.POST)
	public Link createLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@RequestBody Link linkToAdd) {
		linkToAdd.setPublicId(generateId());
		links.add(linkToAdd);
		return linkToAdd;
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links/{linkPublicId}", method = RequestMethod.PUT)
	public Link updateLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@PathVariable("linkPublicId") final String linkPublicId,
			@RequestBody Link linkToUpdate) {

		int foundIndex = -1;
		for (int i = 0; i < links.size(); i++) {
			if (linkToUpdate.getPublicId().equals(links.get(i).getPublicId())) {
				foundIndex = i;
			}
		}
		if (foundIndex > -1) {
			links.remove(foundIndex);
			links.add(linkToUpdate);
		}

		return linkToUpdate;
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links/{linkPublicId}", method = RequestMethod.DELETE)
	public Void deleteLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@PathVariable("linkPublicId") final String linkPublicId) {

		int foundIndex = -1;
		for (int i = 0; i < links.size(); i++) {
			if (linkPublicId.equals(links.get(i).getPublicId())) {
				foundIndex = i;
			}
		}
		if (foundIndex > -1) {
			links.remove(foundIndex);
		}
		return null;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
