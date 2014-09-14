/**
 * Created by homer on 14.09.14.
 */

app.controller('loginCtrl', function ($scope, $http, $location) {
    $scope.signin = function () {
        $http.post("/rest/j_spring_security_check", $.param({j_username: $scope.username, j_password: $scope.password}), {
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }).success(function () {
                $location.path('/clients');
            });
    };
});
