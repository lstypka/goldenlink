package pl.jsolve.goldenlink.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.jsolve.goldenlink.dto.Category
import pl.jsolve.goldenlink.service.CategoryService

import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class CategoryController {

    @Autowired
    CategoryService categoryService

    @RequestMapping(value = '/categories', method = GET)
    def getCategories() {
        println "/dashboard/tiles"
        categoryService.getMainCategories()
    }

    @RequestMapping(value = '/categories/search', method = GET)
    def searchCategories(@RequestParam String term) {
        categoryService.searchCategories term
    }

    @RequestMapping(value = '/categories/{parentPublicId}/children', method = GET)
    def getCategoriesChildren(@PathVariable String parentPublicId) {
        categoryService.getChildrenCategories parentPublicId
    }

    @RequestMapping(value = '/categories/{publicId}', method = PUT)
    def updateCategory(@PathVariable String publicId, @RequestBody Category categoryToUpdate) {
        categoryService.updateCategory publicId, categoryToUpdate
    }

    @RequestMapping(value = '/categories/{parentPublicId}', method = POST)
    def createSubcategory(@PathVariable String parentPublicId, @RequestBody Category categoryToAdd) {
        categoryService.createSubcategory parentPublicId, categoryToAdd
    }

    @RequestMapping(value = '/categories/{publicId}', method = DELETE)
    def deleteCategory(@PathVariable String publicId) {
        categoryService.deleteCategory publicId
    }

    @RequestMapping(value = '/categories/{parentPublicId}/breadcrumbs', method = GET)
    def getBreadcrumbs(@PathVariable String parentPublicId) {
        categoryService.getBreadcrumbs parentPublicId
    }
}
