'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Star
 * @description
 * # select2Star
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Star', ['$translate', function ($translate) {

        return  {
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'MARKED', text: $translate.instant('MARKED')},
                        {id: 'NOT_MARKED', text: $translate.instant('NOT_MARKED')}
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
