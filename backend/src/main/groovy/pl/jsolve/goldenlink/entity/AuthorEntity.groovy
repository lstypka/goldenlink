package pl.jsolve.goldenlink.entity

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.jsolve.goldenlink.dto.Author
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Document
@EqualsAndHashCode
@MappableTo(Author)
class AuthorEntity {

    @Id
    @Map(to = 'publicId')
    String id

    @Map
    String name
}
