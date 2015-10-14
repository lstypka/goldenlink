package pl.jsolve.goldenlink.api.author

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.service.author.AuthorService

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
class AuthorController {

    @Autowired
    AuthorService authorService

    @RequestMapping(method = GET, value = '/authors')
    @ResponseStatus(OK)
    def getAuthors() {
        authorService.retrieveAll()
    }
}
