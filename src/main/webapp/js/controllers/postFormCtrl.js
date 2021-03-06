/**
 * Created by homer on 13.09.14.
 */

app.controller('postFormCtrl', function ($scope, $http) {

    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
    $scope.paymentOpen = false;
    $scope.paymentExpanded = function(){
        alert(isExpanded);
    };
    $scope.priceList = [];
    $http.get('/rest/prices').success(function(data){
        $scope.priceList = data;
        console.log(data);
    });
    $scope.planChosen = false;
    $scope.hasReward = false;
    $scope.price = 0.00;
    $scope.selectedNewPlan = function(){
        resetForm();
        if($scope.plan != null){
            $scope.planChosen = true
            $scope.hasReward = $scope.plan.reward;
            $scope.price = $scope.plan.price;
        } else {
            $scope.planChosen = false;
        }
    };

    var validTitle = function(title){
        return title.length <= 48 && title.length >= 4;

    };
    $scope.tooLongText = false;
    $scope.showSymbolCount = false;
    $scope.lengthControl = function(){
        $scope.showSymbolCount = true;
        if($scope.text.length > $scope.plan.amountOfSymbols){
            $scope.tooLongText = true;
        } else {
            $scope.tooLongText = false;
        }
        $scope.symbolsRemaining = $scope.plan.amountOfSymbols - $scope.text.length;

    };
    $scope.correctTitle = false;
    $scope.validateTitle = function(){
        $scope.correctTitle = validTitle($scope.title);
    };

    var resetForm = function(){
        $scope.tooLongText = false;
        $scope.showSymbolCount = false;
        $scope.correctTitle = false;
        $scope.title = '';
        $scope.text = '';
        $scope.price = 0.00;
    };

});
