package pl.jsolve.goldenlink.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.dto.Author

import static java.util.stream.Collectors.toList
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
class AuthorController {

    @RequestMapping(value = '/authors', method = GET)
    def getFriends() {

        (0..50).stream()
                .map { new Author(generateId(), "Znajomy nr ${it + 1}") }
                .collect toList()
    }

    static def generateId() {
        UUID.randomUUID().toString().substring 0, 32
    }
}
