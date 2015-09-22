'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:linkService
 * @description
 * # linkService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('linkService', ['$http', 'restServiceConfig', 'timeService', function ($http, restServiceConfig, timeService) {

        this.getLinks = function (categoryId, page, resultsPerPage, search) {
            var promise = $http.get(restServiceConfig.url + '/categories/' + categoryId + '/links',
                {
                    params: {
                        page: page,
                        resultsPerPage: resultsPerPage,
                        title: search.title,
                        comment: search.comment,
                        author: search.author,
                        tag: search.tag,
                        date: search.date
                    }
                }).then(function (response) {
                    return response;
                });
            return promise;
        };

        this.search = function (page, resultsPerPage, search) {
            var promise = $http.get(restServiceConfig.url + '/search',
                {
                    params: {
                        page: page,
                        resultsPerPage: resultsPerPage,
                        title: search.title,
                        comment: search.comment,
                        author: search.author,
                        tag: search.tag,
                        date: search.date,
                        expiryDate: search.expiryDate,
                        type: search.type,
                        marked: search.marked,
                        categoryPublicId: search.categoryId
                    }
                }).then(function (response) {
                    return response;
                });
            return promise;
        };

        this.addLink = function (categoryPublicId, link, successFn, errorFn) {
            link.expiryDate = timeService.formatDate(link.expiryDate);

            $http.post(restServiceConfig.url + '/categories/' + categoryPublicId + '/links', link).then(function (response) {
                successFn(response.data);
            }, errorFn);
        };

        this.updateLink = function (categoryPublicId, linkPublicId, link, successFn, errorFn) {
            link.expiryDate = timeService.formatDate(link.expiryDate);

            $http.put(restServiceConfig.url + '/categories/' + categoryPublicId + '/links/' + linkPublicId, link).then(function (response) {
                successFn(response.data);
            }, errorFn);
        };

        this.deleteLink = function (categoryPublicId, linkPublicId, successFn, errorFn) {
            $http.delete(restServiceConfig.url + '/categories/' + categoryPublicId + '/links/' + linkPublicId).then(function (response) {
                successFn(response);
            }, errorFn);
        };

    }]);

