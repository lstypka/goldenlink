package pl.jsolve.goldenlink.dto

import groovy.transform.Canonical

@Canonical
class Links extends PaginationWrapper {

    List<Link> links

    Links(List<Link> links,
          Integer page,
          Integer resultsPerPage,
          Integer totalResults) {
        super(page, resultsPerPage, totalResults)
    }
}
