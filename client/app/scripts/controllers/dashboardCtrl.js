'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('DashboardCtrl', ['$scope', '$timeout', '$translate', 'alertMessageService', 'dashboardService', function ($scope, $timeout, $translate, alertMessageService, dashboardService) {

        $scope.isDirty = false;
        $scope.backup = [];

        var init = function () {
            dashboardService.getTiles().then(function (data) {
                $scope.items = [];

                for (var i = 0; i < data.data.length; i++) {
                    var tile = data.data[i];
                    $scope.items.push({
                        publicId: tile.publicId,
                        index: i,
                        drag: true,
                        numberOfLinks: tile.numberOfLinks,
                        label: tile.label,
                        colour: tile.colour,
                        categoryGroup: tile.categoryGroup,
                        icon: tile.icon
                    });
                }

                $scope.backup = $scope.items.slice();
            });
        };

        $scope.dropCallback = function (event, ui, item) {
            var fromIndex = $scope.items[item.index].index;
            var toIndex = item.index;

            if (fromIndex > toIndex) {
                arraymove($scope.items, fromIndex, toIndex + 1);
            } else {
                arraymove($scope.items, fromIndex, toIndex - 1);
            }

            rewriteIndexes();
            $scope.isDirty = true;
        };

        function arraymove(arr, fromIndex, toIndex) {
            var element = arr[fromIndex];
            arr.splice(fromIndex, 1);
            arr.splice(toIndex, 0, element);
        }

        function rewriteIndexes() {
            for (var i = 0; i < $scope.items.length; i++) {
                $scope.items[i].index = i;
            }
        }

        $scope.deleteTile = function (tileToRemove) {
            $scope.items.splice(tileToRemove.index, 1);
            $scope.isDirty = true;
            rewriteIndexes();
        };

        $scope.save = function () {
            dashboardService.updateTiles($scope.items).then(function (response) {

                    window.console.log("RESPONSE", response);
                    $scope.isDirty = false;
                    $scope.message = $translate.instant("DASHBOARD_SAVE_MESSAGE");
                    $scope.isSaved = true;
                    $scope.backup = $scope.items.slice();
                    resetFlags();
            }, function() {
                    $scope.message = $translate.instant("DASHBOARD_SAVE_ERROR_MESSAGE");
                    $scope.items = $scope.backup.slice();
                    rewriteIndexes();
                    resetFlags();
            });
        };

        $scope.revert = function () {
            $scope.isDirty = false;
            $scope.message = $translate.instant("DASHBOARD_REVERT_MESSAGE");
            $scope.isReverted = true;
            $scope.items = $scope.backup.slice();
            rewriteIndexes();
            resetFlags();
        };

        var resetFlags = function () {
            alertMessageService.showMessage($scope.message);
            $scope.isSaved = false;
            $scope.isReverted = false;
            $scope.isDirty = false;
            $scope.message = "";
        };

        init();
    }]);
