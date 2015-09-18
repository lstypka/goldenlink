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

        $scope.defaultCommentLimit = 100;

        $scope.settings = settingsService.settings;

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

        $scope.editLink = function (link) {
            if (!link) {
                return;
            }

            ModalService.showModal({
                templateUrl: "views/partials/edit_link_modal.html",
                controller: "editLinkModalCtrl",
                inputs: {
                    link: angular.copy(link)
                }
            }).then(function (modal) {
                    modal.element.modal();
                    modal.close.then(function (result) {
                        if (result) {
                            if(result.operationType === 'edit') {
                                link.link = result.link.link;
                                link.title = result.link.title;
                                link.comment = result.link.comment;
                                link.tags = result.link.tags;
                                link.expiryDate = result.link.expiryDate;
                            }

                        }
                        if(result.operationType === 'changeCategory') {
                            $scope.search(); // load links once again
                        }
                        if(result.operationType === 'delete') {
                           for(var i=0 ; i< $scope.links.length; i++){
                               if($scope.links[i].publicId === result.link.publicId) {
                                   $scope.links.splice(i, 1);
                               }
                           }
                        }
                    });
                });
        };

        $scope.star = function (link) {
            link.isMarked = !link.isMarked;
            linkService.updateLink(link.category.publicId, link.publicId, link, function () {
                if (link.isMarked) {
                    alertMessageService.showMessage("Link '" + link.title + "' otrzymał gwiazdkę");
                } else {
                    alertMessageService.showMessage("Link '" + link.title + "' stracił gwiazdkę");
                }
            }, function () {
                alertMessageService.showMessage("Błąd podczas aktualizacji linku '" + link.title + "'");
            });
        };

        init();

    }]);
