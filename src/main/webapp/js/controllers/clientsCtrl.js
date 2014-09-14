/**
 * Created by homer on 14.09.14.
 */


app.controller('clientsCtrl', function ($scope, $http) {

    $http.post('/rest/admin/clients').success(function(data){
        $scope.clients = data;
        console.log(data);
    });

});