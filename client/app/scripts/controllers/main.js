'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('MainCtrl', ['$scope', function ($scope) {

        $scope.dragControlListeners = {
            accept: function (sourceItemHandleScope, destSortableScope) {return boolean},
            itemMoved: function (event) {},
                orderChanged: function(event) {},
                    containment: '#board'
                };

        var init = function() {
               $scope.items = [];
            for(var i = 0; i < 10; i++) {
                $scope.items.push({id: i, label : 'Zdjęcuia Nr. ' + (i +1)})
            }
        };

        init();
  }]);
