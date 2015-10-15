package pl.jsolve.goldenlink.api.friend

import groovy.transform.Immutable

@Immutable
class Friend {
    String publicId
    String name
    String thumbnail
    String sex
    Integer numberOfSharedLinks
    Boolean isGroup
}