package pl.jsolve.goldenlink.service.dashboard
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.api.dashboard.DashboardTile
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileRepository
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

    @AutoMap(argument = DashboardTileEntity, result = DashboardTile)
    def updateTile(String id, def dashboardTile) {
        throwExceptionWhenTileDoesNotExist id
        dashboardRepository.save({
            dashboardTile.id = id
            dashboardTile
        }())
    }

    private void throwExceptionWhenTileDoesNotExist(String id) {
        if (!dashboardRepository.exists(id)) {
            throw new DashboardTileNotFound(id)
        }
    }

    @AutoMap(result = DashboardTile)
    def retrieveTiles() {
        dashboardRepository.findAll()
    }
}
