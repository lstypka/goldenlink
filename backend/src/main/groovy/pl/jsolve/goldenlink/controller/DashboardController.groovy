package pl.jsolve.goldenlink.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.dto.DashboardTile
import pl.jsolve.goldenlink.service.DashboardTilesService

import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class DashboardController {

    @Autowired
    DashboardTilesService dashboardTilesService

    @RequestMapping(value = '/dashboard/tiles', method = GET)
    def getTiles() {
        dashboardTilesService.getTiles()
    }

    @RequestMapping(value = '/dashboard/tiles', method = PUT)
    def updateTiles(@RequestBody List<DashboardTile> tiles) {
        dashboardTilesService.updateTiles tiles
    }

    @RequestMapping(value = '/dashboard/tiles', method = POST)
    def createTile(@RequestBody DashboardTile tile) {
        dashboardTilesService.createTile tile
    }
}
