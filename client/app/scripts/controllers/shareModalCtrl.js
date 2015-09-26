'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:shareModalCtrl
 * @description
 * # shareModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('shareModalCtrl', ['$scope', 'friendsService', 'shareService', 'alertMessageService', 'link', 'close', function ($scope, friendsService, shareService, alertMessageService, link, close) {

    $scope.friends = [];

    $scope.numberOfSelectedItems = 0;

    var init = function () {
        friendsService.getFriends(function (foundFriends) {
            $scope.friends = foundFriends;
        });
    };

    $scope.toggleFriend = function (friend) {
        friend._isSelected = !friend._isSelected;
        if (friend._isSelected) {
            $scope.numberOfSelectedItems++;
        } else {
            $scope.numberOfSelectedItems--;
        }
    };

    var getSelectedFriends = function () {
        var selectedFriends = [];
        for (var index in $scope.friends) {
            if ($scope.friends[index]._isSelected) {
                selectedFriends.push($scope.friends[index]);
            }
        }
        return selectedFriends;
    };

    $scope.share = function () {
        window.console.log("SHARE");
        var selectedFriends = getSelectedFriends();
        var shareLink = { link: link, friends: selectedFriends};

        shareService.shareLink(shareLink, function () {
            alertMessageService.showMessage("LINK_SHARED_CORRECTLY_MESSAGE", { label: link.link});
            close(link, 500);
        }, function () {
            alertMessageService.showMessage("LINK_SHARED_ERROR_MESSAGE", { label: link.link});
            close(link, 500);
        });
    };

    $scope.close = function (result) {
        close(result, 500);
    };

    init();

}]);
