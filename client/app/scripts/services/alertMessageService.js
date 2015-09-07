'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:alertMessageService
 * @description
 * # alertMessageService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('alertMessageService', ['$timeout', '$rootScope', 'restServiceConfig', function ($timeout, $rootScope, restServiceConfig) {

        this.showMessage = function (message) {
            $rootScope.$emit(restServiceConfig.events.SHOW_MESSAGE, message);
        };

    }]);
