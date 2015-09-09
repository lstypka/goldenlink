'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linksCtrl', ['$scope', '$routeParams', 'linkService', function ($scope, $routeParams, linkService) {

        $scope.page = 0;
        $scope.resultsPerPage = 25;

        $scope.links = [];

        var init = function () {
            $scope.nextPage(); // get first page
        };

        $scope.nextPage = function () {
            linkService.getLinks($routeParams.category_id, $scope.page, $scope.resultsPerPage).then(function (response) {
                var results = response.data;
                for (var i = 0; i < results.length; i++) {
                    $scope.links.push(results[i]);
                }
            });
            $scope.page++;
        };

        init();

    }]);
