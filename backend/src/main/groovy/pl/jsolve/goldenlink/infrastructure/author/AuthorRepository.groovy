package pl.jsolve.goldenlink.infrastructure.author
import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorRepository extends MongoRepository<AuthorEntity, String> {
}