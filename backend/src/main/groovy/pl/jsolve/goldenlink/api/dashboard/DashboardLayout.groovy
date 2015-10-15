package pl.jsolve.goldenlink.api.dashboard

import groovy.transform.Immutable
import pl.jsolve.goldenlink.infrastructure.dashboard.DashboardLayoutEntity
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Immutable
@MappableTo(DashboardLayoutEntity)
class DashboardLayout {

    @Map(to = 'id')
    String publicId

    @Map
    List<String> tiles
}
