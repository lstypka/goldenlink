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

        var panels = ['panel-blue','panel-red','panel-green','panel-orange','panel-yellow', 'panel-lavender', 'panel-olivedrab', 'panel-khaki'];

        $scope.dragControlListeners = {
           /* accept: function (sourceItemHandleScope, destSortableScope) {return true;},
            itemMoved: function (event) {},
                orderChanged: function(event) {},
                    containment: '#board'*/
                };

        var init = function() {
               $scope.items = [];
            for(var i = 0; i < 25; i++) {
                $scope.items.push({id: i, index:i, drag: true, nrOfLinks: Math.floor((Math.random() * 100) + 1), label : 'Zdjęcuia Nr. ' + (i +1) + ' ' + guid(), panel : panels[Math.floor((Math.random() * panels.length))]});
            }

        };

        function guid() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
            }

            return s4() + s4()+ s4()+ s4()+ s4() + s4() + s4()+ s4()+ s4()+ s4() + ' '  + s4() + s4()+ s4()+ s4()+ s4() ;
        }

        $scope.dropCallback = function(event, ui, item) {
                                         var fromIndex = $scope.items[item.index].index;
            var toIndex = item.index;

            window.console.log("From ",  fromIndex);
            window.console.log("To ",   toIndex);

            arraymove($scope.items, fromIndex, toIndex+1);
            rewriteIndexes();
        };


        function arraymove(arr, fromIndex, toIndex) {
            var element = arr[fromIndex];
            arr.splice(fromIndex, 1);
            arr.splice(toIndex, 0, element);
        }

        function rewriteIndexes() {
          for(var i =0; i < $scope.items.length; i++) {
              $scope.items[i].index = i;
          }
        }

        init();
  }]);
