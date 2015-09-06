'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:breadcrumbsCtrl
 * @description
 * # breadcrumbsCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('breadcrumbsCtrl', ['$scope', '$routeParams', 'breadcrumbsService', function ($scope,$routeParams,  breadcrumbsService) {

        var init = function () {
            loadBreadcrumbs();
        };

        var loadBreadcrumbs = function() {
            if($routeParams.category_id) {
                breadcrumbsService.getBreadcrumbs($routeParams.category_id).then(function (breadcrumbs) {
                    $scope.breadcrumbs = breadcrumbs.data;
                });
            }
        };

        init();

    }]);
