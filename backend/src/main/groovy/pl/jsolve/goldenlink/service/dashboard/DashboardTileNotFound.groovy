package pl.jsolve.goldenlink.service.dashboard

import pl.jsolve.goldenlink.service.EntityNotFoundException

class DashboardTileNotFound extends EntityNotFoundException {
    DashboardTileNotFound(String id) {
        super("Cannot find dashboard tile with id ${id}")
    }
}
