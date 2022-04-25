angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555';

    $scope.showMyOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.MyOrders = response.data;
        });
    };

    $scope.calculateSumOfOneOrder = function (order){
        var totalSum = 0;
        for (let i = 0; i < order.products.length; i++) {
            totalSum += $scope.calculateSumOfOneOrderPosition(order.products[i]);
        }
        return totalSum;
    }
    $scope.calculateSumOfOneOrderPosition = function (product){
        return product.orderedProductPrice * product.quantity;
    }

    $scope.showMyOrders();
});
