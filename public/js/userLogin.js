/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('login', []);

app.controller('headerLoginController', function($scope,$http) {

    $http.get("/isLoggedIn").then(function (response) {
        $scope.isLoggedIn = response.data;
    });
});

app.controller('app', function($scope, $http, $location, $window) {
    $scope.hideError = true;
    $scope.hideErrorPassword = true;
    $scope.send = function () {

        $scope.hideError = true;
        $scope.hideErrorPassword = true;

        $scope.user = new Object();
        $scope.user.username = $scope.username;
        $scope.user.password = $scope.pass;
        //console.log($scope.user);
        $http({
            url: '/login/',
            method: "POST",
            data: $scope.user
        })
            .then(function(response) {
                    if(response.data != null){
                        if(response.data.username != null && response.data.username.length > 0){
                            $scope.hideError = false;
                            //console.log(response.data.username[0])
                            $scope.Errorvalue = response.data.username[0];
                        }

                        if(response.data.password != null && response.data.password.length > 0){
                            $scope.hideErrorPassword = false;
                            $scope.ErrorvaluePassword = response.data.password[0];
                        }

                        if(response.data.loginmsg != null){
                            console.log(response.data)
                            if(response.data.loginmsg == 'redirect'){
                                //console.log("redirecting... ")
                                $window.location.href = '/profile';
                            }
                        }
                    }
                },
                function(response) { // optional
                    // failed
                });
    }
});