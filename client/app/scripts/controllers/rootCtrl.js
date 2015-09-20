'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('rootCtrl', ['$rootScope', '$scope', '$location', '$translate', 'restServiceConfig', function ($rootScope, $scope, $location, $translate, restServiceConfig) {

        var init = function () {

        };

        $scope.changeLanguage = function (key) {
            $translate.use(key);
            $location.path("");
            $rootScope.$emit(restServiceConfig.events.LANGUAGE_CHANGED, key);
        };

        init();

    }]);
