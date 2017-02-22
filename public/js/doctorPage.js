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

    $scope.hasFiltered = false;
    $scope.selectedDay = $scope.days[0];
    $scope.selectedMonth = $scope.months[0];
    $scope.selectedYear = $scope.years[0];

    $scope.loadAppointments = function () {
        $http.get("/doctor/appointmentRequests")
            .then(function (response) {
                     console.log(response.data);
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
                    var tempDate = new Date($scope.acceptedAppointments[i].appointmentDate);
                    $scope.acceptedAppointments[i].appointmentDate = tempDate;
                    var intervalJalaliDate = convertDate.gregorianToJalali(tempDate.getFullYear(),
                        tempDate.getMonth()+1,tempDate.getDate(), tempDate.getHours());
                    $scope.acceptedAppointments[i].appointmentJalaliDate = intervalJalaliDate[0]+"/"+intervalJalaliDate[1]+
                        "/"+intervalJalaliDate[2]+" --- زمان: "+tempDate.getHours()+":"+tempDate.getMinutes();
                    // console.log(new Date(2017, 0, 1, 7, 0, 0, 0));
                }

                $scope.filterAppointments(false,0,0,0);

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

    $scope.filterAppointments = function(hasFiltered, selectedYear,selectedMonth,selectedDay){
        if (!hasFiltered)
            $scope.filteredAcceptedAppointments = $scope.acceptedAppointments;
        else{
            var gregDateArray = convertDate.jalaliToGregorian(selectedYear, selectedMonth, selectedDay);
            $scope.filteredAcceptedAppointments = [];
            for(var i = 0 ; i < $scope.acceptedAppointments.length; i++){
                if(gregDateArray[0] == $scope.acceptedAppointments[i].appointmentDate.getFullYear() &&
                    gregDateArray[1] == ($scope.acceptedAppointments[i].appointmentDate.getMonth()+1) &&
                    gregDateArray[2] == $scope.acceptedAppointments[i].appointmentDate.getDate() )
                    $scope.filteredAcceptedAppointments.push($scope.acceptedAppointments[i]);

            }

        }
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
                // console.log($scope.slist);
                user_lastname = $scope.slist.lastName;
            }
        );

    $scope.list =[];

    $http.get("/doctors/info")
        .then(function (response) {$scope.list = response.data;
            insuranceCompanies = $scope.list.supportedInsuranceCompanies;
            $scope.insuranceCompanies = insuranceCompanies;
            // console.log(insuranceCompanies);

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

        // console.log($scope.user);


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
                // console.log(user_lastname);
                $scope.lastName = user_lastname;
            }
        );
});

app.controller('customerMessages', function($scope, $http) {
    $http.get("/getMessages")
        .then(function (response) {$scope.messages = response.data;
            }
        );
});

app.controller('patients', function($scope, $http, convertDate) {

    var temp_patients = [];
    $http.get("/doctorPatients")
        .then(function (response) {temp_patients = response.data;
            for(var i = 0 ; i < temp_patients.length ; i++){
                    for(var j = 0 ; j < temp_patients[i].appointmentsDate.length; j++){
                        tempDate = new Date(temp_patients[i].appointmentsDate[j]);
                        intervalJalaliDate = convertDate.gregorianToJalali(tempDate.getFullYear(),
                            tempDate.getMonth()+1,tempDate.getDate());
                        temp_patients[i].appointmentsDate[j] = intervalJalaliDate[0]+"/"+intervalJalaliDate[1]+
                            "/"+intervalJalaliDate[2]+" --- زمان: "+tempDate.getHours()+":"+tempDate.getMinutes();
                    }
                }
                $scope.patients = temp_patients;
        }
        );
});

app.controller('issueController', function($scope, $http, convertDate) {
    $scope.subjects = ["ورود و خروج به حساب کاربری", "دیگر", "ملاقات های تایید شده", "رزرو وقت"];
    $scope.response = false;

    $scope.reportIssue = function () {

        $scope.response = false;

        var issue = new Object();

        issue.issueReport = $scope.issueDesc;
        issue.subject = $scope.issueSubject;
        issue.issueDate = new Date();
        var persianDate = convertDate.gregorianToJalali(issue.issueDate.getFullYear(),
            issue.issueDate.getMonth()+1,issue.issueDate.getDate());
        issue.issueDate = persianDate[0]+"/"+persianDate[1]+
            "/"+persianDate[2]+" --- زمان: "+issue.issueDate.getHours()+":"+issue.issueDate.getMinutes();

        var jsonObjectIssue = JSON.stringify(issue);

        $http({
            url: '/issueReport/',
            method: "POST",
            data: jsonObjectIssue
        })
            .then(function (response) {
                    //console.log(response.data);
                    if (response.data != null) {
                        $scope.response = true;
                    }
                },
                function (response) { // optional
                    // failed
                });
    }
});


app.controller('advertise', function($scope, $http) {
    $scope.ads =["10هزار تا کلیک - یک میلیون تومان", "30هزار کلیک - 3 میلیون تومان", "50هزار کلیک - 5 میلیون تومان"];
    $scope.advertiseModel = $scope.ads[0];

    $scope.requestAdvertise = function () {
            var adPlan = new Object();
            adPlan.adPlan = $scope.advertiseModel;

            // var string = "{ 'adPlan' : " + "'"+adPlan+"'}";
            console.log(JSON.stringify(adPlan));

            $http({
                url: '/sendAdRequest/',
                method: "POST",
                data: JSON.stringify(adPlan)
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
        // console.log(insurances);

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
        })
        .when("/patients", {
            restrict : 'A',
            templateUrl : "/patients"
        })
        .when("/issues", {
            restrict : 'A',
            templateUrl : "/issues"
        })
        .when("/messages", {
            restrict : 'A',
            templateUrl : "/messages"
        })
        .when("/advertise", {
            restrict : 'A',
            templateUrl : "/advertise"
        });
});