/**
 * Created by ARYA on 12/23/2016.
 */
var app = angular.module('search',[]);
app.controller('searchapp',function($scope,$http){

    $scope.search = function () {

        $scope.searchQuery = new Object();
        $scope.searchQuery.firstName = $scope.firstName;
        $scope.searchQuery.lastName = $scope.lastName;
        $scope.searchQuery.speciality = $scope.speciality;
        $scope.searchQuery.place = $scope.place;

        console.log($scope.searchQuery);

        $http({
            url: '/search/',
            method: "POST",
            data: $scope.searchQuery
        })
            .then(function (response) {
                    //console.log(response.data);
                    if (response.data != null) {
                        console.log(response.data.results);
                    }
                },
                function (response) { // optional
                    // failed
                });
    }
});