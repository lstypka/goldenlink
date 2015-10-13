package pl.jsolve.goldenlink.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.dto.Author
import pl.jsolve.goldenlink.repository.AuthorRepository

import static java.util.stream.Collectors.toList
import static pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper.map

@Service
class AuthorService {

    @Autowired
    AuthorRepository authorRepository

    def retrieveAll() {
        authorRepository.findAll()
                .stream()
                .map { map it, Author }
                .collect toList()
    }
}