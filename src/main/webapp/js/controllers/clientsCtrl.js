/**
 * Created by homer on 14.09.14.
 */


app.controller('clientsCtrl', function ($scope, $http) {

    $http.post('/rest/admin/clients').success(function (data) {
        $scope.clients = data;
    });

    $scope.sendReward = function (client) {
        $http.post('/rest/admin/sendreward', {uuid: client.uuid}).success(function () {
            alert("Reward sent sucessfully");
            client.rewardSent = true;
        }).error(function (status, statusText) {
                alert(status + " " + statusText);
            });
    };

    $scope.postBulletin = function(client){
        $http.post('/rest/admin/post', {uuid: client.uuid}).success(function () {
            alert("Posted successfully");
            client.posted = true;
        }).error(function (status, statusText) {
                alert(status + " " + statusText);
            });
    };

});