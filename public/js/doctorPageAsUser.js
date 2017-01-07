/**
 * Created by HamedMahdavi on 1/7/2017.
 */
var app = angular.module('drPageAsUser', ["ngRoute","headerModule"]);
app.controller('doctorInfoController', function($scope,$http,$window) {

    console.log($window.infoURI);
   $http.get($window.infoURI).then(function (response) {
        $scope.firstName = response.data.firstName;
        $scope.lastName = response.data.lastName;
        $scope.speciality = response.data.speciality;
        $scope.clinicAddress = response.data.clinicAddress;
        $scope.clinicPhoneNumber = response.data.clinicPhoneNumber;
        $scope.supportedInsuranceCompanies = response.data.supportedInsuranceCompanies;
    });
});
