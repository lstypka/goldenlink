package pl.jsolve.goldenlink.controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.jsolve.goldenlink.dto.Category
import pl.jsolve.goldenlink.service.CategoryService

import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping('/categories')
class CategoryController {

    @Autowired
    CategoryService categoryService

    @RequestMapping(method = GET)
    def getCategories() {
        categoryService.retrieveMainCategories()
    }

    @RequestMapping(value = 'search', method = GET)
    def searchCategories(@RequestParam String term) {
        categoryService.searchCategories term
    }

    @RequestMapping(value = '/{parentPublicId}/children', method = GET)
    def getCategoriesChildren(@PathVariable String parentPublicId) {
        categoryService.retrieveChildrenCategories parentPublicId
    }

    @RequestMapping(value = '/{publicId}', method = PUT)
    def updateCategory(@PathVariable String publicId, @RequestBody Category categoryToUpdate) {
        categoryService.updateCategory publicId, categoryToUpdate
    }

    @RequestMapping(value = '/{parentPublicId}', method = POST)
    def createSubcategory(@PathVariable String parentPublicId, @RequestBody Category categoryToAdd) {
        categoryService.createSubcategory parentPublicId, categoryToAdd
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = '/{publicId}', method = DELETE)
    def deleteCategory(@PathVariable String publicId) {
        categoryService.deleteCategory publicId
    }

    @RequestMapping(value = '/{id}/breadcrumbs', method = GET)
    def getBreadcrumbs(@PathVariable String id) {
        categoryService.getBreadcrumbs id
    }
}
