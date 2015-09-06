'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:categoriesTreeCtrl
 * @description
 * # categoriesTreeCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('categoriesTreeCtrl', ['$rootScope', '$scope', '$timeout', '$location', '$routeParams', 'categoryService', 'restServiceConfig', function ($rootScope, $scope, $timeout, $location, $routeParams, categoryService, restServiceConfig) {

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

                    categoryService.execute($scope.mainCategories, current.params.category_id, function (foundCategory) {
                        foundCategory._isSelected = true;
                        previousSelectedCategory = foundCategory;
                    });

                });
            });

            $rootScope.$on(restServiceConfig.events.CATEGORY_UPDATED, function (event, categoryToUpdate) {
                categoryService.execute($scope.mainCategories, categoryToUpdate.publicId, function (foundCategory) {
                    foundCategory.label = categoryToUpdate.label;
                    foundCategory.icon = categoryToUpdate.icon;
                });
            });

            $rootScope.$on(restServiceConfig.events.SUBCATEGORY_ADDED, function (event, addedSubcategory) {
                categoryService.execute($scope.mainCategories, addedSubcategory.parentPublicId, function (foundParentCategory) {
                    if (!foundParentCategory.hasChildren) {
                        foundParentCategory.hasChildren = true;
                        foundParentCategory.children = [addedSubcategory];
                    } else if (foundParentCategory.children) {
                        foundParentCategory.children.push(addedSubcategory);
                    }
                });
            });

            $rootScope.$on(restServiceConfig.events.CATEGORY_DELETED, function (event, categoryToDelete) {
                categoryService.execute($scope.mainCategories, categoryToDelete.parentPublicId, function (foundCategory) {
                    if (foundCategory.children.length === 1) {
                        foundCategory.hasChildren = false;
                        foundCategory.children = null;
                    } else {
                        for (var i = 0; i < foundCategory.children.length; i++) {
                            if (foundCategory.children[i].publicId === categoryToDelete.publicId) {
                                foundCategory.children.splice(i, 1);
                            }
                        }
                    }
                });
            });
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
