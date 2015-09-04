'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('DashboardCtrl', ['$scope', '$timeout', function ($scope, $timeout) {

        var panels = ['panel-blue', 'panel-red', 'panel-green', 'panel-orange', 'panel-yellow', 'panel-lavender', 'panel-olivedrab', 'panel-khaki'];

        $scope.isDirty = false;
        $scope.backup = [];

        $scope.dragControlListeners = {
            /* accept: function (sourceItemHandleScope, destSortableScope) {return true;},
             itemMoved: function (event) {},
             orderChanged: function(event) {},
             containment: '#board'*/
        };

        var init = function () {
            $scope.items = [];
            for (var i = 0; i < 25; i++) {
                $scope.items.push({id: i, index: i, drag: true, nrOfLinks: Math.floor((Math.random() * 100) + 1), label: 'Zdjęcuia Nr. ' + (i + 1) + ' ' + guid(), panel: panels[Math.floor((Math.random() * panels.length))]});
            }

            $scope.backup = $scope.items.slice();
        };

        function guid() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
            }

            return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + ' ' + s4() + s4() + s4() + s4() + s4();
        }

        $scope.dropCallback = function (event, ui, item) {
            var fromIndex = $scope.items[item.index].index;
            var toIndex = item.index;

            window.console.log("From ", fromIndex);
            window.console.log("To ", toIndex);

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

        $scope.save = function () {
            $scope.isDirty = false;
            $scope.message= "Zapisano";
            $scope.isSaved = true;
            $scope.backup = $scope.items.slice();
            $timeout(function(){ $scope.isSaved = false;}, 1000);
            window.console.log("SAVED");
            resetFlags();
        };

        $scope.revert = function () {
            $scope.isDirty = false;
            $scope.message = "Przywrócono";
            $scope.isReverted = true;
            $scope.items = $scope.backup.slice();
            rewriteIndexes();
            window.console.log("REVERTED");
            resetFlags();
        };

        var resetFlags = function() {
            $timeout(function(){
                $scope.isSaved = false;
                $scope.isReverted = false;
                $scope.isDirty = false;
                $scope.message = "";
            }, 1000);
        };

        init();
    }]);
