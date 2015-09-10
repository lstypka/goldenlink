'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linksCtrl', ['$scope', '$routeParams', 'linkService', 'alertMessageService', function ($scope, $routeParams, linkService, alertMessageService) {

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

        $scope.copyFallback = function () {
            alertMessageService.showMessage("Wtyczka flash została zablokowana, co uniemożliwia automatyczne kopiowanie do schowka");
        };

        $scope.linkCopied = function (link) {
            alertMessageService.showMessage("Link '" + link + "' został poprawnie skopiowany do schowka");
        };

        init();

    }]);
