package pl.jsolve.goldenlink.api.dashboard

import groovy.transform.Immutable
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardTileEntity
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Immutable
@MappableTo(DashboardTileEntity)
class DashboardTile {

    @Map(to = 'id')
    String publicId

    @Map
    String label

    @Map
    String colour

    @Map
    Integer numberOfLinks

    @Map
    String categoryGroup

    @Map
    String icon
}
