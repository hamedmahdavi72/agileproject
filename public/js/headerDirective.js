/**
 * Created by HamedMahdavi on 12/27/2016.
 */

var module = angular.module("headerModule",[]);
app.controller('headerLoginController', function($scope,$http) {

    $http.get("/isLoggedIn").then(function (response) {
        $scope.isLoggedIn = response.data;
    });
});

app.directive('header', function () {
    return {
        restrict: 'A',
        templateUrl: "/base"

    }
});