
var app = angular.module('headerLoginApp', []);
app.controller('headerLoginController', function($scope,$http) {

    $http.get("/isLoggedIn").then(function (response) {
        $scope.isLoggedIn = response.data;
    });
});






