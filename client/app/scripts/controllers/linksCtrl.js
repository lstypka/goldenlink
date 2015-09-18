'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linksCtrl', ['$scope', '$routeParams', 'ModalService', 'linkService', 'alertMessageService', 'settingsService', 'select2Date', 'select2Author', 'select2Tag', function ($scope, $routeParams, ModalService, linkService, alertMessageService, settingsService, select2Date, select2Author, select2Tag) {

        $scope.page = 0;
        $scope.resultsPerPage = 10;
        $scope.totalResults = 10000000000000;

        $scope.authorsSearchOptions = select2Author;

        $scope.tagsSearchOptions = select2Tag;

        $scope.dateSearchOptions = select2Date;

        $scope.searchParams = {
            title: null,
            comment: null,
            author: null,
            tag: null,
            date: null
        };

        $scope.links = [];

        var init = function () {
            $scope.nextPage(); // get first page of links
        };

        $scope.nextPage = function () {
            if (!$scope.hasNextPage() || $scope.isLoading) {
                return;
            }
            $scope.isLoading = true;
            linkService.getLinks($routeParams.category_id, $scope.page, $scope.resultsPerPage, $scope.searchParams).then(function (response) {
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

        $scope.search = function () {
            $scope.page = 0;
            $scope.links = [];
            $scope.nextPage();
        };

        $scope.hasNextPage = function () {
            return ($scope.page * $scope.resultsPerPage) < $scope.totalResults;
        };

        $scope.showLoadingPanel = function () {
            return $scope.isLoading || $scope.hasNextPage();
        };

        init();

    }]);
