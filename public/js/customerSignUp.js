/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('signup', []);

app.controller('headerLoginController', function($scope,$http) {

    $http.get("/isLoggedIn").then(function (response) {
        $scope.isLoggedIn = response.data;
    });
});


app.controller('signupapp', function($scope, $http, $window) {
    $scope.hideError = true;
    $scope.hideErrorPassword = true;
    $scope.hideErrorNationalId = true;
    $scope.hideErrorFirstName = true;
    $scope.hideErrorLastName = true;
    $scope.hideErrorEmail = true;

    $scope.send = function () {

        $scope.user = new Object();
        $scope.user.username = $scope.username;
        $scope.user.password = $scope.pass;
        $scope.user.mobileNumber = $scope.mobileNumber;
        $scope.user.nationalId = $scope.nationalId;
        $scope.user.firstName = $scope.firstName;
        $scope.user.lastName = $scope.lastName;
        $scope.user.email = $scope.email;

        //console.log($scope.user);


        $scope.hideError = true;
        $scope.hideErrorPassword = true;
        $scope.hideErrorNationalId = true;
        $scope.hideErrorFirstName = true;
        $scope.hideErrorLastName = true;
        $scope.hideErrorEmail = true;

        $http({
            url: '/signup/',
            method: "POST",
            data: $scope.user
        })
            .then(function(response) {
                    //console.log(response.data);
                    if(response.data != null){
                        //console.log(response.data);

                        if(response.data.username != null){
                            $scope.hideError = false;
                            $scope.Errorvalue = response.data.username[0];
                        }

                        if(response.data.password != null){
                            $scope.hideErrorPassword = false;
                            $scope.ErrorvaluePassword = response.data.password[0];
                        }

                        if(response.data.nationalId != null){
                            $scope.hideErrorNationalId = false;
                            $scope.ErrorvalueNationalId = response.data.nationalId[0];
                        }

                        if(response.data.firstName != null){
                            $scope.hideErrorFirstName = false;
                            $scope.ErrorvalueFirstName = response.data.firstName[0];
                        }

                        if(response.data.lastName != null){
                            $scope.hideErrorLastName = false;
                            $scope.ErrorvalueLastName = response.data.lastName[0];
                        }

                        if(response.data.email != null){
                            $scope.hideErrorEmail = false;
                            $scope.ErrorvalueEmail = response.data.email[0];
                        }

                        if(response.data.signupmsg != null){
                            if(response.data.signupmsg[0] == "redirect"){
                                $window.location.href = "/profile";
                            }
                        }
                    }
                },
                function(response) { // optional
                    // failed
                });
    }

});