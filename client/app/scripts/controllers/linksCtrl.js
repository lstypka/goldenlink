'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:rootCtrl
 * @description
 * # rootCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linksCtrl', ['$scope', '$routeParams', 'linkService', 'alertMessageService', 'authorsService', function ($scope, $routeParams, linkService, alertMessageService, authorsService) {

        var authors = [];

        $scope.page = 0;
        $scope.resultsPerPage = 10;
        $scope.totalResults = 10000000000000;

        $scope.defaultCommentLimit = 100;

        $scope.settings = {
            videoPreload: 'none',     // possible values: 'auto', 'none', 'metadata'
            imagePreload: 'auto'          // possible values: 'auto', 'placeholder'
        };

        $scope.search = {
            title: null,
            comment: null,
            author: null,
            tag: null,
            date: null
        };

        $scope.links = [];

        var init = function () {
            $scope.nextPage(); // get first page of links

            authorsService.getAuthors(function (results) {
                for (var i = 0; i < results.length; i++) {
                    authors.push({ id: results[i].publicId, text: results[i].name});
                }
            });

        };

        $scope.nextPage = function () {
            if (!$scope.hasNextPage() || $scope.isLoading) {
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

        $scope.hasNextPage = function () {
            return ($scope.page * $scope.resultsPerPage) < $scope.totalResults;
        };

        $scope.showLoadingPanel = function () {
            return $scope.isLoading || $scope.hasNextPage();
        };

        $scope.showMore = function (link) {
            link.commentLimit = link.comment.length;
        };

        $scope.showMoreVisible = function (link) {
            return link.comment.length > $scope.commentLengthLimit(link);
        };

        $scope.showLess = function (link) {
            link.commentLimit = $scope.defaultCommentLimit;
        };

        $scope.showLessVisible = function (link) {
            return $scope.commentLengthLimit(link) > $scope.defaultCommentLimit;
        };

        $scope.commentLengthLimit = function (link) {
            return link.commentLimit || $scope.defaultCommentLimit;
        };

        $scope.tags = [
            {id: 1, text: 'Java'},
            {id: 2, text: 'Jquery'},
            {id: 3, text: 'AngularJs'},
            {id: 4, text: 'Jsolve'},
            {id: 5, text: 'SweetenerSweetener'},
            {id: 6, text: 'Oven'}
        ];

        $scope.authorsSearchOptions = {
            placeholder: 'Dowolny autor',
            containerCssClass: 'select2-container',
            formatNoMatches: function () {
                return 'Brak pasujących wyników';
            },
            allowClear: true,
            query: function (query) {
                var data = {
                    results: []
                };
                for (var i = 0; i < authors.length; i++) {
                    if (authors[i].text.indexOf(query.term) > -1) {
                        data.results.push(authors[i]);
                    }
                }
                query.callback(data);
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };

        $scope.tagsSearchOptions = {
            placeholder: 'Dowolne',
            containerCssClass: 'select2-container',
            allowClear: true,
            formatNoMatches: function () {
                return 'Brak pasujących wyników';
            },
            query: function (query) {
                var data = {
                    results: []
                };
                for (var i = 0; i < $scope.tags.length; i++) {
                    if ($scope.tags[i].text.indexOf(query.term) > -1) {
                        data.results.push($scope.tags[i]);
                    }
                }
                query.callback(data);
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };

        $scope.dateSearchOptions = {
            placeholder: 'Od początku',
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'withoutTimeLimit', text: 'Od początku'},
                        {id: 'pastWeek', text: 'Ostatni tydzień'},
                        {id: 'pastMonth', text: 'Ostatni miesiąc'},
                        {id: 'past3Months', text: 'Ostatnie 3 miesiące'},
                        {id: 'past6Months', text: 'Ostatnich 6 miesięcy'},
                        {id: 'pastYear', text: 'Ostatni rok'},
                        {id: 'past2Years', text: 'Ostatnie 2 lata'}
                    ]
                };
                query.callback(data);
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };
        init();

    }]);
