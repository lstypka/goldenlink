'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:editLinkModalCtrl
 * @description
 * # editLinkModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('editLinkModalCtrl', ['$scope', '$timeout', 'restServiceConfig', 'alertMessageService', 'linkService', 'settingsService', 'categoryService', 'moment', 'link', 'close', function ($scope, $timeout, restServiceConfig, alertMessageService, linkService, settingsService, categoryService, moment, link, close) {

    $scope.showEdit = true;
    $scope.showPreview = false;
    $scope.mainCategories = [];

    $scope.settings = settingsService.settings;

    $scope.defaultCommentLimit = 100;

    var init = function () {
        $scope.link = link;
        $scope.categoryPrefix = '' + Math.random();
        categoryService.getCategories().then(function (categories) {
            $scope.mainCategories = categories.data;
            window.console.log("Wczytalem kategorie ", $scope.mainCategories);
        });
    };


    init();

    $scope.close = function (result) {
        close(
            {
                operationType: 'close',
                link: result
            }, 500);
    };

    $scope.updateLink = function () {
        linkService.updateLink($scope.link.category.publicId, $scope.link.publicId, $scope.link, function (response) {
            alertMessageService.showMessage("Link '" + response.title + "' został zaktualizowany");
            close({
                operationType: 'edit',
                link: response
            }, 500);
        }, function () {
            alertMessageService.showMessage("Wystąpił błąd podczas aktualizowania linku '" + $scope.link.title + "'");
        });
    };

    $scope.tagKeypress = function (link, event) {
        if (event.which === 13) {
            var foundDuplicate = false;
            for (var i = 0; i < link.tags.length; i++) {
                if (link.tags[i].label.trim() === $scope.tag.trim()) {
                    foundDuplicate = true;
                }
            }
            if (!foundDuplicate) {
                if (!($scope.tag.length === 0 || !$scope.tag.trim())) {
                    link.tags.unshift({id: null, label: $scope.tag.trim() });
                }
            }
            $scope.tag = '';
        }
    };

    $scope.deleteTag = function (link, tag) {
        for (var i = 0; i < link.tags.length; i++) {
            if (link.tags[i].label.trim() === tag.label.trim()) {
                link.tags.splice(i, 1);
            }
        }
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

    $scope.loadChildren = function (category) {
        if (category._opened) {
            category._opened = false;
        } else {
            category._opened = true;
        }

        category.isLoadingChildren = true;

        if (!category._childrenLoaded) {

            $timeout(
                function () {
                    return categoryService.getChildren(category.publicId).then(function (children) {
                        category.children = children.data;
                        category._childrenLoaded = true;
                        category.isLoadingChildren = false;
                    });
                }, 50);
        } else {
            category.isLoadingChildren = false;
        }
    };

    $scope.selectCategory = function (selectedCategory) {
        if ($scope.selectedCategory) {
            $scope.selectedCategory._isSelected = false;
        }
        selectedCategory._isSelected = true;
        $scope.selectedCategory = selectedCategory;
    };

    $scope.changeCategory = function () {
        var previousCategory = $scope.link.category;
        $scope.link.category = $scope.selectedCategory;
        linkService.updateLink(previousCategory.publicId, $scope.link.publicId, $scope.link, function (response) {
            alertMessageService.showMessage("Link '" + response.title + "' został przypisany do kategorii '" + response.category.label + "'");
            close({
                operationType: 'changeCategory',
                link: response
            }, 500);
        }, function () {
            alertMessageService.showMessage("Wystąpił błąd podczas zmiany kategorii linku '" + $scope.link.title + "'");
        });
    };

    $scope.deleteLink = function() {
        linkService.deleteLink($scope.link.category.publicId, $scope.link.publicId, function () {
            alertMessageService.showMessage("Link '" + $scope.link.title + "' został usunięty");
            close({
                operationType: 'delete',
                link: $scope.link
            }, 500);
        }, function () {
            alertMessageService.showMessage("Wystąpił błąd podczas usuwania linku '" + $scope.link.title + "'");
        });
    };

}]);
