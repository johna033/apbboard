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
            when('/execute', {
                templateUrl:'./templates/executePayment.html',
                controller: 'executePaymentCtrl'
            }).
            when('/cancel', {
                templateUrl:'./templates/cancelPayment.html',
                controller: 'cancelPaymentCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
