package pl.jsolve.goldenlink.dto

import groovy.transform.Immutable

@Immutable
class Category {
    String publicId
    String label
    boolean hasChildren
    String categoryGroup
    String parentPublicId
    String icon
}
