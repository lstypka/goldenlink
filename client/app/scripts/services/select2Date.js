'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Date
 * @description
 * # select2Date
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Date', ['$translate', function ($translate) {

        return  {
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'withoutTimeLimit', text: $translate.instant('ANY_DATE')},
                        {id: 'pastWeek', text: $translate.instant('LAST_WEEK')},
                        {id: 'pastMonth', text: $translate.instant('LAST_MONTH')},
                        {id: 'past3Months', text: $translate.instant('LAST_3_MONTHS')},
                        {id: 'past6Months', text: $translate.instant('LAST_6_MONTHS')},
                        {id: 'pastYear', text: $translate.instant('LAST_YEAR')},
                        {id: 'past2Years', text: $translate.instant('LAST_2_YEARS')}
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
