package pl.jsolve.goldenlink.api
import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.dto.DashboardTile
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static javax.servlet.http.HttpServletResponse.SC_CREATED

class DashboardControllerApiTest extends ApiIntegrationSpecification {

    @Autowired
    DashboardTileRepository dashboardRepository

    def 'Should create dashboard tile'() {
        given:
        def tile = new DashboardTile(
                label: 'Louie tile',
                colour: 'red',
                numberOfLinks: 2,
                categoryGroup: 'Life with Louie',
                icon: "Louie's face"
        )

        when:
        def result = api.post path: '/dashboard/tiles', body: tile

        then:
        result.status == SC_CREATED
        !result.data.publicId.isEmpty()
        result.data.label == tile.label
        result.data.colour == tile.colour
        result.data.numberOfLinks == tile.numberOfLinks
        result.data.categoryGroup == tile.categoryGroup
        result.data.icon == tile.icon
    }
}
