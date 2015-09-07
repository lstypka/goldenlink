'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:iconsCtrl
 * @description
 * # iconsCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('iconsCtrl', ['$scope', '$rootScope', 'restServiceConfig', 'icons', 'close', function ($scope, $rootScope, restServiceConfig, icons, close) {


    var page = 0;
    var resultsPerPage = 60;
    var offset = 60;

    $scope.icons = icons.slice(0, offset);

    var init = function () {
        $rootScope.$on(restServiceConfig.events.ICONS_MODAL_NEXT_PAGE, function () {
            $scope.nextPage();
        });
    };

    $scope.nextPage = function () {
        $scope.isLoading = true;
        var iconsToAdd = icons.slice(offset + (page * resultsPerPage), offset + ((page + 1) * resultsPerPage));
        for (var i = 0; i < iconsToAdd.length; i++) {
            $scope.icons.push(iconsToAdd[i]);
        }
        page++;
        $scope.isLoading = false;
    };

    $scope.close = function (result) {
        close(result, 500);
    };

    init();

}]);
