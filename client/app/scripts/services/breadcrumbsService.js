'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:breadcrumbsService
 * @description
 * # breadcrumbsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('breadcrumbsService', ['$http', '$routeParams', function ($http, $routeParams) {

        this.getBreadcrumbs = function () {

            var promise = $http.get('http://localhost:8080/categories/' + $routeParams.category_id + '/breadcrumbs').then(function (response) {
                return response;
            });
            return promise;
        };

    }]);
