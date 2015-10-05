package pl.jsolve.goldenlink.repository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.entity.AuthorEntity
import pl.jsolve.goldenlink.test.DatabaseIntegrationSpecification

class AuthorRepositoryIntegrationTest extends DatabaseIntegrationSpecification {

    @Autowired
    AuthorRepository authorRepository

    @Test
    def 'Should save an author: Louie Anderson'() {
        given:
        def louie = new AuthorEntity(name: 'Louie Anderson')

        when:
        def persistedLouie = authorRepository.save louie

        then:
        !persistedLouie.id.isEmpty()
        persistedLouie.name == louie.name
    }


    def 'Should retrieve author by id'() {
        given:
        def louie = authorRepository.save new AuthorEntity(name: 'Louie Anderson')
        def andy = authorRepository.save new AuthorEntity(name: 'Andy Anderson')

        when:
        def foundAuthor = authorRepository.findOne louie.id

        then:
        foundAuthor == louie
    }

    def 'Should retrieve all authors'() {
        given:
        def louie = authorRepository.save new AuthorEntity(name: 'Louie Anderson')
        def andy = authorRepository.save new AuthorEntity(name: 'Andy Anderson')

        when:
        def foundAuthor = authorRepository.findAll()

        then:
        foundAuthor.containsAll([andy, louie])
    }

    def 'Should delete Andy Anderson as an author'() {
        given:
        def louie = authorRepository.save new AuthorEntity(name: 'Louie Anderson')
        def andy = authorRepository.save new AuthorEntity(name: 'Andy Anderson')

        when:
        authorRepository.delete andy

        then:
        !authorRepository.findAll().contains(andy)
        authorRepository.findAll().contains(louie)
    }

    def "Should update author's name"() {
        given:
        def andy = authorRepository.save new AuthorEntity(name: 'Andee Anderson')

        when:
        andy.name = 'Andy Anderson'
        def updatedAndy = authorRepository.save andy

        then:
        updatedAndy.name == 'Andy Anderson'
    }
}
