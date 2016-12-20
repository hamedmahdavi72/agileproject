/**
 * Created by hamed on 12/20/16 AD.
 */
var app = angular.module('login', []);
app.controller('app', function($scope, $http, $location, $window) {
    $scope.hideError = true;
    $scope.send = function () {

        $scope.user = new Object();
        $scope.user.username = $scope.username;
        $scope.user.password = $scope.pass;

        console.log($scope.user);

        $http({
            url: '/admin/',
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
                        if(response.data.adminmsg != null){
                            console.log(response.data);
                            if(response.data.adminmsg == 'redirect'){
                                console.log("redirecting... ");
                                $window.location.href = '/adminPanel';
                            }
                        }
                    }
                },
                function(response) { // optional
                    // failed
                });
    }

});