/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('drSignUp', []);
app.controller('drSignUpController', function($scope, $http, $window) {

    $scope.send = function () {
        $scope.doctor = new Object();
        $scope.doctor.firstName = $scope.lastName.split(" ")[0];
        $scope.doctor.lastName = $scope.lastName.split(" ")[1];;
        $scope.doctor.password = $scope.password;
        $scope.doctor.mobileNumber = $scope.mobileNumber;
        $scope.doctor.email = $scope.email;
        $scope.doctor.medicalOrgId = $scope.medicalOrgId;
        $scope.doctor.speciality = $scope.speciality;
        $scope.doctor.clinicAddress = $scope.clinicAddress;


        console.log($scope.doctor);



        $http({
            url: '/doctorSignup/',
            method: "POST",
            data: $scope.doctor
        })
            .then(function(response) {
                    //console.log(response.data);
                    if(response.data != null){
                        console.log(response.data);
                        if(response.data.password != null){
                            $scope.hideErrorPassword = false;
                            $scope.ErrorvaluePassword = response.data.password[0];
                        }
                    //
                        if(response.data.firstName != null){
                            $scope.hideErrorFirstName = false;
                            $scope.ErrorvalueFirstName = response.data.firstName[0];
                        }
                    //
                        if(response.data.lastName != null){
                            $scope.hideErrorLastName = false;
                            $scope.ErrorvalueLastName = response.data.lastName[0];
                        }
                    //
                        if(response.data.email != null){
                            $scope.hideErrorEmail = false;
                            $scope.ErrorvalueEmail = response.data.email[0];
                        }

                        if(response.data.signupmsg !=null){
                            if(response.data.signupmsg[0] == "redirect"){
                                $window.location.href = '/';
                            }
                        }
                    }
                },
                function(response) { // optional
                    // failed
                });


    }

});