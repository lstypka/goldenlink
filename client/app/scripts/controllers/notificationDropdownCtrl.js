'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:notificationCtrl
 * @description
 * # notificationCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
    .controller('notificationDropdownCtrl', ['$scope', 'notificationService', function ($scope, notificationService) {

        var init = function () {
            var page = 0;
            var resultsPerPage = 3;
            notificationService.getNotifications(page, resultsPerPage).then(function(response) {
                window.console.log("NOTIFICATIONS ", response.data);
                 $scope.notifications = response.data.notifications;
            });
        };

        init();

    }]);
