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
        $scope.resultsPerPage = 10;
        $scope.totalResults = 10000000000000;

        $scope.defaultCommentLimit = 100;

        $scope.settings = {
            videoPreload : 'auto',     // possible values: 'auto', 'none', 'metadata'
            imagePreload : 'auto'          // possible values: 'auto', 'placeholder'
        };

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

        $scope.showMore = function(link) {
            link.commentLimit = link.comment.length;
        };

        $scope.showMoreVisible = function(link) {
            return link.comment.length > $scope.commentLengthLimit(link);
        };

        $scope.showLess = function(link) {
            link.commentLimit = $scope.defaultCommentLimit;
        };

        $scope.showLessVisible = function(link) {
            return $scope.commentLengthLimit(link) > $scope.defaultCommentLimit;
        };

        $scope.commentLengthLimit = function(link) {
           return link.commentLimit || $scope.defaultCommentLimit;
        };

        init();

    }]);
