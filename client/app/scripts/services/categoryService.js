'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:categoryService
 * @description
 * # categoryService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('categoryService', ['$http', 'restServiceConfig', function ($http, restServiceConfig) {

        this.getCategories = function () {
            var promise = $http.get(restServiceConfig.url + '/categories').then(function (response) {
                return response;
            });
            return promise;
        };

        this.getChildren = function (publicId) {
            var promise = $http.get(restServiceConfig.url + '/categories/' + publicId + '/children').then(function (response) {
                return response;
            });
            return promise;
        };

        this.updateCategory = function(category) {
            var promise = $http.put(restServiceConfig.url + '/categories/' + category.publicId, category).then(function (response) {
                return response;
            });
            return promise;
        };

        this.createSubcategory = function(parentPublicId, category) {
            var promise = $http.post(restServiceConfig.url + '/categories/' + parentPublicId, category).then(function (response) {
                return response;
            });
            return promise;
        };

    }]);
