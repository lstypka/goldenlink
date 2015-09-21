'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:utilService
 * @description
 * # utilService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('utilService', [function () {

        this.isBlank = function (value) {
            return !!(value || '').match(/^\s*$/);
        };

    }]);
