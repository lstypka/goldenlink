package pl.jsolve.goldenlink.service.category
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.api.category.Category
import pl.jsolve.goldenlink.infrastructure.category.CategoryEntity
import pl.jsolve.goldenlink.infrastructure.category.CategoryRepository
import pl.jsolve.goldenlink.service.AutoMap

@Service
class CategoryService {

    @Autowired
    CategoryRepository categoryRepository

    @AutoMap(result = Category)
    def retrieveMainCategories() {
        categoryRepository.findMainCategories()
    }

    @AutoMap(result = Category)
    def searchCategories(String term) {
        categoryRepository.findByLabelContainingIgnoreCase(term)
    }

    @AutoMap(result = Category)
    def retrieveChildrenCategories(String parentId) {
        categoryRepository.findByParentId(parentId)
    }

    @AutoMap(argument = CategoryEntity, result = Category)
    def updateCategory(String publicId, def category) {
        throwExceptionWhenCategoryDoesNotExist publicId

        categoryRepository.save({
            category.id = publicId
            category
        }())
    }

    private void throwExceptionWhenCategoryDoesNotExist(String publicId) {
        if (!categoryRepository.exists(publicId)) {
            throw new CategoryNotFound(publicId)
        }
    }

    @AutoMap(argument = CategoryEntity, result = Category)
    def createSubcategory(String parentCategoryPublicId, def subcategory) {

        categoryRepository.save({
            def parentCategoryEntity = loadCategory parentCategoryPublicId
            parentCategoryEntity.hasChildren = true
            parentCategoryEntity
        }())

        categoryRepository.save({
            subcategory.id = ObjectId.get()
            subcategory.parentId = parentCategoryPublicId
            subcategory
        }())
    }

    def deleteCategory(String publicId) {
        def category = loadCategory publicId
        throwExceptionWhenAttemptedRootCategoryDeletion(category)

        def children = categoryRepository.findByParentId category.id
        children.forEach { categoryRepository.delete it }

        categoryRepository.delete category

        Optional.ofNullable(category.parentId).ifPresent({
            categoryRepository.save({
                def parent = categoryRepository.findOne category.parentId
                parent.hasChildren = categoryRepository.countByParentId parent.id
                parent
            }())
        })
    }

    private static throwExceptionWhenAttemptedRootCategoryDeletion(CategoryEntity category) {
        if (!category.parentId) {
            throw new RootCategoryDeletionAttempt(category.id);
        }
    }

    private loadCategory(String categoryId) {
        Optional
                .ofNullable(categoryRepository.findOne(categoryId))
                .orElseThrow({ throw new CategoryNotFound(categoryId) })
    }

    def getBreadcrumbs(String id) {
        def breadcrumbs = []
        def category = loadCategory id
        breadcrumbs << category
        while (category.parentId) {
            category = categoryRepository.findOne category.parentId
            breadcrumbs << category
        }
        breadcrumbs.reverse()
    }
}