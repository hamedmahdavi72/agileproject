/**
 * Created by HamedMahdavi on 12/31/2016.
 */
var app = angular.module('doctorProfile', ["ngRoute","headerModule"]);

app.controller('panel',function ($scope, $http, $filter) {
    $scope.appointments = [];
    $scope.acceptedAppointments = [];

    $http.get("/doctor/appointments")
        .then(function (response) {
                // console.log(response.data);
                $scope.appointments = response.data;
                for(var i = 0 ; i < $scope.appointments.length; i++){
                    for(var j = 0; j < $scope.appointments[i].appointmentInterval.length; j++){
                        $scope.appointments[i].appointmentInterval[j].date = $filter('date')($scope.appointments[i].appointmentInterval[j].date, "dd/MM/yyyy");
                    }
                }
                // console.log($scope.appointments[0].appointmentInterval[0].fromHour);
            }
        );

    $http.get("/doctor/getDoctorAppointments")
        .then(function (response) {
                $scope.acceptedAppointments = response.data;
                for(var i = 0 ; i < $scope.acceptedAppointments.length; i++){
                    $scope.acceptedAppointments[i].date = $filter('date')($scope.acceptedAppointments[i].date);
                }
                // console.log($scope.appointments[0].appointmentInterval[0].fromHour);
        })

});

app.config(function($routeProvider) {
    $routeProvider
        .when("/docInfo", {
            restrict : 'A',
            templateUrl : "/docInfo"
        })
        .when("/docCalendar", {
        restrict : 'A',
        templateUrl : "/docCalendar"
        })
        .when("/doctorAppointments", {
            restrict : 'A',
            templateUrl : "/doctorAppointments"
        })
        .when("/acceptedAppointmentRequest", {
            restrict : 'A',
            templateUrl : "/acceptedAppointmentRequest"
        });
});