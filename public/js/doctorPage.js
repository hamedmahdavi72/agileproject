/**
 * Created by HamedMahdavi on 12/31/2016.
 */
var app = angular.module('doctorProfile', ["ngRoute","headerModule","dateConvertorModule", "checklist-model"]);
var user_lastname;
var insuranceCompanies = [];
var insuranceIDs = [];
app.controller('panel',function ($scope, $http, $filter,convertDate) {
    $scope.days = [];
    for(var i = 0 ; i < 31; i++){
        $scope.days[i] = i+1;
    }
    $scope.months = [];

    for(var i = 0 ; i < 12; i++){
        $scope.months[i] = i+1;
    }

    $scope.years = [];

    $scope.years[0] = '1395';
    $scope.years[1] = '1396';


    $scope.appointments = [];
    $scope.acceptedAppointments = [];

    $scope.loadAppointments = function () {
        $http.get("/doctor/appointmentRequests")
            .then(function (response) {
                     // console.log(response.data);
                    $scope.appointments = response.data;
                    for(var i = 0 ; i < $scope.appointments.length; i++){
                        for(var j = 0; j < $scope.appointments[i].appointmentInterval.length; j++){

                            //setting interval jalali dates
                            tempDate = new Date($scope.appointments[i].appointmentInterval[j].date);
                            intervalJalaliDate = convertDate.gregorianToJalali(tempDate.getFullYear(),
                                tempDate.getMonth()+1,tempDate.getDate());
                            $scope.appointments[i].appointmentInterval[j].jalaliDate =intervalJalaliDate[0]+"/"+intervalJalaliDate[1]+
                            "/"+intervalJalaliDate[2];

                        }

                        //setting isHandled
                        $scope.appointments[i].isHandled = false;
                    }

                }
            );
    };

    $scope.loadAcceptedAppointments=function () {
        $http.get("/doctor/getDoctorAppointments")
            .then(function (response) {
                $scope.acceptedAppointments = response.data;

                for(var i = 0 ; i < $scope.acceptedAppointments.length; i++){
                    tempDate = new Date($scope.acceptedAppointments[i].appointmentDate);
                    // console.log(tempDate);
                    intervalJalaliDate = convertDate.gregorianToJalali(tempDate.getFullYear(),
                        tempDate.getMonth()+1,tempDate.getDate(), tempDate.getHours());
                    $scope.acceptedAppointments[i].appointmentDate = intervalJalaliDate[0]+"/"+intervalJalaliDate[1]+
                        "/"+intervalJalaliDate[2]+" --- زمان: "+tempDate.getHours()+":"+tempDate.getMinutes();
                    // console.log(new Date(2017, 0, 1, 7, 0, 0, 0));
                }

            });
    };

    $scope.sendAppointment = function(index,selectedYear,selectedMonth,selectedDay,selectedTime){
      var gregorianDate = convertDate.jalaliToGregorian(selectedYear,selectedMonth,selectedDay);
        var date = new Date(gregorianDate[0],gregorianDate[1] - 1,gregorianDate[2],selectedTime.getHours(),selectedTime.getMinutes(), 0, 0);
        var appointmentData = {
            "date": date.getTime(),
            "id": $scope.appointments[index].id,
            "customerUsername": $scope.appointments[index].customerUsername
        };

       $http.post("/doctor/acceptAppointmentRequest/",appointmentData).success(function(data, status) {
           $scope.appointments[index].isHandled = true;
           $scope.loadAcceptedAppointments();

        })


    };

    $scope.loadAcceptedAppointments();
    $scope.loadAppointments();
});

app.controller('edit',function ($scope, $http, $window) {
    $scope.hideErrorPassword = true;
    $scope.hideErrorNationalId = true;

    $scope.insuranceCompanies = [];
    $scope.slist =[];
    $http.get("/getUser")
        .then(function (response) {$scope.slist = response.data;
                $scope.firstNameValue = $scope.slist.firstName;
                $scope.lastNameValue = $scope.slist.lastName;
                $scope.mobileNumberValue = $scope.slist.mobileNumber;
                console.log($scope.slist);
                user_lastname = $scope.slist.lastName;
            }
        );

    $scope.list =[];

    $http.get("/doctors/info")
        .then(function (response) {$scope.list = response.data;
            insuranceCompanies = $scope.list.supportedInsuranceCompanies;
            $scope.insuranceCompanies = insuranceCompanies;
            console.log(insuranceCompanies[0]);

                for (var i = 0 ; i < 4 ; i++){
                    if(insuranceCompanies[i] == '"پارسیان"'){
                        insuranceIDs.push(1);
                    }
                    if(insuranceCompanies[i] == '"سپه"'){
                        insuranceIDs.push(2);
                    }
                    if(insuranceCompanies[i] == '"تامین اجتماعی"'){
                        insuranceIDs.push(3);
                    }
                    if(insuranceCompanies[i] == '"بانک صادرات"'){
                        insuranceIDs.push(4);
                    }
                }
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

app.controller('callDoc', function($scope, $http) {
    $http.get("/getUser")
        .then(function (response) {$scope.slist = response.data;
                user_lastname = $scope.slist.lastName;
                console.log(user_lastname);
                $scope.lastName = user_lastname;
            }
        );
});

app.controller('DemoCtrl', function($scope, $http) {
    $scope.roles = [
        {id: 1, text: 'پارسیان'},
        {id: 2, text: 'سپه'},
        {id: 3, text: 'تامین اجتماعی'},
        {id: 4, text: 'بانک صادرات'}
    ];
    $scope.user = {
        roles: []
    };



    $scope.user.roles = insuranceIDs;

    $scope.checkAll = function() {
        $scope.user.roles = $scope.roles.map(function(item) { return item.id; });
    };
    $scope.uncheckAll = function() {
        $scope.user.roles = [];
    };
    $scope.checkFirst = function() {
        $scope.user.roles.splice(0, $scope.user.roles.length);
        $scope.user.roles.push(1);
    };
    
    $scope.saveInsurance = function () {
        var insurances = [];
        for(var i = 0 ; i < $scope.user.roles.length; i++){
            insurances.push($scope.roles[$scope.user.roles[i]-1].text);
        }
        console.log(insurances);

        $http({
            url: '/saveInsurance/',
            method: "POST",
            data: insurances
        })
            .then(function (response) {
                    //console.log(response.data);
                    if (response.data != null) {
                    }
                },
                function (response) { // optional
                    // failed
                });
    }

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