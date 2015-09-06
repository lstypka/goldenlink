'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:alertMessageService
 * @description
 * # alertMessageService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('alertMessageService', ['$timeout', function ($timeout) {

        this.showMessage = function (scope, message) {
            scope.showMessage = true;
            scope.message = message;

            $timeout(function () {
                scope.message = null;
                scope.showMessage = false;
            }, 4000);
        };

    }]);
