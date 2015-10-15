package pl.jsolve.goldenlink.infrastructure.friend

import org.springframework.data.mongodb.core.mapping.Document
import pl.jsolve.goldenlink.api.friend.Friend
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Document
@MappableTo(Friend)
class FriendEntity {

    @Map(to = 'publicId')
    String id

    @Map
    String name

    @Map
    String thumbnail

    @Map
    String sex

    @Map
    Integer numberOfSharedLinks

    @Map
    Boolean isGroup
}
