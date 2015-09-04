'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:breadcrumbsService
 * @description
 * # breadcrumbsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('breadcrumbsService', ['$http', '$routeParams', 'restServiceConfig', function ($http, $routeParams, restServiceConfig) {

        this.getBreadcrumbs = function () {

            var promise = $http.get(restServiceConfig.url + '/categories/' + $routeParams.category_id + '/breadcrumbs').then(function (response) {
                return response;
            });
            return promise;
        };

    }]);
