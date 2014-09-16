/**
 * Created by homer on 12.09.14.
 */


app.controller('bulletinListCtrl', function ($scope, $http) {
    $scope.maxSize = 5;
    //$scope.bulletins = [];
    $http.get('/rest/bulletins').success(function(data){
        $scope.bulletins = data.listResponse;

        $scope.currentPage = getPageNumber(data.size, data.offset);
        $scope.itemsPerPage = data.itemsPerPage;
        $scope.totalItems = data.itemsCount;
        console.log(data);

    });
    console.log($scope.bulletins);
    $scope.pageChanged = function(){
        var bounds = getBoundsFromPageNumber($scope.currentPage, $scope.itemsPerPage);

        $http.get('/rest/bulletins/'+bounds.offset+'/'+bounds.size).success(function(data){
            $scope.bulletins = data.listResponse;

            $scope.currentPage = getPageNumber(data.size, data.offset);
            $scope.itemsPerPage = data.itemsPerPage;
            $scope.totalItems = data.itemsCount;

        });
    };

    });
