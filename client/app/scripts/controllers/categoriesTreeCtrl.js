'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:categoriesTreeCtrl
 * @description
 * # categoriesTreeCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('categoriesTreeCtrl', ['$scope', '$timeout', '$location', '$routeParams', 'categoryService', function ($scope, $timeout, $location, $routeParams, categoryService) {

        var previousSelectedCategory = null;

        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
                $scope.$on('$routeChangeSuccess', function (next, current) {
                    if (!$scope.mainCategories) {
                        return;
                    }
                    if (previousSelectedCategory) {
                        previousSelectedCategory._isSelected = false;
                    }
                    for (var i = 0; i < $scope.mainCategories.length; i++) {
                        var found = selectCategoryOnTree($scope.mainCategories[i], current.params.category_id);
                        if(found) {
                            return;
                        }
                    }
                });
            });
        };

        var selectCategoryOnTree = function (category, selectedCategoryId) {
            if (category.publicId === selectedCategoryId) {
                category._isSelected = true;
                previousSelectedCategory = category;
                return true;
            } else {
                if(category.hasChildren && category.children) {
                    for (var i = 0; i < category.children.length; i++) {
                        var found = selectCategoryOnTree(category.children[i], selectedCategoryId);
                        if(found) {
                            return true;
                        }
                    }
                }
            }
            return false;
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
                    }, 1500);
            } else {
                category.isLoadingChildren = false;
            }
        };

        $scope.loadCategory = function (category) {
            if (previousSelectedCategory) {
                previousSelectedCategory._isSelected = false;
            }
            category._isSelected = true;
            previousSelectedCategory = category;
            window.console.log(category);
        };


        init();
    }]);
