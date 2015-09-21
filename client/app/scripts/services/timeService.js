'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:timeService
 * @description
 * # timeService
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('timeService', ['moment', function (moment) {

        this.now = function () {
            return moment().utc().format("YYYY-MM-DD[T]HH:mm:ss[Z]");
        };

        this.formatDate = function (value) {
            if (value) {
                value = moment(value).utc().format("YYYY-MM-DD[T]HH:mm:ss[Z]");
            }
            if (value && value.trim().length === 0) {
                value = null;
            }
            return value;
        };

    }]);
