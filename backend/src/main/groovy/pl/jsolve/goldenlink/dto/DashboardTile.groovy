package pl.jsolve.goldenlink.dto

import groovy.transform.Canonical
import groovy.transform.Immutable

@Canonical
class DashboardTile {
    String publicId
    String label
    String colour
    Integer numberOfLinks
    String categoryGroup
    String icon

    @Deprecated // will be deleted after full groovy migration
    DashboardTile(String publicId, String label, String colour, Integer numberOfLinks, String categoryGroup, String icon) {
        this.publicId = publicId
        this.label = label
        this.colour = colour
        this.numberOfLinks = numberOfLinks
        this.categoryGroup = categoryGroup
        this.icon = icon
    }
}
