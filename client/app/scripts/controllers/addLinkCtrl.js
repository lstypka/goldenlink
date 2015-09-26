'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:addLinkCtrl
 * @description
 * # addLinkCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('addLinkCtrl', ['$rootScope', '$scope', '$http', '$timeout', '$translate', 'timeService', 'authService', 'utilService', 'categoryService', 'linkService', 'alertMessageService', function ($rootScope, $scope, $http, $timeout, $translate, timeService, authService, utilService, categoryService, linkService, alertMessageService) {

        $scope.editMode = true;


        var init = function () {
            categoryService.getCategories().then(function (categories) {
                $scope.mainCategories = categories.data;
            });
            resetLinkForm();
        };

        var resetLinkForm = function () {
            if ($scope.link && $scope.link.category) {
                $scope.link.category._isSelected = false;
            }
            $scope.link = {
                tags: [],
                date: timeService.now(),
                author: authService.getLoggedUser()
            };
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

        $scope.tagKeypress = function (link, event) {
            if (event.which === 13) {
                event.preventDefault();
                if (!link._tag) {
                    return;
                }
                var foundDuplicate = false;
                for (var i = 0; i < link.tags.length; i++) {
                    if (link.tags[i].label.trim() === link._tag.trim()) {
                        foundDuplicate = true;
                    }
                }
                if (!foundDuplicate) {
                    if (!(link._tag.length === 0 || !link._tag.trim())) {
                        link.tags.unshift({id: null, label: link._tag.trim() });
                    }
                }
                link._tag = '';
            }
        };

        $scope.deleteTag = function (link, tag) {
            for (var i = 0; i < link.tags.length; i++) {
                if (link.tags[i].label.trim() === tag.label.trim()) {
                    link.tags.splice(i, 1);
                }
            }
        };

        $scope.selectCategory = function (selectedCategory) {
            if ($scope.link.category) {
                $scope.link.category._isSelected = false;
            }
            selectedCategory._isSelected = true;

            $scope.link.category = selectedCategory;
        };

        $scope.isBlank = function (value) {
            return utilService.isBlank(value);
        };

        $scope.checkUrl = function (link) {
            window.console.log("BLUR ", link);
            if (link.category.categoryGroup === 'YOUTUBE') {
                if (link.link !== null && link.link !== '') {
                    if (link.link.indexOf('https://') !== 0) {
                        link.link = 'https://' + link.link;
                    }
                    if (link.link.indexOf('embed') !== 0) {
                        var youtubeId = getParameterByName(link.link, 'v');
                        link.link = "https://www.youtube.com/embed/" + youtubeId;
                    }
                }
            }
        };

        function getParameterByName(url, name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(url);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }

        $scope.saveLink = function () {
            linkService.addLink($scope.link.category.publicId, $scope.link, function () {
                alertMessageService.showMessage("LINK_ADD_MESSAGE", {label: $scope.link.title});
                resetLinkForm();
            }, function () {
                alertMessageService.showMessage("LINK_ADD_ERROR_MESSAGE", {label: $scope.link.title});
            });
        };

        init();

    }]);
