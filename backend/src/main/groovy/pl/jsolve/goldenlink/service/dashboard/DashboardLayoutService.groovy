package pl.jsolve.goldenlink.service.dashboard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.api.dashboard.DashboardLayout
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutEntity
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutRepository
import pl.jsolve.goldenlink.service.AutoMap

@Service
class DashboardLayoutService {

    @Autowired
    DashboardLayoutRepository dashboardLayoutRepository

    @AutoMap(argument = DashboardLayoutEntity, result = DashboardLayout)
    def updateLayout(def dashboardLayout) {
        dashboardLayoutRepository.save({
            dashboardLayout.id = 'loggedUserId'
            dashboardLayout
        }())
    }

    @AutoMap(result = DashboardLayout)
    def retrieveLayout() {
        'user id passed here maybe?'
        dashboardLayoutRepository.findAll().first()
    }
}
