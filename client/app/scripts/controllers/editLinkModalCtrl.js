'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:editLinkModalCtrl
 * @description
 * # editLinkModalCtrl
 * Controller of the clientApp
 */

var app = angular.module('clientApp');

app.controller('editLinkModalCtrl', ['$scope', 'restServiceConfig', 'alertMessageService', 'linkService', 'moment', 'link', 'close', function ($scope, restServiceConfig, alertMessageService, linkService, moment, link, close) {

    $scope.showEdit = true;
    $scope.showPreview = false;


    $scope.defaultCommentLimit = 100;

    var init = function () {
        $scope.link = link;
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

    $scope.updateLink = function() {
        linkService.updateLink($scope.link.category.publicId, $scope.link.publicId, $scope.link, function(response){
            alertMessageService.showMessage("Link '" + response.title + "' został zaktualizowany");
            close(response, 500);
        }, function() {
            alertMessageService.showMessage("Wystąpił błąd podczas aktualizowania linku '" + $scope.link.title + "'");
        });
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

    $scope.showMore = function (link) {
        link.commentLimit = link.comment.length;
    };

    $scope.showMoreVisible = function (link) {
        return link.comment.length > $scope.commentLengthLimit(link);
    };

    $scope.showLess = function (link) {
        link.commentLimit = $scope.defaultCommentLimit;
    };

    $scope.showLessVisible = function (link) {
        return $scope.commentLengthLimit(link) > $scope.defaultCommentLimit;
    };

    $scope.commentLengthLimit = function (link) {
        return link.commentLimit || $scope.defaultCommentLimit;
    };

    init();

}]);