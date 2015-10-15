package pl.jsolve.goldenlink.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import pl.jsolve.goldenlink.infrastructure.InfrastructurePlaceholder

@Configuration
@EnableMongoRepositories(basePackageClasses = [InfrastructurePlaceholder])
class MongoConfiguration {}
