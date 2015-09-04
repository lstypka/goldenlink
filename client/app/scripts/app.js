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
        'ngDragDrop'
    ]).constant("restServiceConfig", {
        "url": "http://localhost:8080"
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
            .otherwise({
                redirectTo: '/'
            });
    });
