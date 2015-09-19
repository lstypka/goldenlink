'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('rootCtrl', ['$scope', '$translate', function ($scope, $translate) {

        var init = function () {

        };

        $scope.changeLanguage = function (key) {
            $translate.use(key);
            window.console.log("ZMIENILEM JEZYK na ", key);
        };

        init();

    }]);
