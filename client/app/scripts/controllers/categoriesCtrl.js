'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:categoriesCtrl
 * @description
 * # categoriesCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('categoriesCtrl', ['$scope', '$http', 'ModalService', function ($scope, $http, ModalService) {

        var init = function () {
            $http.get('assets/icons.json').success(function (icons) {
                $scope.icons = icons;
            });
        };

        $scope.open = function() {

            ModalService.showModal({
                templateUrl: "views/partials/icons_modal.html",
                controller: "iconsCtrl",
                inputs: {
                    icons : $scope.icons
                }
            }).then(function(modal) {
                    modal.element.modal();
                    modal.close.then(function(result) {
                        window.console.log("RESULT " + result);
                    });
                });
        };

        init();

    }]);
