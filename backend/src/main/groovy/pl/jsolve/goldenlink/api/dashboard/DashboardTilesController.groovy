package pl.jsolve.goldenlink.api.dashboard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.jsolve.goldenlink.service.dashboard.DashboardTilesService

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping('/dashboard/tiles')
class DashboardTilesController {

    @Autowired
    DashboardTilesService dashboardTilesService

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    def 'get tiles'() {
        dashboardTilesService.retrieveTiles()
    }

    @RequestMapping(method = PUT, value = '/{id}')
    @ResponseStatus(OK)
    def 'update tile'(@PathVariable String id, @RequestBody DashboardTile tile) {
        dashboardTilesService.updateTile id, tile
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    def 'create tile'(@RequestBody DashboardTile tile) {
        dashboardTilesService.createTile tile
    }
}
