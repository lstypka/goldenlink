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

                    execute(current.params.category_id, function (foundCategory) {
                        foundCategory._isSelected = true;
                        previousSelectedCategory = foundCategory;
                    });

                });
            });

            $rootScope.$on(restServiceConfig.events.CATEGORY_UPDATED, function (event, categoryToUpdate) {
                execute(categoryToUpdate.publicId, function (foundCategory) {
                    foundCategory.label = categoryToUpdate.label;
                    foundCategory.icon = categoryToUpdate.icon;
                });
            });

            $rootScope.$on(restServiceConfig.events.SUBCATEGORY_ADDED, function (event, addedSubcategory) {
                execute(addedSubcategory.parentPublicId, function (foundParentCategory) {
                    if (!foundParentCategory.hasChildren) {
                        foundParentCategory.hasChildren = true;
                        foundParentCategory.children = [addedSubcategory];
                    } else if (foundParentCategory.children) {
                        foundParentCategory.children.push(addedSubcategory);
                    }
                });
            });

        };

        var execute = function (categoryPublicId, successFn) {
            for (var i = 0; i < $scope.mainCategories.length; i++) {
                var found = executeRecursively($scope.mainCategories[i], categoryPublicId, successFn);
                if (found) {
                    return;
                }
            }
        };

        var executeRecursively = function (category, categoryPublicId, successFn) {
            if (category.publicId === categoryPublicId) {
                successFn(category);
                return true;
            } else {
                if (category.hasChildren && category.children) {
                    for (var i = 0; i < category.children.length; i++) {
                        var updated = executeRecursively(category.children[i], categoryPublicId, successFn);
                        if (updated) {
                            return true;
                        }
                    }
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
