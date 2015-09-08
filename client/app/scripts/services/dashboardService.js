'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:dashboardService
 * @description
 * # dashboardService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('dashboardService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getTiles = function () {
            var promise = $http.get(restServiceConfig.url + '/dashboard/tiles').then(function (response) {
                return response;
            });
            return promise;
        };

        this.updateTiles = function (tiles) {
            var promise = $http.put(restServiceConfig.url + '/dashboard/tiles', tiles).then(function (response) {
                return response;
            });
            return promise;
        };

        this.createTile = function (tile) {
            var promise = $http.post(restServiceConfig.url + '/dashboard/tiles', tile).then(function (response) {
                return response;
            });
            return promise;
        };

    }]);
