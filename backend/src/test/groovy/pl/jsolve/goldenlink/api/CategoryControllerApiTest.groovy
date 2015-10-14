package pl.jsolve.goldenlink.api

import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.api.category.Category
import pl.jsolve.goldenlink.infrastructure.category.CategoryEntity
import pl.jsolve.goldenlink.infrastructure.category.CategoryRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static org.apache.http.HttpStatus.*

class CategoryControllerApiTest extends ApiIntegrationSpecification {

    @Autowired
    CategoryRepository categoryRepository

    def 'Should get breadcrumbs'() {
        given: 'relationship: root -> a -> b -> c'
        def root = categoryRepository.save new CategoryEntity(
                label: 'root'
        )
        def a = categoryRepository.save new CategoryEntity(
                label: 'a',
                parentId: root.id
        )
        def b = categoryRepository.save new CategoryEntity(
                label: 'b',
                parentId: a.id
        )
        def c = categoryRepository.save new CategoryEntity(
                label: 'c',
                parentId: b.id
        )

        when:
        def result = api.get path: "/categories/${c.id}/breadcrumbs"

        then:
        result.data*.label == ['root', 'a', 'b', 'c']
    }

    def 'Should not delete root category'() {
        given:
        def rootCategory = categoryRepository.save new CategoryEntity(
                label: "Louie's Macaroni",
                hasChildren: true,
                parentId: null
        )
        when:
        api.delete path: "/categories/${rootCategory.id}"

        then:
        def thrownException = thrown HttpResponseException
        thrownException.message == 'Bad Request'
        thrownException.statusCode == SC_BAD_REQUEST
    }

    def 'Should update hasChildren property while deleting a child'() {
        given:
        def parentCategory = categoryRepository.save new CategoryEntity(
                label: "Louie's Macaroni",
                hasChildren: true,
        )
        def childCategory = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                parentId: parentCategory.id
        )
        when:
        def result = api.delete path: "/categories/${childCategory.id}"

        then:
        result.status == SC_NO_CONTENT
        !categoryRepository.exists(childCategory.id)
        !categoryRepository.findOne(parentCategory.id).hasChildren
    }

    def 'Should update hasChildren property while deleting a child 2'() {
        given:
        def parentCategory = categoryRepository.save new CategoryEntity(
                label: "Louie's Macaroni",
                hasChildren: true,
        )
        def childCategory = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                parentId: parentCategory.id
        )
        def secondChildCategory = categoryRepository.save new CategoryEntity(
                label: "Andy's green Macaroni",
                hasChildren: false,
                parentId: parentCategory.id
        )
        when:
        def result = api.delete path: "/categories/${childCategory.id}"

        then:
        result.status == SC_NO_CONTENT
        !categoryRepository.exists(childCategory.id)
        categoryRepository.exists(secondChildCategory.id)
        categoryRepository.findOne(parentCategory.id).hasChildren
    }

    def 'Should delete category with its children'() {
        given:
        def root = categoryRepository.save new CategoryEntity(
                label: 'root'
        )
        def categoryToDelete = categoryRepository.save new CategoryEntity(
                label: "Louie's Macaroni",
                hasChildren: true,
                parentId: root.id
        )
        def childOfCategoryToDelete = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                parentId: categoryToDelete.id
        )
        when:
        def result = api.delete path: "/categories/${categoryToDelete.id}"

        then:
        result.status == SC_NO_CONTENT
        !categoryRepository.exists(categoryToDelete.id)
        !categoryRepository.exists(childOfCategoryToDelete.id)
    }

    def 'Should delete category'() {
        given:
        def root = categoryRepository.save new CategoryEntity(
                label: 'root'
        )
        def categoryToDelete = categoryRepository.save new CategoryEntity(
                label: "Louie's Macaroni",
                hasChildren: false,
                categoryGroup: 'Fish named Pepper',
                icon: 'pepper',
                parentId: root.id
        )
        categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                categoryGroup: 'Fish named Pepper',
                icon: 'pepper',
                parentId: 'root'
        )
        when:
        def result = api.delete path: "/categories/${categoryToDelete.id}"

        then:
        result.status == SC_NO_CONTENT
        !categoryRepository.exists(categoryToDelete.id)
    }

    def 'Should not delete not existing category'() {
        given:
        def notExistingCategory = 'notExistingCategory'

        when:
        api.delete path: "/categories/${notExistingCategory}"

        then:
        def thrownException = thrown HttpResponseException
        thrownException.message == 'Not Found'
        thrownException.statusCode == SC_NOT_FOUND
    }

    def 'Should update category'() {
        given:
        def category = categoryRepository.save new CategoryEntity(
                label: "Andy's cakes",
                hasChildren: false,
                parentId: 'root'
        )

        when:
        category.label = 'cheesecake'
        def result = api.put path: "/categories/${category.id}", body: category

        then:
        result.status == SC_OK
        result.data.publicId == category.id
        result.data.label == category.label
    }

    def 'Should not update not existing category'() {
        given:
        def notExistingCategory = 'notExistingCategory'
        def category = categoryRepository.save new CategoryEntity(
                label: "Andy's cakes",
                hasChildren: false
        )

        when:
        api.put path: "/categories/${notExistingCategory}", body: category

        then:
        def thrownException = thrown HttpResponseException
        thrownException.message == 'Not Found'
        thrownException.statusCode == SC_NOT_FOUND
    }

    def 'Should create sub-category'() {
        given:
        def parent = categoryRepository.save new CategoryEntity(
                id: 'categoryId',
                label: "Andy's cakes",
                hasChildren: false,
                parentId: 'root'
        )
        def category = new Category(
                label: "Louie's Macaroni"
        )

        when:
        def result = api.post path: "/categories/${parent.id}", body: category

        then:
        result.data.publicId != null
        result.data.label == category.label
        result.data.parentPublicId == parent.id
        result.status == SC_CREATED
        categoryRepository.findOne(parent.id).hasChildren
    }

    def 'Should not create sub-category of not existing parent category'() {
        given:
        def notExistingCategoryId = 'notExistingCategory'
        def category = new Category(
                label: "Louie's Macaroni"
        )

        when:
        api.post path: "/categories/${notExistingCategoryId}", body: category

        then:
        def thrownException = thrown HttpResponseException
        thrownException.message == 'Not Found'
        thrownException.statusCode == SC_NOT_FOUND
    }

    def 'Should retrieve children categories'() {
        given:
        def andysCakes = categoryRepository.save new CategoryEntity(
                label: "Andy's cakes",
                hasChildren: true,
                categoryGroup: 'Fish named Pepper',
                icon: 'pepper'
        )
        categoryRepository.save([
                new CategoryEntity(
                        label: "Andy's cheesecake",
                        hasChildren: false,
                        categoryGroup: 'Fish named Pepper',
                        icon: 'pepper',
                        parentId: andysCakes.id
                ),
                new CategoryEntity(
                        label: "Louie's Macaroni",
                        hasChildren: false,
                        categoryGroup: 'Fish named Pepper',
                        icon: 'pepper',
                        parentId: andysCakes.id
                )
        ])
        when:
        def result = api.get path: "/categories/${andysCakes.id}/children"

        then:
        result.data*.label == ["Andy's cheesecake", "Louie's Macaroni"]
    }


    def 'Should retrieve all categories'() {
        given:
        categoryRepository.save([
                new CategoryEntity(label: 'Anderson'),
                new CategoryEntity(label: 'Jensen')
        ])

        when:
        def result = api.get path: '/categories'

        then:
        result.status == SC_OK
        result.data*.label == ['Anderson', 'Jensen']
    }

    def 'Should search for category by its label'() {
        given:
        categoryRepository.save([
                new CategoryEntity(label: 'Anderson'),
                new CategoryEntity(label: 'Jensen')
        ])

        when:
        def result = api.get path: '/categories/search', query: ['term': 'jensen']

        then:
        result.status == SC_OK
        result.data*.label == ['Jensen']
    }

    def 'Should not find a category searching by other than label property'() {
        given:
        categoryRepository.save([
                new CategoryEntity(id: 'jensen'),
                new CategoryEntity(categoryGroup: 'Jensen')
        ])

        when:
        def result = api.get path: '/categories/search', query: ['term': 'jensen']

        then:
        result.status == SC_OK
        result.data*.label.isEmpty()
    }
}
