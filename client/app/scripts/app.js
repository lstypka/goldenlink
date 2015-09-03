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
    ])
    .config(function ($routeProvider) {
        $routeProvider
            .when('', {
                templateUrl: 'views/home.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/links/:category_id', {
                templateUrl: 'views/links.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/photos/:category_id', {
                templateUrl: 'views/photos.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/videos/:category_id', {
                templateUrl: 'views/videos.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/youtube/:category_id', {
                templateUrl: 'views/youtube.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/notes/:category_id', {
                templateUrl: 'views/notes.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/search', {
                templateUrl: 'views/search.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .otherwise({
                redirectTo: '/'
            });
    });
