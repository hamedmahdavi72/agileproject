/**
 * Created by HamedMahdavi on 12/31/2016.
 */
var app = angular.module('doctorProfile', ["ngRoute","headerModule"]);

app.controller('panel',function ($scope, $http, $filter) {
    $scope.appointments = [];
    $scope.acceptedAppointments = [];


    $scope.loadAppointments = function () {
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
    };

    $scope.loadAcceptedAppointments=function () {
        $http.get("/doctor/getDoctorAppointments")
            .then(function (response) {
                $scope.acceptedAppointments = response.data;
                // console.log($scope.acceptedAppointments);
                for(var i = 0 ; i < $scope.acceptedAppointments.length; i++){
                    $scope.acceptedAppointments[i].appointmentDate = $filter('date')($scope.acceptedAppointments[i].appointmentDate);
                }
                // console.log($scope.appointments[0].appointmentInterval[0].fromHour);
            });
    };

    $scope.loadAcceptedAppointments();
    $scope.loadAppointments();
});

app.controller('edit',function ($scope, $http, $window) {
    $scope.hideErrorPassword = true;
    $scope.hideErrorNationalId = true;

    $scope.slist =[];
    $http.get("/getUser")
        .then(function (response) {$scope.slist = response.data;
                $scope.firstNameValue = $scope.slist.firstName;
                $scope.lastNameValue = $scope.slist.lastName;
                $scope.mobileNumberValue = $scope.slist.mobileNumber;
            }
        );

    $scope.canSend = false;

    $scope.send = function () {

        $scope.user = new Object();
        $scope.user.password = $scope.password;
        $scope.user.confirmPassword = $scope.confirmPassword;
        $scope.user.mobileNumber = $scope.mobileNumber;
        $scope.user.firstName = $scope.firstName;
        $scope.user.lastName = $scope.lastName;

        console.log($scope.user);


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

                            if(response.data.docedit != null){
                                var x = response.data.docedit;
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
    };
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
            templateUrl : "/doctorAppointments",
        })
        .when("/acceptedAppointmentRequest", {
            restrict : 'A',
            templateUrl : "/acceptedAppointmentRequest"
        });
});