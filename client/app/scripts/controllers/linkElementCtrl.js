'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:linkElementCtrl
 * @description
 * # linkElementCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('linkElementCtrl', ['$scope', '$routeParams', 'ModalService', 'linkService', 'alertMessageService', 'settingsService', function ($scope, $routeParams, ModalService, linkService, alertMessageService, settingsService) {

        $scope.defaultCommentLimit = 100;

        $scope.settings = settingsService.settings;

        var init = function () {
        };

        $scope.copyFallback = function () {
            alertMessageService.showMessage("Wtyczka flash została zablokowana, co uniemożliwia automatyczne kopiowanie do schowka");
        };

        $scope.linkCopied = function (link) {
            alertMessageService.showMessage("Link '" + link + "' został poprawnie skopiowany do schowka");
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
            if (!link || $scope.editMode) {
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
                            if (result.operationType === 'edit') {
                                link.link = result.link.link;
                                link.title = result.link.title;
                                link.comment = result.link.comment;
                                link.tags = result.link.tags;
                                link.expiryDate = result.link.expiryDate;
                            }

                        }
                        if (result.operationType === 'changeCategory') {
                            $scope.search(); // load links once again
                        }
                        if (result.operationType === 'delete') {
                            for (var i = 0; i < $scope.links.length; i++) {
                                if ($scope.links[i].publicId === result.link.publicId) {
                                    $scope.links.splice(i, 1);
                                }
                            }
                        }
                    });
                });
        };

        $scope.star = function (link) {
            if ($scope.editMode) {
                return;
            }
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

        $scope.share = function (link) {
            ModalService.showModal({
                templateUrl: "views/partials/share_modal.html",
                controller: "shareModalCtrl",
                inputs: {
                    link: angular.copy(link)
                }
            }).then(function (modal) {
                    modal.element.modal();
                    modal.close.then(function (result) {
                          window.console.log("RESULT ", result);
                    });
                });
        };

        init();

    }]);
