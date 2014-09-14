/**
 * Created by homer on 14.09.14.
 */

var app = angular.module("adminApp", ['ui.bootstrap', 'ngRoute']);

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: './templates/adminSignInView.html',
                controller: 'loginCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
