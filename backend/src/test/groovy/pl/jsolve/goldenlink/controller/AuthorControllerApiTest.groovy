package pl.jsolve.goldenlink.controller

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.entity.AuthorEntity
import pl.jsolve.goldenlink.repository.AuthorRepository
import pl.jsolve.goldenlink.test.ApiIntegrationSpecification

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
