'use strict';

angular.module('clientApp').directive('dateTimePicker', ['$timeout', '$parse', 'moment', function ($timeout, $parse, moment) {
    return {
        require: '?ngModel',
        restrict: 'AE',
        link: function ($scope, element, $attrs) {
            return $timeout(function () {
                var ngModelGetter = $parse($attrs.ngModel);

                window.console.log("MODEL VALUE ", ngModelGetter($scope));
                return $(element).datetimepicker(
                    {
                        minDate: moment().toDate(),
                        sideBySide: false,
                        allowInputToggle: true,
                        locale: "pl",
                        useCurrent: false,
                        format: 'YYYY-MM-DD',
                        defaultDate : ngModelGetter($scope)
                    }
                ).on('dp.change', function (event) {
                        $scope.$apply(function () {
                            return ngModelGetter.assign($scope, event.target.value);
                        });
                    });
            });
        }

    };
}]);