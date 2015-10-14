package pl.jsolve.goldenlink.api

import groovy.transform.Immutable

@Immutable
class Failure {
    int statusCode
    String message
}
