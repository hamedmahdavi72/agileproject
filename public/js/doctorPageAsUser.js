/**
 * Created by HamedMahdavi on 1/7/2017.
 */
var app = angular.module('drPageAsUser', ["ngRoute","headerModule"]);

app.controller('reserve', function($scope, $http, $window) {


    $scope.days = [];

    for(var i = 0 ; i < 31; i++){
        $scope.days[i] = i+1;
    }

    $scope.months = ['فروردین', 'اردیبهشت', 'خرداد', 'تیر', 'مرداد',
                    'شهریور', 'مهر', 'آبان', 'آذر', 'دی',
                    'بهمن', 'اسفند'];
    $scope.years = [];

    $scope.years[0] = new Date().getYear();
    $scope.years[1] = new Date().getYear() + 1;

    $scope.reserved = true;
    $scope.message = false;
    var cursor = 0;
    var long = 0;
    var doctorUsername = $window.infoURI.split("/")[3];
    $scope.choices = [{date:"",name:"false",id: 'درخواست1'}];

    $scope.appointments = [];
    $scope.saveChoice = function(choice) {
        if(choice.from != null && choice.to != null && choice.date != null){
            //console.log(choice);
            $scope.appointment = new Object();
            $scope.appointment.fromHour = choice.from;
            $scope.appointment.toHour = choice.to;
            $scope.appointment.date = choice.date;
            $scope.appointments.push($scope.appointment);
            $scope.dateToMillisecond(choice.date, cursor);

            choice.name = false;
            cursor = cursor + 1;
        }
    };

    $scope.addNewChoice = function() {
        $scope.hide = false;
        var newItemNo = $scope.choices.length+1;
        $scope.choices.push({'id':'درخواست'+newItemNo,'name': 'false', 'date':""});
    };

    $scope.removeChoice = function() {
        var lastItem = $scope.choices.length-1;
        $scope.choices.splice(lastItem);
        $scope.appointments.splice(lastItem);
    };

    $scope.send = function () {
        if($scope.appointments.length > 0){
            //console.log($scope.appointments);
            $scope.reserved = false;
            $scope.message = true;

            $scope.appointmentRequest = new Object();
            $scope.appointmentRequest.intervals = $scope.appointments;
            $scope.appointmentRequest.doctorUsername = doctorUsername;

            console.log($scope.appointmentRequest);

            $http({
                url: '/saveAppointments/',
                method: "POST",
                data: angular.toJson($scope.appointmentRequest)
            })
                .then(function(response) {
                    if(response.data != null){
                        $scope.reservedMessage = "درخواست رزرو با موفقیت ثبت و ارسال شد.";
                        }
                    },
                    function(response) { // optional
                        // failed
                    });
        }
    };

    $scope.dateToMillisecond = function(date, cursor){
        console.log(date);
        $http({
            url: '/convert/',
            method: "POST",
            data: JSON.stringify(date)
        })
            .then(function(response) {
                    if(response.data != null){
                        long = response.data;
                        $scope.appointments[cursor].date = long;
                        console.log($scope.appointments[cursor]);//.date=long;
                        return long;
                        }
                },
                function(response) { // optional
                    // failed
                });
    };

});

app.controller('doctorInfoController', function($scope,$http,$window) {

    // $scope.days = [];
    // $scope.day_selected = $scope.days[0];
   $http.get($window.infoURI).then(function (response) {
        $scope.firstName = response.data.firstName;
        $scope.lastName = response.data.lastName;
        $scope.speciality = response.data.speciality;
        $scope.clinicAddress = response.data.clinicAddress;
        $scope.clinicPhoneNumber = response.data.clinicPhoneNumber;
        $scope.supportedInsuranceCompanies = response.data.supportedInsuranceCompanies;
    });
});
