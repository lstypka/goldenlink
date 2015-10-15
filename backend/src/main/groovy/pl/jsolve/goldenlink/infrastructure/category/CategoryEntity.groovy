package pl.jsolve.goldenlink.infrastructure.category

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.jsolve.goldenlink.api.category.Category
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Document
@EqualsAndHashCode
@MappableTo(Category)
class CategoryEntity {

    @Id
    @Map(to = 'publicId')
    String id

    @Map
    String label

    @Map
    boolean hasChildren

    @Map
    String categoryGroup

    @Map(to = 'parentPublicId')
    String parentId

    @Map
    String icon
}