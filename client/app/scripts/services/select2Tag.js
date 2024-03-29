'use strict';

/**
 * @ngdoc function
 * @name clientApp.service:select2Tag
 * @description
 * # select2Tag
 * Service of the clientApp
 */
angular.module('clientApp')
    .service('select2Tag', ['tagService', '$translate', function (tagService, $translate) {

        var tags = [];

        var init = function () {
            tagService.getTags(function (results) {
                for (var i = 0; i < results.length; i++) {
                    tags.push({ id: results[i].publicId, text: results[i].label});
                }
            });
        };

        init();

        return {
            containerCssClass: 'select2-container',
            allowClear: true,
            formatNoMatches: function () {
                return $translate.instant('NO_MATCHES_FOUND');
            },
            query: function (query) {
                var data = {
                    results: []
                };
                for (var i = 0; i < tags.length; i++) {
                    if (tags[i].text.indexOf(query.term) > -1) {
                        data.results.push(tags[i]);
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
