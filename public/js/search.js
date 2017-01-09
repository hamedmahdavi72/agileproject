/**
 * Created by ARYA on 12/23/2016.
 */
var app = angular.module('search',["ngRoute","headerModule"]);




app.controller('searchapp',function($scope,$http, $window){

    $scope.places= [];
    $scope.noResultsHide = true;
    $scope.resulthide = true;

    for(var i = 0 ; i < 22 ; i++){
        $scope.places[i] = i+1;
    }

    $scope.goToDoctor = function(id){
        // console.log(id);
        var uri = '/doctors/page/'+id;
        // console.log(uri);
        $http({
            url: uri,
            method: "GET"
        })
            .then(function (response) {
                    if (response.data != null) {
                        $window.location.href=uri;
                        // console.log(response.data);
                    }
                    //console.log(response.data);
                },
                function (response) { // optional
                    // failed
                });
    };

    $scope.search = function () {

        $scope.resulthide = true;

        $scope.searchQuery = new Object();
        $scope.canSearch = true;
        if($scope.firstName == null)
            $scope.searchQuery.firstName = "";
        else
            $scope.searchQuery.firstName = $scope.firstName;

        if($scope.lastName == null)
            $scope.searchQuery.lastName = "";
        else
            $scope.searchQuery.lastName = $scope.lastName;

        if($scope.speciality == null)
            $scope.searchQuery.speciality = "";
        else
            $scope.searchQuery.speciality = $scope.speciality;

        if($scope.areaName == null){
            $scope.canSearch = false;
            $scope.error = "مقدار فیلد مکان نمی تواند خالی باشد.";
        }
        else {
            $scope.canSearch = true;
            $scope.searchQuery.areaName = $scope.areaName;
        }

       // console.log($scope.searchQuery);

        if($scope.canSearch){
            $http({
                url: '/search/',
                method: "POST",
                data: $scope.searchQuery
            })
                .then(function (response) {
                        if (response.data != null) {
                           // console.log(response.data.results);
                            if(response.data.results.length > 0){
                                $scope.resulthide = false;
                                $scope.noResultsHide = true;
                                $scope.results = response.data.results;
                            }
                        else {
                                $scope.resulthide = true;
                                $scope.noResultsHide= false;
                                $scope.noResults = "موردی یافت نشد.";
                            }
                        }
                        //console.log(response.data);
                    },
                    function (response) { // optional
                        // failed
                    });
        }
    }
});