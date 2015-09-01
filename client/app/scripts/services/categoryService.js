'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:categoryService
 * @description
 * # categoryService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('categoryService', ['$http', function ($http) {

        this.getCategories = function () {
            var promise = $http.get('http://localhost:8080/categories').then(function (response) {
                return response;
            });
            return promise;
        };

        this.getChildren2 = function (publicId) {
            var promise = $http.get('http://localhost:8080/categories/' + publicId + '/children').then(function (response) {
                return response;
            });
            return promise;
        };

        this.getChildren = function () {
            var children = {};
            children.data = [];
            for (var i = 0; i < Math.floor((Math.random() * 10) + 1); i++) {
                var id = guid();
                children.data.push({publicId : id, label : id, hasChildren: true});
            }

            return { then: function (successFn) {
                successFn(children);
            }};
        };

        function guid() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
            }

            return s4() + s4() + '-' + s4();
        }
    }]);
