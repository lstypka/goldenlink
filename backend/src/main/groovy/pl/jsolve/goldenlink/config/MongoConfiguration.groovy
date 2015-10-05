package pl.jsolve.goldenlink.config
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import pl.jsolve.goldenlink.repository.Repositories

@Configuration
@EnableMongoRepositories(basePackageClasses = [Repositories])
class MongoConfiguration {}
