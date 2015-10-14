package pl.jsolve.goldenlink.service.dashboard

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.dto.DashboardTile
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.goldenlink.service.AutoMap

@Service
class DashboardTilesService {

    @Autowired
    DashboardTileRepository dashboardRepository

    @AutoMap(argument = DashboardTileEntity, result = DashboardTile)
    def createTile(def dashboardTile) {
        dashboardRepository.save({
            dashboardTile.id = ObjectId.get()
            dashboardTile
        }())
    }




}
