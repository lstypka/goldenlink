package pl.jsolve.goldenlink.test.specs

import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles('integration-test')
abstract class DatabaseIntegrationSpecification extends SpringIntegrationSpecification {
    @Autowired
    MongoClient mongoClient

    def cleanup() {
        mongoClient.dropDatabase('test')
    }
}
