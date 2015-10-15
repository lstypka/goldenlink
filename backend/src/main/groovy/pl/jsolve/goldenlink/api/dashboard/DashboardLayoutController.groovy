package pl.jsolve.goldenlink.api.dashboard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.jsolve.goldenlink.service.dashboard.DashboardLayoutService

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.PUT

@RestController
@RequestMapping('/dashboard/layout')
class DashboardLayoutController {

    @Autowired
    DashboardLayoutService dashboardLayoutService

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    def 'get dashboard layout'() {
        dashboardLayoutService.retrieveLayout()
    }

    @RequestMapping(method = PUT)
    @ResponseStatus(OK)
    def 'update dashboard layout'(@RequestBody DashboardLayout layout) {
        dashboardLayoutService.updateLayout layout
    }
}
