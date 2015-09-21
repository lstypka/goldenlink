'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:authService
 * @description
 * # authService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('authService', [ function () {

        this.isLogged = function () {
            return true;
        };

        this.getLoggedUser = function () {
            return {
                publicId: 'abc1234',
                name: 'Łukasz Stypka',
                roles: ['ADMIN', 'USER']
            };
        };


    }]);
