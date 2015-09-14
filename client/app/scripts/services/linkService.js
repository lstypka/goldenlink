'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:linkService
 * @description
 * # linkService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('linkService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getLinks = function (categoryId, page, resultsPerPage, search) {
            window.console.log("SEARCH ", search);
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

    }]);

