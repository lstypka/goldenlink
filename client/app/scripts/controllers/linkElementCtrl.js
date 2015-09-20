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
            alertMessageService.showMessage("BLOCKED_FLASH_PLUGIN_MESSAGE");
        };

        $scope.linkCopied = function (link) {
            alertMessageService.showMessage("LINK_COPIED_TO_CLIPBOARD", {label: link });
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
                    alertMessageService.showMessage("LINK_GAINED_STAR_MESSAGE", {label: link.title });
                } else {
                    alertMessageService.showMessage("LINK_LOST_STAR_MESSAGE", {label: link.title });
                }
            }, function () {
                alertMessageService.showMessage("LINK_UPDATED_MESSAGE", {label: link.title});
            });
        };

        $scope.share = function (link) {
            if ($scope.editMode) {
                return;
            }
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
