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

    $scope.mainCategories = [];
    $scope.settings = settingsService.settings;

    $scope.editMode = true;

    var init = function () {
        $scope.link = link;
        $scope.categoryPrefix = '' + Math.random();
        categoryService.getCategories().then(function (categories) {
            $scope.mainCategories = categories.data;
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
            alertMessageService.showMessage("LINK_UPDATE_MESSAGE", {label: response.title});
            close({
                operationType: 'edit',
                link: response
            }, 500);
        }, function () {
            alertMessageService.showMessage("LINK_UPDATE_ERROR_MESSAGE", {label: $scope.link.title});
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
            alertMessageService.showMessage("LINK_ASSIGN_TO_CATEGORY_MESSAGE", {linkLabel: response.title, categoryLabel: response.category.label});
            close({
                operationType: 'changeCategory',
                link: response
            }, 500);
        }, function () {
            alertMessageService.showMessage("LINK_ASSIGN_TO_CATEGORY_ERROR_MESSAGE", { linkLabel: $scope.link.title, categoryLabel: $scope.link.category.label });
        });
    };

    $scope.deleteLink = function () {
        linkService.deleteLink($scope.link.category.publicId, $scope.link.publicId, function () {
            alertMessageService.showMessage("LINK_REMOVE_MESSAGE", {label: $scope.link.title});
            close({
                operationType: 'delete',
                link: $scope.link
            }, 500);
        }, function () {
            alertMessageService.showMessage("LINK_REMOVE_ERROR_MESSAGE", {label: $scope.link.title });
        });
    };

}]);
