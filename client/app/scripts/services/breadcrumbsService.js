'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:breadcrumbsService
 * @description
 * # breadcrumbsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('breadcrumbsService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getBreadcrumbs = function (categoryId) {

            var promise = $http.get(restServiceConfig.url + '/categories/' + categoryId + '/breadcrumbs').then(function (response) {
                return response;
            });
            return promise;
        };

    }]);
