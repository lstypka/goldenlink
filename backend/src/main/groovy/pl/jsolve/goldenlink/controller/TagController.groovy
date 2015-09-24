package pl.jsolve.goldenlink.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.dto.Friend
import pl.jsolve.goldenlink.dto.Tag

import static java.util.stream.Collectors.toList
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
class TagController {

    @RequestMapping(value = '/tags', method = GET)
    def getTags() {
        (0..50).stream()
                .map { new Tag(generateId(), "Tag nr ${it + 1}") }
                .collect toList()
    }

    static def generateId() {
        UUID.randomUUID().toString().substring 0, 32
    }
}
