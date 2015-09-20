'use strict';

angular.module('clientApp').directive('dateTimePicker', ['$timeout', '$translate', '$parse', 'moment', function ($timeout, $translate, $parse, moment) {
    return {
        require: '?ngModel',
        restrict: 'AE',
        link: function ($scope, element, $attrs) {
            return $timeout(function () {
                var ngModelGetter = $parse($attrs.ngModel);
                return $(element).datetimepicker(
                    {
                        minDate: moment().toDate(),
                        sideBySide: false,
                        allowInputToggle: true,
                        showClear : true,
                        locale: $translate.use(),
                        useCurrent: false,
                        date:  ngModelGetter($scope),
                        format: 'YYYY.MM.DD',
                        useStrict: true/*,
                        defaultDate : moment().add(1, 'd').toDate()*/
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