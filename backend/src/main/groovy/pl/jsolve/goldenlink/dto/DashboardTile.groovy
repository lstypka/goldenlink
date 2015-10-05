package pl.jsolve.goldenlink.dto

import groovy.transform.Immutable

@Immutable
class DashboardTile {
    String publicId
    String label
    String colour
    Integer numberOfLinks
    String categoryGroup
    String icon
}
