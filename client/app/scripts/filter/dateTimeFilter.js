'use strict';

/**
 * @ngdoc function
 * @name clientApp.filter:dateTimeFilter
 * @description
 * # dateTimeFilter
 * Filter of the dateTimeFilter
 */



angular.module('clientApp').filter('dateTimeFilter', ['$filter', function ($filter) {
    return function (input) {
        return $filter('date')(input, 'yyyy.MM.dd HH:mm');
    };
}]);
