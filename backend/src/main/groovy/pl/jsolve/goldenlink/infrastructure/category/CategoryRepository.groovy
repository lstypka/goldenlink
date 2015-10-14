package pl.jsolve.goldenlink.infrastructure.category

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

    @Query("{ 'parentId' : null }")
    List<CategoryEntity> findMainCategories()

    List<CategoryEntity> findByLabelContainingIgnoreCase(String label)

    List<CategoryEntity> findByParentId(String parentId)

    int countByParentId(String parentId)
}