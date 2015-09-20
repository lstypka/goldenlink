'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:alertMessageService
 * @description
 * # alertMessageService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('alertMessageService', ['$timeout', '$rootScope', 'restServiceConfig', '$translate', function ($timeout, $rootScope, restServiceConfig, $translate) {

        this.showMessage = function (messageKey, params) {
            var message = $translate.instant(messageKey, params);
            $rootScope.$emit(restServiceConfig.events.SHOW_MESSAGE, message);
        };

    }]);
