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
        'rt.select2'

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
                templateUrl: 'views/photos.html'
            })
            .when('/videos/:category_id', {
                templateUrl: 'views/videos.html'
            })
            .when('/youtube/:category_id', {
                templateUrl: 'views/youtube.html'
            })
            .when('/notes/:category_id', {
                templateUrl: 'views/notes.html'
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

angular.module('clientApp').config(function($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist(['**']);
});