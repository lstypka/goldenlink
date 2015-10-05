package pl.jsolve.goldenlink.test
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration

@WebAppConfiguration
@ActiveProfiles(['api-test', 'integration-test'])
@IntegrationTest('server.port:0')
abstract class ApiIntegrationSpecification extends DatabaseIntegrationSpecification {

    @Value('${local.server.port}')
    int port
    RESTClient api

    def setup() {
        api = new RESTClient('http://localhost:' + port)
    }
}
