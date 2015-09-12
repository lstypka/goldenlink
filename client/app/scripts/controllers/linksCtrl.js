'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linksCtrl', ['$scope', '$routeParams', 'linkService', 'alertMessageService', '$sce', function ($scope, $routeParams, linkService, alertMessageService, $sce) {

        $scope.page = 0;
        $scope.resultsPerPage = 10;
        $scope.totalResults = 10000000000000;

        $scope.preloadVideo = 'metadata';

        $scope.links = [];

        var init = function () {
            $scope.nextPage(); // get first page
        };

        $scope.nextPage = function () {
            if(!$scope.hasNextPage() || $scope.isLoading) {
                return;
            }
            $scope.isLoading = true;
            linkService.getLinks($routeParams.category_id, $scope.page, $scope.resultsPerPage).then(function (response) {
                var results = response.data;
                $scope.totalResults = results.totalResults;
                var links = results.links;
                for (var i = 0; i < links.length; i++) {
                    $scope.links.push(links[i]);
                }
                $scope.isLoading = false;
            });
            $scope.page++;
        };

        $scope.copyFallback = function () {
            alertMessageService.showMessage("Wtyczka flash została zablokowana, co uniemożliwia automatyczne kopiowanie do schowka");
        };

        $scope.linkCopied = function (link) {
            alertMessageService.showMessage("Link '" + link + "' został poprawnie skopiowany do schowka");
        };

        $scope.hasNextPage = function() {
             return ($scope.page * $scope.resultsPerPage) < $scope.totalResults;
        };

        $scope.showLoadingPanel = function() {
            return $scope.isLoading || $scope.hasNextPage();
        };

        $scope.trustSrc = function(src) {
            window.console.log($sce);
            return src;// $sce.trustAsResourceUrl(src);
        };

        init();

    }]);
