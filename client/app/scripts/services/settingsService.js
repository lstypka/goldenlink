'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:settingsService
 * @description
 * # settingsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('settingsService', [ function () {

        this.settings = {
            videoPreload: 'none',     // possible values: 'auto', 'none', 'metadata'
            imagePreload: 'placeholder'          // possible values: 'auto', 'placeholder'
        };


    }]);
