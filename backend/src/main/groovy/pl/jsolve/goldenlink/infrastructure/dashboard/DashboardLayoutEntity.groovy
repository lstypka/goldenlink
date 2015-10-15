package pl.jsolve.goldenlink.infrastructure.dashboard

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.jsolve.goldenlink.api.dashboard.DashboardLayout
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Document
@MappableTo(DashboardLayout)
class DashboardLayoutEntity {

    @Id
    @Map(to = 'publicId')
    String id

    @Map
    List<String> tiles
}
