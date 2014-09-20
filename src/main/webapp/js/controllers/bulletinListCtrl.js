/**
 * Created by homer on 12.09.14.
 */


app.controller('bulletinListCtrl', function ($scope, $http, $routeParams, $location) {
    $scope.maxSize = 5;
    $scope.approved = false;
    $scope.canceled = false;
    $scope.alert = null;
    if(typeof $routeParams.orderStatus != undefined){
        if($routeParams.orderStatus == 'approved'){
            $scope.approved = true;
            $scope.alert = {type: 'success', msg:'Your order was successfully completed!'};
        } else if($routeParams.orderStatus == 'canceled') {
            $scope.alert = {type: 'danger', msg:'You have canceled your order!'};
            $scope.canceled = true;
        }
    }

    $scope.closeAlert = function(){
        $scope.approved = false;
        $scope.canceled = false;
        $location.path('/');
    };
    $http.get('/rest/bulletins').success(function(data){
        $scope.bulletins = data.listResponse;

        $scope.currentPage = getPageNumber(data.size, data.offset);
        $scope.itemsPerPage = data.itemsPerPage;
        $scope.totalItems = data.itemsCount;

    }).error(function(data){
            alert(data.errorDesc);
        });
    $scope.pageChanged = function(){
        var bounds = getBoundsFromPageNumber($scope.currentPage, $scope.itemsPerPage);

        $http.get('/rest/bulletins/'+bounds.offset+'/'+bounds.size).success(function(data){
            $scope.bulletins = data.listResponse;

            $scope.currentPage = getPageNumber(data.size, data.offset);
            $scope.itemsPerPage = data.itemsPerPage;
            $scope.totalItems = data.itemsCount;

        }).error(function(data){
                alert(data.errorDesc);
            });
    };

    });
