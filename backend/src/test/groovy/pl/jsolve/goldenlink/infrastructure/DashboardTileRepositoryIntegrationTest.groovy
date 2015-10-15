package pl.jsolve.goldenlink.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
import pl.jsolve.goldenlink.test.specs.DatabaseIntegrationSpecification

class DashboardTileRepositoryIntegrationTest extends DatabaseIntegrationSpecification {

    @Autowired
    DashboardTileRepository dashboardTileRepository

    def 'Should save dashboard tile'() {
        given:
        def tileEntity = new DashboardTileEntity(
                label: 'Macaroni',
                colour: 'yellow'
        )

        when:
        def result = dashboardTileRepository.save tileEntity


        then:
        result == tileEntity
    }

    def 'Should find all tiles'() {
        given:
        dashboardTileRepository.save([
                new DashboardTileEntity(
                        label: 'Macaroni',
                        colour: 'yellow'
                ),
                new DashboardTileEntity(
                        label: 'Spaghetti',
                        colour: 'red'
                )
        ])

        when:
        def result = dashboardTileRepository.findAll()

        then:
        result*.label == ['Macaroni', 'Spaghetti']
    }
}
