'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:friendsService
 * @description
 * # friendsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('friendsService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getFriends = function (successFn) {
            $http.get(restServiceConfig.url + '/friends').then(function (response) {
                successFn(response.data);
            });

        };
    }]);
