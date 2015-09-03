'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:breadcrumbsCtrl
 * @description
 * # breadcrumbsCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('breadcrumbsCtrl', ['$scope', 'breadcrumbsService', function ($scope, breadcrumbsService) {

        var init = function () {
            loadBreadcrumbs();
        };

        var loadBreadcrumbs = function() {
            breadcrumbsService.getBreadcrumbs().then(function (breadcrumbs) {
                $scope.breadcrumbs = breadcrumbs.data;
            });

        };

        init();

    }]);
