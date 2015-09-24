package pl.jsolve.goldenlink.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.jsolve.goldenlink.dto.Link
import pl.jsolve.goldenlink.service.LinkService

import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class LinkController {

    @Autowired
    LinkService linkService

    @RequestMapping(value = '/categories/{categoryId}/links', method = GET)
    def getLinks(
            @PathVariable String categoryId,
            @RequestParam Integer page,
            @RequestParam Integer resultsPerPage,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String date) {

        linkService.searchLinks categoryId, page, resultsPerPage, title,
                comment, null, tag, author, date, null, null
    }

    @RequestMapping(value = '/search', method = GET)
    def search(
            @RequestParam Integer page,
            @RequestParam Integer resultsPerPage,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String categoryPublicId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String expiryDate,
            @RequestParam(required = false) String marked) {

        linkService.searchLinks categoryPublicId, page, resultsPerPage,
                title, comment, type, tag, author, date, expiryDate, marked
    }

    @RequestMapping(value = '/categories/{categoryPublicId}/links', method = POST)
    def createLink(
            @PathVariable String categoryPublicId,
            @RequestBody Link linkToAdd) {
        linkService.addLink linkToAdd
    }

    @RequestMapping(value = '/categories/{categoryPublicId}/links/{linkPublicId}', method = PUT)
    def updateLink(
            @PathVariable String categoryPublicId,
            @PathVariable String linkPublicId,
            @RequestBody Link linkToUpdate) {

        linkService.updateLink linkToUpdate
    }

    @RequestMapping(value = '/categories/{categoryPublicId}/links/{linkPublicId}', method = DELETE)
    def deleteLink(
            @PathVariable String categoryPublicId,
            @PathVariable String linkPublicId) {

        linkService.deleteLink linkPublicId
    }
}
