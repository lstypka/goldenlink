'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:LeftMenuCtrl
 * @description
 * # LeftMenuCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('leftMenuCtrl', function ($scope, categoryService) {

        var previousSelectedCategory = null;

        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
            });
        };

        $scope.loadChildren = function (category, event) {
            if(event) {
                event.preventDefault();
            }
            if(category._opened)  {
                category._opened = false;
            } else {
                category._opened = true;
            }

            if (!category._childrenLoaded) {
                category._loading = true;
                categoryService.getChildren(category.publicId).then(function (children) {
                    category.children = children.data;
                    category._childrenLoaded = true;
                    category._loading = false;
                });
            }
        };

        $scope.loadCategory = function(category) {
            if(previousSelectedCategory) {
                previousSelectedCategory._isSelected = false;
            }
            category._isSelected = true;
            previousSelectedCategory = category;
            window.console.log(category);
        };

        init();
    });
