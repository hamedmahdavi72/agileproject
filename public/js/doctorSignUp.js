/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('drSignUp', []);
app.controller('drSignUpController', function($scope, $http, $window) {

    $scope.hideErrorPassword = true;
    $scope.hideErrorFirstName = true;
    $scope.hideErrorLastName = true;
    $scope.hideErrorEmail = true;
    $scope.hideErrorMedicalOrgId = true;
    $scope.hideErrorSpeciality = true;
    $scope.hideErrorClinicAddress = true;
    $scope.hideErrorClinicPhoneNumber = true;

    $scope.send = function () {


        $scope.hideErrorPassword = true;
        $scope.hideErrorFirstName = true;
        $scope.hideErrorLastName = true;
        $scope.hideErrorEmail = true;
        $scope.hideErrorMedicalOrgId = true;
        $scope.hideErrorSpeciality = true;
        $scope.hideErrorClinicAddress = true;
        $scope.hideErrorClinicPhoneNumber = true;

        $scope.doctor = new Object();
        if($scope.lastName == null){

            $scope.hideErrorLastName = false;
            $scope.hideErrorFirstName = false;
        }
        else if($scope.lastName.split(" ").length > 0){
            $scope.doctor.firstName = $scope.lastName.split(" ")[0];
        }
        else if($scope.lastName.split(" ").length >= 1){
            $scope.doctor.lastName = $scope.lastName.split(" ")[1];;
        }
        $scope.doctor.password = $scope.password;
        $scope.doctor.mobileNumber = $scope.mobileNumber;
        $scope.doctor.email = $scope.email;
        $scope.doctor.medicalOrgId = $scope.medicalOrgId;
        $scope.doctor.speciality = $scope.speciality;
        $scope.doctor.clinicAddress = $scope.clinicAddress;
        $scope.doctor.clinicPhoneNumber = $scope.clinicPhoneNumber;


        //console.log($scope.doctor);



        $http({
            url: '/doctorSignup/',
            method: "POST",
            data: $scope.doctor
        })
            .then(function(response) {
                    //console.log(response.data);
                    if(response.data != null){
                       // console.log(response.data);
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

                        if(response.data.clinicPhoneNumber != null){
                            $scope.hideErrorClinicPhoneNumber = false;
                            $scope.ErrorvalueClinicPhoneNumber = response.data.clinicPhoneNumber[0];
                        }

                        if(response.data.clinicAddress != null){
                            $scope.hideErrorClinicAddress = false;
                            $scope.ErrorvalueClinicAddress = response.data.clinicAddress[0];
                        }

                        if(response.data.speciality != null){
                            $scope.hideErrorSpeciality = false;
                            $scope.ErrorvalueSpeciality = response.data.speciality[0];
                        }

                        if(response.data.medicalOrgId != null){
                            $scope.hideErrorMedicalOrgId = false;
                            $scope.ErrorvalueMedicalOrgId = response.data.medicalOrgId[0];
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