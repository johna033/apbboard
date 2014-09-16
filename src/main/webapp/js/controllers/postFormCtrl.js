/**
 * Created by homer on 13.09.14.
 */

app.controller('postFormCtrl', function ($scope, $http, $location) {

    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
    $scope.paymentOpen = false;
    $scope.paymentExpanded = function () {
        alert(isExpanded);
    };

    $scope.priceList = [];
    $http.get('/rest/prices').success(function (data) {
        $scope.priceList = data;
        console.log(data);
    });

    $scope.postBulletin = function () {
        if($scope.email == null || $scope.email == ''){
            alert("Fill out email correctly please");
            return;
        }
        $http.post('/rest/paypal/pay', {email: $scope.email, payment: $scope.plan.price, numberOfSymbols: $scope.plan.amountOfSymbols, text: $scope.text, title: $scope.title}).success(function (data) {
           window.location.href = data.link;
        }).error(function (statusText) {
                alert(statusText);
            });
    };

    $scope.planChosen = false;
    $scope.hasReward = false;
    $scope.price = 0.00;
    $scope.selectedNewPlan = function () {
        resetForm();
        if ($scope.plan != null) {
            $scope.planChosen = true;
            $scope.hasReward = $scope.plan.reward;
            $scope.price = $scope.plan.price;
        } else {
            $scope.planChosen = false;
        }
    };

    var validTitle = function (title) {
        return title.length <= 48 && title.length >= 4;

    };
    $scope.tooLongText = false;
    $scope.showSymbolCount = false;
    $scope.lengthControl = function () {
        $scope.showSymbolCount = true;
        $scope.tooLongText = $scope.text.length > $scope.plan.amountOfSymbols;
        $scope.symbolsRemaining = $scope.plan.amountOfSymbols - $scope.text.length;

    };
    $scope.correctTitle = false;
    $scope.validateTitle = function () {
        $scope.correctTitle = validTitle($scope.title);
    };

    var resetForm = function () {
        $scope.tooLongText = false;
        $scope.showSymbolCount = false;
        $scope.correctTitle = false;
        $scope.title = '';
        $scope.text = '';
        $scope.price = 0.00;
    };

});
