'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:iconsCtrl
 * @description
 * # iconsCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('iconsCtrl', ['$scope', 'icons', 'close', function($scope, icons, close) {

    $scope.icons = icons;

    $scope.close = function(result) {
        close(result, 500);
    };

}]);
