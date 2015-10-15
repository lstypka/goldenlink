package pl.jsolve.goldenlink.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

class DashboardLayoutRepositoryIntegrationTest extends ApiIntegrationSpecification {

    @Autowired
    DashboardLayoutRepository dashboardLayoutRepository

    def 'Should save a dashboard layout'() {
        given:
        def layout = new DashboardLayoutEntity(
                tiles: ['a', 'b', 'c']
        )

        when:
        def result = dashboardLayoutRepository.save layout

        then:
        result == layout
    }

    def 'Should find a dashboard layout'() {
        given:
        def layout = new DashboardLayoutEntity(
                id: 'layoutId',
                tiles: ['a', 'b', 'c']
        )
        dashboardLayoutRepository.save layout

        when:
        def result = dashboardLayoutRepository.findOne layout.id

        then:
        result.id == layout.id
        result.tiles == layout.tiles
    }
}
