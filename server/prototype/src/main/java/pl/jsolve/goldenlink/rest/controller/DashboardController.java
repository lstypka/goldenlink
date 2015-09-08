package pl.jsolve.goldenlink.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.DashboardTile;
import pl.jsolve.goldenlink.rest.service.DashboardTilesService;

@RestController
public class DashboardController {

    @Autowired
    private DashboardTilesService dashboardTilesService;

    @RequestMapping(value = "/dashboard/tiles", method = RequestMethod.GET)
    public List<DashboardTile> getTiles() {
        return dashboardTilesService.getTiles();
    }

    @RequestMapping(value = "/dashboard/tiles", method = RequestMethod.PUT)
    public List<DashboardTile> updateTiles(@RequestBody List<DashboardTile> tiles) {
        return dashboardTilesService.updateTiles(tiles);
    }

    @RequestMapping(value = "/dashboard/tiles", method = RequestMethod.POST)
    public DashboardTile createTile(@RequestBody DashboardTile tile) {
        return dashboardTilesService.createTile(tile);
    }
    

}
