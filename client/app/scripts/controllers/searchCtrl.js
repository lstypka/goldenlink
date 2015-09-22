'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:searchCtrl
 * @description
 * # searchCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('searchCtrl', ['$scope', '$routeParams', 'ModalService', 'linkService', 'alertMessageService', 'settingsService', 'select2Date', 'select2Author', 'select2Tag', 'select2CategoryType', 'select2Star', 'select2ExpiryDate', 'select2Category', function ($scope, $routeParams, ModalService, linkService, alertMessageService, settingsService, select2Date, select2Author, select2Tag, select2CategoryType, select2Star, select2ExpiryDate, select2Category) {

        $scope.page = 0;
        $scope.resultsPerPage = 10;
        $scope.totalResults = 10000000000000;

        $scope.authorsSearchOptions = select2Author;

        $scope.tagsSearchOptions = select2Tag;

        $scope.dateSearchOptions = select2Date;

        $scope.categoryTypeSearchOptions = select2CategoryType;

        $scope.starSearchOptions = select2Star;

        $scope.expiryDateSearchOptions = select2ExpiryDate;

        $scope.categorySearchOptions = select2Category;

        $scope.searchParams = {
            title: null,
            comment: null,
            categoryId : null,
            author: null,
            tag: null,
            date: null,
            expiryDate : null,
            categoryType : null,
            star : null
        };

        $scope.links = [];

        var init = function () {
            $scope.nextPage(); // get first page of links
        };

        var resetSearchParams = function() {
            $scope.page = 0;
            $scope.resultsPerPage = 10;
            $scope.totalResults = 10000000000000;
            $scope.links = [];
        };

        $scope.nextPage = function () {
            if (!$scope.hasNextPage() || $scope.isLoading) {
                return;
            }
            $scope.isLoading = true;
            linkService.search($scope.page, $scope.resultsPerPage, $scope.searchParams).then(function (response) {
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
            resetSearchParams();

            $scope.nextPage();
        };

        $scope.hasNextPage = function () {
            return ($scope.page * $scope.resultsPerPage) < $scope.totalResults;
        };

        $scope.showLoadingPanel = function () {
            return $scope.isLoading || $scope.hasNextPage();
        };

        $scope.showNoResultsPanel = function() {
            return !$scope.isLoading && $scope.links.length === 0;
        };

        init();

    }]);
