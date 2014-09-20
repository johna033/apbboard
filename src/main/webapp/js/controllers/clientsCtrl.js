/**
 * Created by homer on 14.09.14.
 */


app.controller('clientsCtrl', function ($scope, $http, $modal) {

    $http.post('/rest/admin/clients').success(function (data) {
        $scope.clients = data;
    }).error(function(data){
            alert(data.errorDesc);
        });

    $scope.sendReward = function (client) {
       var modalInstance = $modal.open({
            templateUrl: './templates/modal/sendRewardModal.html',
            controller: ModalInstanceCtrl,
            size: '',
            resolve: {
                client: function () {
                    return client;
                }
            }
        });
      /*  modalInstance.result.then.(function(client){
            client.rewardSent = true;
        },function(){});*/
    };
    $scope.postBulletin = function(client){
        $http.post('/rest/admin/post', {uuid: client.uuid}).success(function () {
            alert("Posted successfully");
            client.posted = true;
        }).error(function(data){
                alert(data.errorDesc);
            });
    };

});

var ModalInstanceCtrl = function ($scope, $http, $modalInstance, client) {

    $scope.client = client;

    $scope.send = function (rewardCode) {
        $http.post('/rest/admin/sendreward', {uuid: client.uuid, giftCode:rewardCode}).success(function () {
            alert("Reward sent sucessfully");
            $modalInstance.close(client);
        }).error(function(data){
                alert(data.errorDesc);
            });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};