package pl.jsolve.goldenlink.infrastructure.dashboard

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.jsolve.goldenlink.dto.DashboardTile
import pl.jsolve.oven.annotationdriven.annotation.Map
import pl.jsolve.oven.annotationdriven.annotation.MappableTo

@Document
@MappableTo(DashboardTile)
class DashboardTileEntity {

    @Id
    @Map(to = 'publicId')
    String id

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
