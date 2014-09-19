/**
 * Created by homer on 14.09.14.
 */

var app = angular.module("postBulletinApp", ['ui.bootstrap', 'ngRoute']);

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: './templates/postForm.html',
                controller: 'postFormCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
