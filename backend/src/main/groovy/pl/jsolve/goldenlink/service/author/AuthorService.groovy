package pl.jsolve.goldenlink.service.author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.api.author.Author
import pl.jsolve.goldenlink.infrastructure.author.AuthorRepository
import pl.jsolve.goldenlink.service.AutoMap

@Service
class AuthorService {

    @Autowired
    AuthorRepository authorRepository

    @AutoMap(result = Author)
    def retrieveAll() {
        authorRepository.findAll()
    }
}