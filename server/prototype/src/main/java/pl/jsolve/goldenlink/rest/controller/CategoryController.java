package pl.jsolve.goldenlink.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.service.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> getCategories() {
        return categoryService.getMainCategories();
    }

    @RequestMapping(value = "/categories/search", method = RequestMethod.GET)
    public List<Category> searchCategories(@RequestParam("term") String term) {
        return categoryService.searchCategories(term);
    }
    
    @RequestMapping(value = "/categories/{parentPublicId}/children", method = RequestMethod.GET)
    public List<Category> getCategoriesChildren(@PathVariable("parentPublicId") final String parentPublicId)
            throws InterruptedException {
        return categoryService.getChildrenCategories(parentPublicId);
    }

    @RequestMapping(value = "/categories/{publicId}", method = RequestMethod.PUT)
    public Category updateCategory(@PathVariable("publicId") final String publicId,
            @RequestBody Category categoryToUpdate) {
        return categoryService.updateCategory(publicId, categoryToUpdate);
    }

    @RequestMapping(value = "/categories/{parentPublicId}", method = RequestMethod.POST)
    public Category createSubcategory(@PathVariable("parentPublicId") final String parentPublicId,
            @RequestBody Category categoryToAdd) {
        return categoryService.createSubcategory(parentPublicId, categoryToAdd);
    }

    @RequestMapping(value = "/categories/{publicId}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("publicId") final String publicId) {
        categoryService.deleteCategory(publicId);
        ;
    }

    @RequestMapping(value = "/categories/{parentPublicId}/breadcrumbs", method = RequestMethod.GET)
    public List<Category> getBreadcrumbs(@PathVariable("parentPublicId") String parentPublicId)
            throws InterruptedException {
        return categoryService.getBreadcrumbs(parentPublicId);
    }
    
    

}
