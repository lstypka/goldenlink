package pl.jsolve.goldenlink.api

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.api.dashboard.DashboardLayout
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutRepository
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static javax.servlet.http.HttpServletResponse.SC_OK

class DashboardLayoutControllerApiTest extends ApiIntegrationSpecification {

    @Autowired
    DashboardTileRepository dashboardTileRepository

    @Autowired
    DashboardLayoutRepository dashboardLayoutRepository

    def 'Should update tiles order on dashboard'() {
        given:
        def louie = dashboardTileRepository.save new DashboardTileEntity(label: 'Louie')
        def tommy = dashboardTileRepository.save new DashboardTileEntity(label: 'Tommy')
        def andy = dashboardTileRepository.save new DashboardTileEntity(label: 'Andy')
        def ora = dashboardTileRepository.save new DashboardTileEntity(label: 'Ora')
        def layout = new DashboardLayout(tiles: [louie.id, tommy.id, andy.id, ora.id])
        def savedLayout = dashboardLayoutRepository.save new DashboardLayoutEntity(tiles: [ora.id, andy.id])

        when:
        def result = api.put path: '/dashboard/layout', body: layout

        then:
        result.status == SC_OK
        result.data.tiles == layout.tiles
    }

    def 'Should retrieve dashboard layout'() {
        given:
        def layout = dashboardLayoutRepository.save new DashboardLayoutEntity(tiles: ['louieId', 'tommyId', 'andyId', 'oraId'])

        when:
        def result = api.get path: '/dashboard/layout'

        then:
        result.status == SC_OK
        result.data.tiles == layout.tiles
    }
}
