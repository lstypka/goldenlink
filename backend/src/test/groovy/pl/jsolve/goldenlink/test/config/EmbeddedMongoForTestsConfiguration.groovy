package pl.jsolve.goldenlink.test.config

import com.mongodb.Mongo
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile('integration-test')
class EmbeddedMongoForTestsConfiguration {

    @Bean(destroyMethod = 'close')
    Mongo mongo() {
        new EmbeddedMongoBuilder()
                .bindIp('127.0.0.1')
                .build();
    }
}
