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
        })
        .when("/issues", {
            restrict : 'A',
            templateUrl : "/issues"
        })
        .when("/messages", {
            restrict : 'A',
            templateUrl : "/messages"
        });
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

app.controller('customerMessages', function($scope, $http) {
    $http.get("/getMessages")
        .then(function (response) {$scope.messages = response.data;
            }
        );
});


app.controller('customerAppointments', function($scope, $http, convertDate) {

    $scope.acceptedAppointments = [];
    $http.get("/customer/appointments")
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
            }
        );
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