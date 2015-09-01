'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:LeftMenuCtrl
 * @description
 * # LeftMenuCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('leftMenuCtrl', ['$scope', '$timeout', 'categoryService', function ($scope, $timeout, categoryService) {

        var previousSelectedCategory = null;

        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
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
                    }, 1500);
            } else {
                category.isLoadingChildren = false;
            }
        };

        $scope.loadCategory = function (category, event) {
            if (event) {
                event.preventDefault();
                event.stopPropagation();
            }
            if (previousSelectedCategory) {
                previousSelectedCategory._isSelected = false;
            }
            category._isSelected = true;
            previousSelectedCategory = category;
            window.console.log(category);
        };


        init();
    }]);
