package pl.jsolve.goldenlink.service

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.dto.Category
import pl.jsolve.goldenlink.entity.CategoryEntity
import pl.jsolve.goldenlink.exceptions.CategoryNotFound
import pl.jsolve.goldenlink.exceptions.RootCategoryDeletionAttempt
import pl.jsolve.goldenlink.repository.CategoryRepository

import static java.util.stream.Collectors.toList
import static pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper.map

@Service
class CategoryService {

    @Autowired
    CategoryRepository categoryRepository

    def retrieveMainCategories() {
        categoryRepository.findMainCategories()
                .stream()
                .map { map it, Category }
                .collect toList()
    }

    def searchCategories(String term) {

        categoryRepository.findByLabelContainingIgnoreCase(term)
                .stream()
                .map { map it, Category }
                .collect toList()
    }

    def retrieveChildrenCategories(String parentId) {
        categoryRepository.findByParentId(parentId)
                .stream()
                .map { map it, Category }
                .collect toList()
    }

    def updateCategory(String publicId, Category category) {
        throwExceptionWhenCategoryDoesNotExist publicId

        def savedEntity = categoryRepository.save({
            def categoryEntity = map category, CategoryEntity
            categoryEntity.id = publicId
            categoryEntity
        }())
        map savedEntity, Category
    }

    private void throwExceptionWhenCategoryDoesNotExist(String publicId) {
        if (!categoryRepository.exists(publicId)) {
            throw new CategoryNotFound(publicId)
        }
    }

    def createSubcategory(String parentCategoryPublicId, Category subcategory) {

        categoryRepository.save({
            def parentCategoryEntity = loadCategory parentCategoryPublicId
            parentCategoryEntity.hasChildren = true
            parentCategoryEntity
        }())

        def savedEntity = categoryRepository.save({
            def subcategoryEntity = map subcategory, CategoryEntity
            subcategoryEntity.id = ObjectId.get()
            subcategoryEntity.parentId = parentCategoryPublicId
            subcategoryEntity
        }())
        map savedEntity, Category
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

    private void throwExceptionWhenAttemptedRootCategoryDeletion(CategoryEntity category) {
        if (!category.parentId) {
            throw new RootCategoryDeletionAttempt(category.id);
        }
    }

    private CategoryEntity loadCategory(String categoryId) {
        Optional
                .ofNullable(categoryRepository.findOne(categoryId))
                .orElseThrow({ throw new CategoryNotFound(categoryId) })
    }

    def getBreadcrumbs(String id) {
        def breadcrumbs = []
        def category = loadCategory id
        breadcrumbs << category
        while(category.parentId) {
            category = categoryRepository.findOne category.parentId
            breadcrumbs << category
        }
        breadcrumbs.reverse()
    }
}