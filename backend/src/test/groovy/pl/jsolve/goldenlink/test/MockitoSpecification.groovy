package pl.jsolve.goldenlink.test

import org.mockito.MockitoAnnotations
import spock.lang.Specification

abstract class MockitoSpecification extends Specification {

    def setup() {
        MockitoAnnotations.initMocks(this)
    }
}
