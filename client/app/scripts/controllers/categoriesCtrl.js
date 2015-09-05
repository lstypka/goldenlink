'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:categoriesCtrl
 * @description
 * # categoriesCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('categoriesCtrl', ['$rootScope', '$scope', '$http', '$timeout', 'categoryService', 'ModalService', 'restServiceConfig', function ($rootScope, $scope, $http, $timeout, categoryService, ModalService, restServiceConfig) {

        $scope.selectedCategory = null;

        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
                window.console.log("Wczytane kategorie " + $scope.mainCategories);
            });

            $http.get('assets/icons.json').success(function (icons) {
                $scope.icons = icons;
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

        $scope.selectCategory = function (selectedCategory) {
            $scope.selectedCategory = selectedCategory;
            $scope.subcategoryDraft = null;
        };

        $scope.editCategory = function () {
            return categoryService.updateCategory($scope.selectedCategory).then(function (updatedCategory) {
                $rootScope.$emit(restServiceConfig.events.CATEGORY_UPDATED, updatedCategory.data);
            });
        };

        $scope.saveSubcategory = function () {
            return categoryService.createSubcategory($scope.selectedCategory.publicId, $scope.subcategoryDraft)
                .then(function (createdSubcategory) {
                    createdSubcategory = createdSubcategory.data;
                    if (!$scope.selectedCategory.hasChildren) {
                        $scope.selectedCategory.hasChildren = true;
                        $scope.selectedCategory.children = [createdSubcategory];
                    } else if ($scope.selectedCategory.children) {
                        $scope.selectedCategory.children.push(createdSubcategory);
                    }
                    $scope.subcategoryDraft = null;
                    $scope.selectedCategory = createdSubcategory;

                    $rootScope.$emit(restServiceConfig.events.SUBCATEGORY_ADDED, createdSubcategory);
                });

        };

        $scope.addSubcategory = function (parentCategory) {
            $scope.subcategoryDraft = {
                publicId: null,
                label: '',
                hasChildren: false,
                categoryGroup: parentCategory.categoryGroup,
                parentPublicId: parentCategory.publicId,
                icon: ''
            };
        };

        $scope.deleteCategory = function () {

        };

        $scope.openIconsModal = function (category) {
            if (!category.parentPublicId && category.publicId) {
                return;
            }

            ModalService.showModal({
                templateUrl: "views/partials/icons_modal.html",
                controller: "iconsCtrl",
                inputs: {
                    icons: $scope.icons
                }
            }).then(function (modal) {
                    modal.element.modal();
                    modal.close.then(function (result) {
                        category.icon = 'fa-' + result;
                    });
                });
        };

        init();

    }]);
