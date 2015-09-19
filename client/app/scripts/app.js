'use strict';

/**
 * @ngdoc overview
 * @name clientApp
 * @description
 * # clientApp
 *
 * Main module of the application.
 */
angular
    .module('clientApp', [
        'ngAnimate',
        'ngAria',
        'ngCookies',
        'ngMessages',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'ngDragDrop',
        'ui.bootstrap',
        'angularModalService',
        'ngClipboard',
        'angularMoment',
        'rt.select2',
        'ui.bootstrap.datetimepicker',
        'pascalprecht.translate'

    ]).constant("restServiceConfig", {
        "url": "http://localhost:8080",
        "events": {
            SHOW_MESSAGE: "show_message",
            CATEGORY_UPDATED: "category_updated",
            SUBCATEGORY_ADDED: "subcategory_added",
            CATEGORY_DELETED: "category_deleted",
            ICONS_MODAL_NEXT_PAGE: "icons_modal_next_page"
        }
    })
    .config(function ($routeProvider) {
        $routeProvider
            .when('', {
                templateUrl: 'views/home.html'
            })
            .when('/', {
                templateUrl: 'views/home.html'
            })
            .when('/links/:category_id', {
                templateUrl: 'views/links.html'
            })
            .when('/photos/:category_id', {
                templateUrl: 'views/links.html'
            })
            .when('/videos/:category_id', {
                templateUrl: 'views/links.html'
            })
            .when('/youtube/:category_id', {
                templateUrl: 'views/links.html'
            })
            .when('/notes/:category_id', {
                templateUrl: 'views/links.html'
            })
            .when('/search', {
                templateUrl: 'views/search.html'
            })
            .when('/categories', {
                templateUrl: 'views/categories.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

angular.module('clientApp').config(['$sceDelegateProvider', function($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist(['**']);
}]);

angular.module('clientApp').config(['$translateProvider', function($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix: 'locale/locale-',
        suffix: '.json'
    });

    $translateProvider.useCookieStorage();
    $translateProvider.preferredLanguage('en');

    $translateProvider.registerAvailableLanguageKeys(['en', 'pl'], {
        'en_US': 'en_EN',
        'pl_PL': 'pl_PL'
    }).determinePreferredLanguage();
}]);