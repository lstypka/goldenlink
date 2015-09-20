'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Author
 * @description
 * # select2Author
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Author', ['authorsService', '$translate', function (authorsService, $translate) {

        var authors = [];

        var init = function () {
            authorsService.getAuthors(function (results) {
                for (var i = 0; i < results.length; i++) {
                    authors.push({ id: results[i].publicId, text: results[i].name});
                }
            });
        };

        init();

        return  {
            containerCssClass: 'select2-container',
            formatNoMatches: function () {
                return $translate.instant('NO_MATCHES_FOUND');
            },
            allowClear: true,
            query: function (query) {
                var data = {
                    results: []
                };
                for (var i = 0; i < authors.length; i++) {
                    if (authors[i].text.indexOf(query.term) > -1) {
                        data.results.push(authors[i]);
                    }
                }
                query.callback(data);
            },
            formatSelection: function format(item) {
                var originalText = item.text;
                return "<div title ='" + originalText + "'>" + originalText + "</div>";
            }
        };

    }]);
