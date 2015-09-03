package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Category;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@RestController
public class CategoryController {

    private List<Category> categories;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> getCategories() {
        return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

            @Override
            public boolean apply(Category input) {
                return input.getParentPublicId() == null;
            }

        }));
    }

    @RequestMapping(value = "/categories/{parentPublicId}/children", method = RequestMethod.GET)
    public List<Category> getCategoriesChildren(@PathVariable("parentPublicId") final String parentPublicId)
            throws InterruptedException {
        return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

            @Override
            public boolean apply(Category input) {
                return input.getParentPublicId() != null && input.getParentPublicId().equals(parentPublicId);
            }

        }));
    }

    @RequestMapping(value = "/categories/{parentPublicId}/breadcrumbs", method = RequestMethod.GET)
    public List<Category> getBreadcrumbs(@PathVariable("parentPublicId") String parentPublicId)
            throws InterruptedException {

        List<Category> breadcrumbs = Lists.newArrayList();
        while (parentPublicId != null) {
            for (Category category : categories) {
                if (category.getPublicId().equals(parentPublicId)) {
                    breadcrumbs.add(0, category);
                    parentPublicId = category.getParentPublicId();
                    break;
                }
            }
            System.out.println("Nie znalazlem zadnego :(");
        }

        return breadcrumbs;
    }

    @PostConstruct
    void setUp() {
        categories = Lists.newArrayList();

        // main categories
        categories.add(new Category(generateId(), "Linki", true, null, "links"));
        categories.add(new Category(generateId(), "Zdjęcia", true, null, "photos"));
        categories.add(new Category(generateId(), "Filmy", true, null, "videos"));
        categories.add(new Category(generateId(), "Youtube", true, null, "youtube"));
        categories.add(new Category(generateId(), "Notatki", true, null, "notes"));

        // inner categories
        for (int i = 0; i < 5; i++) {
            int generateChildren = generateChildren(categories.get(i).getPublicId(), 0, categories.get(i)
                    .getCategoryGroup());
            if (generateChildren == 0) {
                categories.get(i).setHasChildren(false);
            }
        }
    }

    private int generateChildren(String parentId, int level, String categoryGroup) {
        if (level > 6) {
            return 0;
        }
        int numberOfChildren = randomNumberOfChildren();
        for (int j = 0; j < numberOfChildren; j++) {

            String publicId = generateId();
            int children = generateChildren(publicId, level + 1, categoryGroup);
            categories.add(new Category(publicId, publicId, children > 0, parentId, categoryGroup));
        }
        return numberOfChildren;
    }

    private int randomNumberOfChildren() {
        return (int) (Math.random() * 10);
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 32);
    }

}
