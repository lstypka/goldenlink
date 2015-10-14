package pl.jsolve.goldenlink.api.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.jsolve.goldenlink.service.category.CategoryService

import static org.springframework.http.HttpStatus.*
import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping('/categories')
class CategoryController {

    @Autowired
    CategoryService categoryService

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    def 'get categories'() {
        categoryService.retrieveMainCategories()
    }

    @RequestMapping(method = GET, value = 'search')
    @ResponseStatus(OK)
    def 'search categories'(@RequestParam String term) {
        categoryService.searchCategories term
    }

    @RequestMapping(method = GET, value = '/{parentId}/children')
    @ResponseStatus(OK)
    def 'get categories children'(@PathVariable String parentId) {
        categoryService.retrieveChildrenCategories parentId
    }

    @RequestMapping(method = PUT, value = '/{id}')
    @ResponseStatus(OK)
    def 'update category'(@PathVariable String id, @RequestBody Category categoryToUpdate) {
        categoryService.updateCategory id, categoryToUpdate
    }

    @RequestMapping(method = POST, value = '/{parentId}')
    @ResponseStatus(CREATED)
    def 'create subcategory'(@PathVariable String parentId, @RequestBody Category categoryToAdd) {
        categoryService.createSubcategory parentId, categoryToAdd
    }

    @RequestMapping(method = DELETE, value = '/{id}')
    @ResponseStatus(NO_CONTENT)
    def 'delete category'(@PathVariable String id) {
        categoryService.deleteCategory id
    }

    @RequestMapping(method = GET, value = '/{id}/breadcrumbs')
    @ResponseStatus(OK)
    def 'get breadcrumbs'(@PathVariable String id) {
        categoryService.getBreadcrumbs id
    }
}
