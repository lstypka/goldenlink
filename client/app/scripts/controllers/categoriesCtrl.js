'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:categoriesCtrl
 * @description
 * # categoriesCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('categoriesCtrl', ['$rootScope', '$scope', '$http', '$timeout', '$translate', 'categoryService', 'alertMessageService', 'breadcrumbsService', 'dashboardService', 'ModalService', 'restServiceConfig', function ($rootScope, $scope, $http, $timeout, $translate, categoryService, alertMessageService, breadcrumbsService, dashboardService, ModalService, restServiceConfig) {

        $scope.selectedCategory = null;

        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
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
            if ($scope.selectedCategory) {
                $scope.selectedCategory._isSelected = false;
            }
            selectedCategory._isSelected = true;

            $scope.selectedCategory = selectedCategory;
            $scope.subcategoryDraft = null;
            updateBreadcrumbs(selectedCategory.publicId);
        };

        $scope.editCategory = function () {
            return categoryService.updateCategory($scope.selectedCategory).then(function (updatedCategory) {
                updatedCategory = updatedCategory.data;
                $rootScope.$emit(restServiceConfig.events.CATEGORY_UPDATED, updatedCategory);
                alertMessageService.showMessage("CATEGORY_UPDATED_CORRECTLY_MESSAGE", { label: updatedCategory.label});
                updateBreadcrumbs(updatedCategory.publicId);
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

                    if ($scope.selectedCategory) {
                        $scope.selectedCategory._isSelected = false;
                    }
                    $scope.selectedCategory = createdSubcategory;
                    $scope.selectedCategory._isSelected = true;

                    $rootScope.$emit(restServiceConfig.events.SUBCATEGORY_ADDED, createdSubcategory);
                    alertMessageService.showMessage("CATEGORY_ADDED_CORRECTLY_MESSAGE", {label: createdSubcategory.label});
                    updateBreadcrumbs(createdSubcategory.publicId);
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

        $scope.sendToDashboard = function (category) {
            $scope.openPanelsModal(category, function (category) {
                dashboardService.createTile(category).then(function (data) {
                    var tile = data.data;
                    alertMessageService.showMessage("CATEGORY_SEND_TO_DASHBOARD_CORRECTLY_MESSAGE", {label: tile.label});

                });
            });
        };

        $scope.deleteCategory = function () {
            $scope.showConfirmModal($translate.instant("CATEGORY_DELETING_HEADER", {label: $scope.selectedCategory.label}),
                $translate.instant("CATEGORY_DELETING_MESSAGE", {label: $scope.selectedCategory.label}),
                function () {
                    categoryService.deleteSubcategory($scope.selectedCategory.publicId).then(function () {
                        alertMessageService.showMessage("CATEGORY_DELETED_CORRECTLY_MESSAGE", {label: $scope.selectedCategory.label});
                        $rootScope.$emit(restServiceConfig.events.CATEGORY_DELETED, $scope.selectedCategory);
                        categoryService.execute($scope.mainCategories, $scope.selectedCategory.parentPublicId, function (foundCategory) {
                            if (foundCategory.children.length === 1) {
                                foundCategory.hasChildren = false;
                                foundCategory.children = null;
                            } else {
                                for (var i = 0; i < foundCategory.children.length; i++) {
                                    if (foundCategory.children[i].publicId === $scope.selectedCategory.publicId) {
                                        foundCategory.children.splice(i, 1);
                                    }
                                }
                            }
                        });
                    });
                });

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

        $scope.openPanelsModal = function (category, successFn) {
            ModalService.showModal({
                templateUrl: "views/partials/panels_modal.html",
                controller: "panelsCtrl",
                inputs: {

                }
            }).then(function (modal) {
                    modal.element.modal();
                    modal.close.then(function (result) {
                        category.colour = result;
                        successFn(category);
                    });
                });
        };

        $scope.showConfirmModal = function (title, message, positiveFn, negativeFn) {

            ModalService.showModal({
                templateUrl: "views/partials/confirm_modal.html",
                controller: "confirmModalCtrl",
                inputs: {
                    title: title,
                    message: message
                }
            }).then(function (modal) {
                    modal.element.modal();
                    modal.close.then(function (result) {
                        if (result) {
                            if (positiveFn) {
                                positiveFn();
                            }
                        } else {
                            if (negativeFn) {
                                negativeFn();
                            }
                        }
                    });
                });
        };

        $scope.isBlank = function (value) {
            return !!(value || '').match(/^\s*$/);
        };

        var updateBreadcrumbs = function (publicId) {
            breadcrumbsService.getBreadcrumbs(publicId).then(function (breadcrumbs) {
                $scope.breadcrumbs = breadcrumbs.data;
            });
        };


        init();

    }]);
