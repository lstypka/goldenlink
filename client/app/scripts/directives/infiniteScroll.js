'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:alertMessageService
 * @description
 * # alertMessageService
 * Service of the clientApp
 */
angular.module('clientApp').directive('infiniteScroll', ['$timeout', '$rootScope', function (timeout, $rootScope) {
    return{
        priority: 10000,
        link: function (scope, element, attr) {
            var
                lengthThreshold = attr.scrollThreshold || 50,
                timeThreshold = attr.timeThreshold || 400,
                handler = scope.$eval(attr.infiniteScroll),
                promise = null,
                lastRemaining = 9999;

            lengthThreshold = parseInt(lengthThreshold, 10);
            timeThreshold = parseInt(timeThreshold, 10);

            if (!handler || !angular.isFunction(handler)) {

                if (attr.infiniteScrollEvent) {
                    handler = function () {
                        $rootScope.$emit(attr.infiniteScrollEvent);
                    };
                } else {
                    handler = angular.noop;
                }
            }

            element.bind('scroll', function () {
                var
                    remaining = element[0].scrollHeight - (element[0].clientHeight + element[0].scrollTop);
                 window.console.log("REMAINING " + remaining );
                //if we have reached the threshold and we scroll down
                if (remaining < lengthThreshold && (remaining - lastRemaining) < 0) {

                    //if there is already a timer running which has no expired yet we have to cancel it and restart the timer
                    if (promise !== null) {
                        timeout.cancel(promise);
                    }
                    promise = timeout(function () {
                        handler();
                        promise = null;
                    }, timeThreshold);
                }
                lastRemaining = remaining;
            });
        }

    };
}]);