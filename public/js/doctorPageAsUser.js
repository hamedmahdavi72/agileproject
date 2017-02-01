/**
 * Created by HamedMahdavi on 1/7/2017.
 */
var app = angular.module('drPageAsUser', ["ngRoute","headerModule","dateConvertorModule"]);

app.controller('reserve', function($scope, $http, $window, convertDate) {

    $scope.canAdd = true;
    $scope.days = [];

    for(var i = 0 ; i < 31; i++){
        $scope.days[i] = i+1;
    }

    $scope.months = ['فروردین', 'اردیبهشت', 'خرداد', 'تیر', 'مرداد',
                    'شهریور', 'مهر', 'آبان', 'آذر', 'دی',
                    'بهمن', 'اسفند'];
    $scope.years = [];

    $scope.years[0] = '1395'; //new Date().getYear();
    $scope.years[1] = '1396'; //new Date().getYear() + 1;

    $scope.selectedDay = $scope.days[0];
    $scope.selectedMonth = $scope.months[0];
    $scope.selectedYear = $scope.years[0];

    $scope.reserved = true;
    $scope.message = false;
    var cursor = 0;
    var long = 0;
    var doctorUsername = $window.location.pathname.split("/")[3];
    $scope.choices = [{date:"",name:"false",id: 'درخواست1'}];

    $scope.appointments = [];
    $scope.saveChoice = function(choice, year, month, day) {
        if(choice.from != null && choice.to != null && choice.date != null){
            //console.log(choice);
            var numericMonth = 0;
            switch (month) {
                case 'فروردین':
                    numericMonth = 1;
                    break;
                case 'اردیبهشت':
                    numericMonth = 2;
                    break;
                case 'خرداد':
                    numericMonth = 3;
                    break;
                case 'تیر':
                    numericMonth = 4;
                    break;
                case 'مرداد':
                    numericMonth = 5;
                    break;
                case 'شهریور':
                    numericMonth = 6;
                    break;
                case 'مهر':
                    numericMonth = 7;
                    break;
                case 'آبان':
                    numericMonth = 8;
                    break;
                case 'آذر':
                    numericMonth = 9;
                    break;
                case 'دی':
                    numericMonth = 10;
                    break;
                case 'بهمن':
                    numericMonth = 11;
                    break;
                case 'اسمفند':
                    numericMonth = 12;
                    break;
                default:
                    numericMonth = 0;

            }

            var gregorianDate = convertDate.jalaliToGregorian(parseInt(year),numericMonth,parseInt(day));

            choice.jalaliDate = year + "/"+numericMonth+"/"+day;
            choice.date = new Date(gregorianDate[1]+"/"+gregorianDate[2]+"/"+gregorianDate[0]);
            $scope.appointment = new Object();
            $scope.appointment.fromHour = choice.from;
            $scope.appointment.toHour = choice.to;
            $scope.appointment.date = choice.date.getTime();
            $scope.appointments.push($scope.appointment);

            choice.name = false;
            cursor = cursor + 1;
        }
    };

    $scope.addNewChoice = function() {
        if($scope.choices.length <= 2){
            $scope.hide = false;
            var newItemNo = $scope.choices.length+1;
            $scope.choices.push({'id':'درخواست'+newItemNo,'name': 'false', 'date':""});
            if($scope.choices.length == 3)
                $scope.canAdd = false;
        }
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



});

app.controller('doctorInfoController', function($scope,$http,$window) {

    // $scope.days = [];
    // $scope.day_selected = $scope.days[0];
    console.log();
    var username = $window.location.pathname.split("/")[3];
   $http.get("/doctors/info/"+username).then(function (response) {
        $scope.firstName = response.data.firstName;
        $scope.lastName = response.data.lastName;
        $scope.speciality = response.data.speciality;
        $scope.clinicAddress = response.data.clinicAddress;
        $scope.clinicPhoneNumber = response.data.clinicPhoneNumber;
        $scope.supportedInsuranceCompanies = response.data.supportedInsuranceCompanies;
    });
});
