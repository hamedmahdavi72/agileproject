/**
 * Created by hamed on 12/20/16 AD.
 */
var app = angular.module('admin',[]);
app.controller('admin-ctr',function($scope,$http, $window){
   $scope.objects = [];
   $scope.current = [];
   $scope.issues =[];

    $http.get("/getIssues")
        .then(function (response) {$scope.issues = response.data;});

    $http.get("/getDoctors")
        .then(function (response) {$scope.objects = response.data.pending ; $scope.current = response.data.current});
    
    $scope.rejectDoctor=function (email) {
        $http({
            url: '/rejectDoctor/',
            method: "POST",
            data: JSON.stringify(email)
        })
            .then(function (response) {
                console.log(response);
                if(response.data == "removed")
                    $window.location.href = "adminPanel";
            });
    };

    $scope.acceptDoctor=function (email) {
        $http({
            url: '/acceptDoctor/',
            method: "POST",
            data: JSON.stringify(email)
        })
            .then(function (response) {
                console.log(response);
                if(response.data == "accepted")
                    $window.location.href = "adminPanel";
            });
    };

    $scope.deleteDoctor=function (email) {
        $http({
            url: '/deleteDoctor/',
            method: "POST",
            data: JSON.stringify(email)
        })
            .then(function (response) {
                console.log(response);
                if(response.data == "deleted")
                    $window.location.href = "adminPanel";
            });
    };

});