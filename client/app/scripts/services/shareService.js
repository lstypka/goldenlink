'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:shareService
 * @description
 * # shareService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('shareService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.shareLink = function (shareLink, successFn, errorFn) {
            $http.post(restServiceConfig.url + '/share', shareLink).then(function (response) {
                successFn(response.data);
            }, errorFn);
        };

    }]);

