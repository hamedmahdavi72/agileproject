/**
 * Created by ARYA on 12/21/2016.
 */
/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('customerProfile', ["ngRoute","headerModule","dateConvertorModule"]);

app.config(function($routeProvider) {
    $routeProvider
        .when("/customerInfo", {
            restrict : 'A',
            templateUrl : "/customerInfo"
        })
        .when("/customerAppointments", {
            restrict : 'A',
            templateUrl : "/customerAppointments"
        });
});




app.controller('editapp', function($scope, $http, $window) {

    $scope.hideErrorPassword = true;
    $scope.hideErrorNationalId = true;

    $scope.slist =[];
    $http.get("/getUser")
        .then(function (response) {$scope.slist = response.data;
            $scope.firstNameValue = $scope.slist.firstName;
            $scope.lastNameValue = $scope.slist.lastName;
            $scope.mobileNumberValue = $scope.slist.mobileNumber;
            $scope.nationalIdValue = $scope.slist.nationalId;
            }
        );

    $scope.canSend = false;

    $scope.send = function () {

        $scope.user = new Object();
        $scope.user.password = $scope.password;
        $scope.user.confirmPassword = $scope.confirmPassword;
        $scope.user.mobileNumber = $scope.mobileNumber;
        $scope.user.nationalId = $scope.nationalId;
        $scope.user.firstName = $scope.firstName;
        $scope.user.lastName = $scope.lastName;

        //console.log($scope.user);


        $scope.hideErrorPassword = true;
        $scope.hideErrorNationalId = true;

        if ($scope.confirmPassword != $scope.password) {
            $scope.hideErrorPassword = false;
            $scope.ErrorvaluePassword = "گذرواژه ها یکسان نیستند.";
        }
        else $scope.canSend = true;


        if ($scope.canSend) {
            $http({
                url: '/edit/',
                method: "POST",
                data: $scope.user
            })
                .then(function (response) {
                        //console.log(response.data);
                        if (response.data != null) {
                            //console.log(response.data);

                            if (response.data.password != null) {
                                $scope.hideErrorPassword = false;
                                $scope.ErrorvaluePassword = response.data.password[0];
                            }

                            if (response.data.nationalId != null) {
                                $scope.hideErrorNationalId = false;
                                $scope.ErrorvalueNationalId = response.data.nationalId[0];
                            }

                            if(response.data.cusedit != null){
                                var x = response.data.cusedit;
                                //console.log(x[0]);
                                if(x[0] == "Edits Successfully Applied!"){
                                    $window.location.href = "/profile";
                                }
                            }
                        }
                    },
                    function (response) { // optional
                        // failed
                    });
        }
    }

});