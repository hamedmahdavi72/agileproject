/**
 * Created by hamed on 12/20/16 AD.
 */
var app = angular.module('admin',['dateConvertorModule']);
app.controller('admin-ctr',function($scope,$http, $window, convertDate){
   $scope.objects = [];
   $scope.current = [];
   $scope.issues =[];

   var issueIds =[];

    $http.get("/getIssues")
        .then(function (response) {$scope.issues = response.data;});

    $http.get("/getIssuesId")
        .then(function (response) {issueIds = response.data;});

    $http.get("/getDoctors")
        .then(function (response) {$scope.objects = response.data.pending ; $scope.current = response.data.current});


    $scope.solve = function(customerUsername, solveMessage, subject, index){

        var issueSolved = new Object();
        issueSolved.customerUsername = customerUsername;
        issueSolved.issueReport = solveMessage;
        issueSolved.subject = subject;
        issueSolved.objectId = issueIds[index];
        issueSolved.issueDate = new Date();
        var persianDate = convertDate.gregorianToJalali(issueSolved.issueDate.getFullYear(),
            issueSolved.issueDate.getMonth()+1,issueSolved.issueDate.getDate());
        issueSolved.issueDate = persianDate[0]+"/"+persianDate[1]+
            "/"+persianDate[2]+" --- زمان: "+issueSolved.issueDate.getHours()+":"+issueSolved.issueDate.getMinutes();
        console.log(JSON.stringify(issueSolved));

       $http({
            url: '/solveIssue/',
            method: "POST",
            data: JSON.stringify(issueSolved)
        })
            .then(function (response) {
                console.log(response);
                if(response.data != null)
                    $window.location.href = "adminPanel";
            });
    };

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