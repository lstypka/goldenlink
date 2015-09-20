'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:linkService
 * @description
 * # linkService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('linkService', ['$http', 'restServiceConfig', 'moment', function ($http, restServiceConfig, moment) {

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

        this.updateLink = function (categoryPublicId, linkPublicId, link, successFn, errorFn) {

            if(link.expiryDate) {
                link.expiryDate = moment(link.expiryDate).utc().format("YYYY-MM-DD[T]HH:mm:ss[Z]");
            }
            if(link.expiryDate && link.expiryDate.trim().length === 0) {
                link.expiryDate = null;
            }
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

