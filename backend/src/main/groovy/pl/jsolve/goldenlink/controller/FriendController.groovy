package pl.jsolve.goldenlink.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.dto.Friend

import static java.util.stream.Collectors.toList
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
class FriendController {

    @RequestMapping(value = '/friends', method = GET)
    def getFriends() {

        (0..50).stream().map {
            def isGroup = isGroup()
            new Friend(publicId: generateId(),
                    name: "Znajomy nr ${it + 1}",
                    thumbnail: thumbnail(),
                    sex: isGroup ? null : sex(),
                    numberOfSharedLinks: numberOfSharedLinks(),
                    isGroup: isGroup)
        }.collect toList()
    }

    static def generateId() {
        UUID.randomUUID().toString().substring 0, 32
    }

    static def thumbnail() {
        def random = Math.random() * 3
        if (random == 0)
            'https://avatars0.githubusercontent.com/u/1587788?v=3&s=460'
        else if (random == 1)
            'https://avatars3.githubusercontent.com/u/1387716?v=3&s=460'
        null
    }

    static def sex() {
        def random = Math.random() * 2
        (random == 0) ? 'M' : 'F'
    }

    static def isGroup() {
        def random = Math.random() * 10
        random < 3
    }

    static def numberOfSharedLinks() {
        Math.random() * 5000
    }
}
