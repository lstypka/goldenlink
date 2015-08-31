'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:categoryService
 * @description
 * # categoryService
 * Service of the clientApp
 */
angular.module('clientApp')
  .service('categoryService', function ($http) {

        this.getCategories = function() {
            var promise = $http.get('http://localhost:8080/categories').then(function(response) {
                return response;
            });
            return promise;
        };

        this.getChildren = function(publicId) {
            var promise = $http.get('http://localhost:8080/categories/' + publicId + '/children').then(function(response) {
                return response;
            });
            return promise;
        };
  });
