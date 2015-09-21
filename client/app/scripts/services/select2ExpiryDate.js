'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2ExpiryDate
 * @description
 * # select2ExpiryDate
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2ExpiryDate', ['$translate', function ($translate) {

        return  {
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'withoutTimeLimit', text: $translate.instant('ANY_DATE')},
                        {id: 'withinWeek', text: $translate.instant('WITHIN_WEEK')},
                        {id: 'within2Weeks', text: $translate.instant('WITHIN_2_WEEKS')},
                        {id: 'withinMonth', text: $translate.instant('WITHIN_MONTH')},
                        {id: 'within2Months', text: $translate.instant('WITHIN_2_MONTHS')},
                        {id: 'within6Months', text: $translate.instant('WITHIN_6_MONTHS')},
                        {id: 'withinYear', text: $translate.instant('WITHIN_YEAR')}
                    ]
                };
                query.callback(data);
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };

    }]);
