package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Category;

import com.google.common.collect.Lists;

@RestController
public class CategoryController {

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> getCategories() {
        List<Category> categories = Lists.newArrayList();

        categories.add(new Category("1", "Linki", true, null));
        categories.add(new Category("2", "Zdjęcia", true, null));
        categories.add(new Category("3", "Filmy", true, null));
        categories.add(new Category("4", "Youtube", true, null));
        categories.add(new Category("5", "Notatki", true, null));

        return categories;
    }

    @RequestMapping(value = "/categories/{categoryId}/children", method = RequestMethod.GET)
    public List<Category> getCategoriesChildren(@PathVariable("categoryId") String categoryId) {
        List<Category> categories = Lists.newArrayList();

        int numberOfChildren = (int)(Math.random() * 10) + 1;
        
        for (int i = 0; i < numberOfChildren; i++) {
            String label = UUID.randomUUID().toString().substring(0, 10);
            categories.add(new Category(label, label, true, null));
        }
        return categories;
    }

}
