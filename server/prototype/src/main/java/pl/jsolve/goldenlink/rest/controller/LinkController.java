package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.goldenlink.rest.service.CategoryService;

@RestController
public class LinkController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories/{categoryId}/links", method = RequestMethod.GET)
    public List<Link> getTiles(@PathVariable("categoryId") String categoryId, @RequestParam("page") Integer page,
            @RequestParam("resultsPerPage") Integer resultsPerPage) {
        return generateLinks(categoryId, page, resultsPerPage);
    }

    private List<Link> generateLinks(String categoryId, Integer page, Integer resultsPerPage) {
        List<Link> links = Lists.newArrayList();
        Category category = categoryService.findCategory(categoryId);
        List<Tag> tags = Lists.newArrayList(new Tag(generateId(), "JS"), new Tag(generateId(), "Select2"), new Tag(
                generateId(), "AngularJS"), new Tag(generateId(), "JS"), new Tag(generateId(), "Select2"), new Tag(
                generateId(), "AngularJS"), new Tag(generateId(), "JS"), new Tag(generateId(), "Select2"), new Tag(
                generateId(), "AngularJS"), new Tag(generateId(), "JS"), new Tag(generateId(), "Select2"), new Tag(
                generateId(), "AngularJS"));
        Author author = new Author(generateId(), "Lukasz Stypka");

        for (int i = 0; i < resultsPerPage; i++) {
            links.add(new Link(generateId(), "https://kevin-brown.com/#/home",
                    "Strona domowa kolesia od select2 Link nr " + ((page * resultsPerPage) + i + 1), getComment(),
                    category, tags, LocalDateTime.now(), LocalDateTime.now().plusDays(1).plusHours(3).plusMinutes(7),
                    author, false));
        }
        return links;
    }

    private String getComment() {
        return "";
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 32);
    }

}
