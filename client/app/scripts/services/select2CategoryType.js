'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2CategoryType
 * @description
 * # select2CategoryType
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2CategoryType', ['$translate', function ($translate) {

        return  {
            containerCssClass: 'select2-container',
            allowClear: true,
            minimumResultsForSearch: Infinity,
            query: function (query) {
                var data = {
                    results: [
                        {id: 'LINKS', text: $translate.instant('LINKS')},
                        {id: 'PHOTOS', text: $translate.instant('PHOTOS')},
                        {id: 'VIDEOS', text: $translate.instant('VIDEOS')},
                        {id: 'YOUTUBE', text: $translate.instant('YOUTUBE')}
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
