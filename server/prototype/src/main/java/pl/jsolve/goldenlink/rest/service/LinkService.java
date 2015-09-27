package pl.jsolve.goldenlink.rest.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Links;
import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.sweetener.text.Strings;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Service
public class LinkService {

	@Autowired
	private TagService tagService;

	public static List<Link> links = Lists.newArrayList();

	public Link addLink(Link link) {
		return addLink(link, null);
	}

	public Link addLink(Link link, String shareLinkPublicId) {
		// jesli shareLinkPublicId jest roznce od null, to oznacza ze uzytkownik
		// stworzył nowy link na postawie linku udostępnionego od znajomego.
		// W takim przypadku author powinine zostac przepisany od oryginalnego
		// uzytkownika (tego co udostepnial)
		link.setPublicId(generateId());
		if (shareLinkPublicId == null) {
			link.setAuthor(new Author(generateId(), "Tomasz Kuryłek"));
		}
		link.setDate(LocalDateTime.now(DateTimeZone.UTC));
		links.add(link);

		if (link.getTags() != null) {
			for (Tag tag : link.getTags()) {
				tagService.addTag(tag);
			}
		}
		return link;
	}

	public Links searchLinks(final String categoryId, final Integer page, final Integer resultsPerPage, final String title, final String comment, final String type, final String tag,
			final String author, final String date, final String expiryDate, final String marked) {
		List<Link> filteredLinks = Lists.newArrayList(Collections2.filter(links, new Predicate<Link>() {

			@Override
			public boolean apply(Link input) {
				boolean result = true;
				if (categoryId != null) {
					if (!input.getCategory().getPublicId().equals(categoryId)) {
						result = false;
					}
				}
				if (!Strings.isEmpty(title) && !Strings.isEmpty(input.getTitle())) {
					if (!input.getTitle().contains(title)) {
						result = false;
					}
				}

				if (!Strings.isEmpty(comment) && !Strings.isEmpty(input.getComment())) {
					if (!input.getComment().contains(comment)) {
						result = false;
					}
				}

				return result;
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

	public Link updateLink(Link linkToUpdate) {
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

	public Void deleteLink(final String linkPublicId) {
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
