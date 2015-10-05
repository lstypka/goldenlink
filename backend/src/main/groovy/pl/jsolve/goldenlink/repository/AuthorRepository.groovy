package pl.jsolve.goldenlink.repository
import org.springframework.data.mongodb.repository.MongoRepository
import pl.jsolve.goldenlink.entity.AuthorEntity

interface AuthorRepository extends MongoRepository<AuthorEntity, String> {
}