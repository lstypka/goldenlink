package pl.jsolve.goldenlink.api

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.infrastructure.author.AuthorEntity
import pl.jsolve.goldenlink.infrastructure.author.AuthorRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static org.apache.http.HttpStatus.SC_OK

class AuthorControllerApiTest extends ApiIntegrationSpecification {

    @Autowired
    AuthorRepository authorRepository

    def 'Should retrieve all authors'() {
        given:
        authorRepository.save new AuthorEntity(name: 'Tommy Anderson')
        authorRepository.save new AuthorEntity(name: 'Louie Anderson')

        when:
        def result = api.get path: '/authors'

        then:
        result.status == SC_OK
        result.data*.name == ['Tommy Anderson', 'Louie Anderson']
    }
}
