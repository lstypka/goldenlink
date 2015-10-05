package pl.jsolve.goldenlink.dto

import groovy.transform.Immutable

@Immutable
class Pagination {
    Integer page
    Integer resultsPerPage
    Integer totalResults
}
