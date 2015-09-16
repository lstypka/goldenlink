'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:editLinkModalCtrl
 * @description
 * # editLinkModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('editLinkModalCtrl', ['$scope', 'restServiceConfig', 'moment', 'link', 'close', function ($scope, restServiceConfig, moment, link, close) {

    $scope.showEdit = true;
    $scope.showPreview = false;

    var init = function () {
        $scope.link = link;
        if ($scope.link.expiryDate) {
         /* $scope.link.expiryDate = moment($scope.link.expiryDate, "yyyy-MM-dd'T'HH:mm:ss'Z'").toDate();
*/
        }
    };

    $scope.close = function (result) {
        close(result, 500);
    };

    $scope.expireDateSet = function (param1, param2) {
        window.console.log("PARAMS ", param1, param2);
    };

    $scope.setActiveTab = function (tabName) {
        if (tabName === 'preview') {
            $scope.showEdit = false;
            $scope.showPreview = true;
        } else {
            $scope.showEdit = true;
            $scope.showPreview = false;
        }
    };

    $scope.tagKeypress = function (link, event) {
        if (event.which === 13) {
            var foundDuplicate = false;
            for (var i = 0; i < link.tags.length; i++) {
                if (link.tags[i].label.trim() === $scope.tag.trim()) {
                    foundDuplicate = true;
                }
            }
            if (!foundDuplicate) {
                if (!($scope.tag.length === 0 || !$scope.tag.trim())) {
                    link.tags.push({id: null, label: $scope.tag.trim() });
                }
            }
            $scope.tag = '';
        }
    };

    $scope.deleteTag = function (link, tag) {
        for (var i = 0; i < link.tags.length; i++) {
            if (link.tags[i].label.trim() === tag.label.trim()) {
                link.tags.splice(i, 1);
            }
        }
    };

    init();

}]);