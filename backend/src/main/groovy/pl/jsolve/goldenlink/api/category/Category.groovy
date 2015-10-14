package pl.jsolve.goldenlink.api.category

import groovy.transform.Immutable
import pl.jsolve.goldenlink.infrastructure.category.CategoryEntity
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Immutable
@MappableTo(CategoryEntity)
class Category {
    @Map(to = 'id')
    String publicId

    @Map
    String label

    @Map
    boolean hasChildren

    @Map
    String categoryGroup

    @Map(to = 'parentId')
    String parentPublicId

    @Map
    String icon
}
