package pl.jsolve.goldenlink.api

import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.api.dashboard.DashboardTile
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
import pl.jsolve.goldenlink.test.specs.ApiIntegrationSpecification

import static javax.servlet.http.HttpServletResponse.SC_CREATED
import static javax.servlet.http.HttpServletResponse.SC_OK
import static org.apache.http.HttpStatus.SC_NOT_FOUND

class DashboardTilesControllerApiTest extends ApiIntegrationSpecification {

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

    def 'Should update dashboard tile'() {
        given:
        def tileEntity = dashboardRepository.save new DashboardTileEntity(
                label: 'Louie tile',
                colour: 'red',
                numberOfLinks: 2,
                categoryGroup: 'Life with Louie',
                icon: "Louie's face"
        )

        def tile = new DashboardTile(
                label: 'Louie tile',
                colour: 'red',
                numberOfLinks: 2,
                categoryGroup: 'Life with Louie',
                icon: "Louie's face"
        )

        when:
        def result = api.put path: "/dashboard/tiles/${tileEntity.id}", body: tile

        then:
        result.status == SC_OK
        result.data.publicId == tileEntity.id
        result.data.colour == tile.colour
    }

    def 'Should not update not existing tile'() {
        given:
        def unsavedTile = new DashboardTile(label: 'Louie tile')

        when:
        api.put path: "/dashboard/tiles/${unsavedTile.publicId}", body: unsavedTile

        then:
        def thrownException = thrown HttpResponseException
        thrownException.message == 'Not Found'
        thrownException.statusCode == SC_NOT_FOUND
    }

    def 'Should retrieve all dashboard tiles'() {
        given:
        dashboardRepository.save([
                new DashboardTileEntity(label: 'Andy tile'),
                new DashboardTileEntity(label: 'Louie tile')
        ])

        when:
        def result = api.get path: '/dashboard/tiles'

        then:
        result.status == SC_OK
        result.data*.label == ['Andy tile', 'Louie tile']
    }
}
