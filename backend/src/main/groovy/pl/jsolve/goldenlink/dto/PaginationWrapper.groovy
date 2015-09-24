package pl.jsolve.goldenlink.dto

import groovy.transform.Canonical
import groovy.transform.Immutable

@Canonical
class PaginationWrapper {
    Integer page
    Integer resultsPerPage
    Integer totalResults
}
