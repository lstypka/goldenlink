package pl.jsolve.goldenlink.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.service.AuthorService

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
class AuthorController {

    @Autowired
    AuthorService authorService

    @RequestMapping(value = '/authors', method = GET)
    def getAuthors() {
        authorService.retrieveAll()
    }
}
