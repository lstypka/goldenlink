package pl.jsolve.goldenlink.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import pl.jsolve.goldenlink.entity.CategoryEntity

interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

    @Query("{ 'parentId' : null }")
    List<CategoryEntity> findMainCategories()

    List<CategoryEntity> findByLabelContainingIgnoreCase(String label)

    List<CategoryEntity> findByParentId(String parentId)

    int countByParentId(String parentId)
}