package pl.jsolve.goldenlink.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.jsolve.goldenlink.entity.CategoryEntity

interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

}