'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Author
 * @description
 * # select2Author
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Category', ['categoryService', 'authorsService', '$translate', function (categoryService, authorsService, $translate) {

        var init = function () {

        };

        init();

        return  {
            containerCssClass: 'select2-container',
            minimumInputLength: 3,
            formatNoMatches: function () {
                return $translate.instant('NO_MATCHES_FOUND');
            },
            formatInputTooShort: function () {
                return $translate.instant('ENTER_THREE_CHARACTERS');
            },
            allowClear: true,
            query: function (query) {

                categoryService.searchCategories(query.term).then(function (httpResult) {
                    var data = {
                        results: []
                    };
                    var results = httpResult.data;
                    for (var i = 0; i < results.length; i++) {
                        data.results.push({id: results[i].publicId, text: results[i].label});
                    }
                    query.callback(data);
                });
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };

    }]);
