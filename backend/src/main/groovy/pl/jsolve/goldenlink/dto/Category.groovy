package pl.jsolve.goldenlink.dto

import groovy.transform.Canonical
import groovy.transform.Immutable
import groovy.transform.ToString

@Canonical
class Category {
    String publicId
    String label
    boolean hasChildren
    String categoryGroup
    String parentPublicId
    String icon
}
