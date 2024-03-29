'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:panelsCtrl
 * @description
 * # panelsCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('panelsCtrl', ['$scope', 'close', function ($scope, close) {


    $scope.panels = ["panel-blue", "panel-red", "panel-green", "panel-orange", "panel-yellow",
        "panel-lavender", "panel-olivedrab", "panel-khaki", "panel-purple", "panel-grey", "panel-blue-grey", "panel-pink", "panel-indigo", "panel-green-lighten", "panel-green-darken", "panel-lime"];

    var init = function () {

    };

    $scope.selectPanel = function (panel) {
        close(panel, 500);
    };

    init();

}]);
