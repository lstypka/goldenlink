package pl.jsolve.goldenlink.service

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.jsolve.goldenlink.dto.Author
import pl.jsolve.goldenlink.entity.AuthorEntity
import pl.jsolve.goldenlink.repository.AuthorRepository
import spock.lang.Specification

import static org.mockito.BDDMockito.given

class AuthorServiceTest extends Specification {

    @Mock
    AuthorRepository mockedAuthorRepository

    @InjectMocks
    AuthorService authorService

    void setup() {
        MockitoAnnotations.initMocks(this)
    }

    def 'Should retrieve all authors'() {
        given:
        def ora = new AuthorEntity(id: '1stGenerated1dh4sh', name: 'Ora Anderson')
        def andy = new AuthorEntity(id: '2ndGenerated1dh4sh', name: 'Andy Anderson')
        given(mockedAuthorRepository.findAll()).willReturn([ora, andy])

        when:
        List<Author> authors = authorService.retrieveAll()

        then:
        authors*.name == [ora.name, andy.name]
        authors*.publicId == [ora.id, andy.id]
    }
}
