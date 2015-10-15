package pl.jsolve.goldenlink.test.specs
import org.springframework.boot.test.SpringApplicationConfiguration
import pl.jsolve.goldenlink.config.Application
import spock.lang.Specification

@SpringApplicationConfiguration(classes = [Application])
abstract class SpringIntegrationSpecification extends Specification {}