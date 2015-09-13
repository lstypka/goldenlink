'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:friendsService
 * @description
 * # friendsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('authorsService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        var cachedAuthors = null;

        this.getAuthors = function (successFn) {
            if (this.hasCachedAuthors()) {
                successFn(this.cachedAuthors());
            }
            $http.get(restServiceConfig.url + '/authors').then(function (response) {
                cachedAuthors = response.data;
                successFn(response.data);
            });

        };

        this.cachedAuthors = function () {
            return cachedAuthors;
        };

        this.hasCachedAuthors = function () {
            return cachedAuthors !== null;
        };

        this.setAuthors = function (authors) {
            this.cachedAuthors = authors;
        };

    }]);
