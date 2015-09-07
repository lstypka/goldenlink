'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:navbarCtrl
 * @description
 * # navbarCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('navbarCtrl', ['$rootScope', '$scope', '$timeout', 'restServiceConfig', function ($rootScope, $scope, $timeout, restServiceConfig) {

        var init = function () {
            $rootScope.$on(restServiceConfig.events.SHOW_MESSAGE, function (event, message) {
                $scope.showMessage = true;
                $scope.message = message;

                $timeout(function () {
                    $scope.message = null;
                    $scope.showMessage = false;
                }, 5000);
            });
        };

        init();

    }]);
