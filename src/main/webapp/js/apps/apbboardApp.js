/**
 * Created by homer on 12.09.14.
 */

var app = angular.module("apbboardApp", ['ngRoute','ui.bootstrap']);

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: './templates/mainPage.html',
                controller: 'bulletinListCtrl'
            }).
            when('/:orderStatus', {
                templateUrl: './templates/mainPage.html',
                controller: 'bulletinListCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
