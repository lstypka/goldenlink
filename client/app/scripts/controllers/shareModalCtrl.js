'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:shareModalCtrl
 * @description
 * # shareModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('shareModalCtrl', ['$scope', 'friendsService', 'link', 'close', function ($scope, friendsService, link, close) {

    $scope.friends = [];

    $scope.numberOfSelectedItems = 0;

    var init = function () {
        friendsService.getFriends(function (foundFriends) {
            $scope.friends = foundFriends;
        });
    };

    $scope.toggleFriend = function(friend) {
        friend._isSelected = !friend._isSelected;
        if(friend._isSelected) {
            $scope.numberOfSelectedItems++;
        } else {
            $scope.numberOfSelectedItems--;
        }
    };

    $scope.close = function (result) {
        close(result, 500);
    };

    init();

}]);
