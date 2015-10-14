package pl.jsolve.goldenlink.controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.dto.DashboardTile
import pl.jsolve.goldenlink.service.dashboard.DashboardTilesService

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping('/dashboard/tiles')
class DashboardController {

    @Autowired
    DashboardTilesService dashboardTilesService

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    def getTiles() {
        dashboardTilesService.getTiles()
    }

    @RequestMapping(method = PUT)
    @ResponseStatus(OK)
    def updateTiles(@RequestBody DashboardTile tile) {
        dashboardTilesService.updateTile tile
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    def createTile(@RequestBody DashboardTile tile) {
        dashboardTilesService.createTile tile
    }
}
