'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:notificationService
 * @description
 * # notificationService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('notificationService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getNotifications = function (page, resultsPerPage) {
            var promise = $http.get(restServiceConfig.url + '/notifications/',
                {
                    params: {
                        page: page,
                        resultsPerPage: resultsPerPage
                    }
                }).then(function (response) {
                    return response;
                });
            return promise;
        };

        this.getNotification = function (publicId) {
            var promise = $http.get(restServiceConfig.url + '/notifications/' + publicId).then(function (response) {
                return response;
            });
            return promise;
        };

    }]);

