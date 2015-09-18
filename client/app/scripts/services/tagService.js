'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:friendsService
 * @description
 * # friendsService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('tagService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        var cachedTags = null;

        this.getTags = function (successFn) {
            if (this.hasCachedTags()) {
                successFn(this.hasCachedTags());
            }
            $http.get(restServiceConfig.url + '/tags').then(function (response) {
                cachedTags = response.data;
                successFn(response.data);
            });

        };

        this.cachedTags = function () {
            return cachedTags;
        };

        this.hasCachedTags = function () {
            return cachedTags !== null;
        };

        this.setTags = function (tags) {
            this.cachedTags = tags
            ;
        };

    }]);
