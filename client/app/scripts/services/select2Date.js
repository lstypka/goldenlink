'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Date
 * @description
 * # select2Date
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Date', [function () {

        return  {
            placeholder: 'Od początku',
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'withoutTimeLimit', text: 'Od początku'},
                        {id: 'pastWeek', text: 'Ostatni tydzień'},
                        {id: 'pastMonth', text: 'Ostatni miesiąc'},
                        {id: 'past3Months', text: 'Ostatnie 3 miesiące'},
                        {id: 'past6Months', text: 'Ostatnich 6 miesięcy'},
                        {id: 'pastYear', text: 'Ostatni rok'},
                        {id: 'past2Years', text: 'Ostatnie 2 lata'}
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
