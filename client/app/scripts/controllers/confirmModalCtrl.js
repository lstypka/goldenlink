'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:confirmModalCtrl
 * @description
 * # confirmModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('confirmModalCtrl', ['$scope', 'title', 'message', 'close', function($scope, title, message, close) {

    $scope.modalTitle = title;
    $scope.modalMessage = message;

    $scope.yes = function() {
        close(true, 500);
    };

    $scope.no = function() {
        close(false, 500);
    };


}]);
