package pl.jsolve.goldenlink.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Links;
import pl.jsolve.goldenlink.rest.service.LinkService;

@RestController
public class LinkController {

	@Autowired
	private LinkService linkService;

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

		return linkService.searchLinks(categoryId, page, resultsPerPage, title,
				comment, null, tag, author, date, null, null);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Links search(
			@RequestParam("page") final Integer page,
			@RequestParam("resultsPerPage") final Integer resultsPerPage,
			@RequestParam(value = "title", required = false) final String title,
			@RequestParam(value = "comment", required = false) final String comment,
			@RequestParam(value = "categoryPublicId", required = false) final String categoryPublicId,
			@RequestParam(value = "type", required = false) final String type,
			@RequestParam(value = "tag", required = false) final String tag,
			@RequestParam(value = "author", required = false) final String author,
			@RequestParam(value = "date", required = false) final String date,
			@RequestParam(value = "expiryDate", required = false) final String expiryDate,
			@RequestParam(value = "marked", required = false) final String marked) {

		System.out.println("SEARCH -> title: " + title + " comment: " + comment
				+ " author: " + author + " tag: " + tag + " date: " + date);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return linkService.searchLinks(categoryPublicId, page, resultsPerPage,
				title, comment, type, tag, author, date, expiryDate, marked);
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links", method = RequestMethod.POST)
	public Link createLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@RequestBody Link linkToAdd, @RequestParam(value = "shareLinkPublicId", required = false) String shareLinkPublicId) {
		return linkService.addLink(linkToAdd, shareLinkPublicId);
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links/{linkPublicId}", method = RequestMethod.PUT)
	public Link updateLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@PathVariable("linkPublicId") final String linkPublicId,
			@RequestBody Link linkToUpdate) {

		return linkService.updateLink(linkToUpdate);
	}

	@RequestMapping(value = "/categories/{categoryPublicId}/links/{linkPublicId}", method = RequestMethod.DELETE)
	public Void deleteLink(
			@PathVariable("categoryPublicId") final String categoryPublicId,
			@PathVariable("linkPublicId") final String linkPublicId) {

		return linkService.deleteLink(linkPublicId);
	}

}
