/**
 * Created by hamed on 12/20/16 AD.
 */
var app = angular.module('admin',[]);
app.controller('admin-ctr',function($scope,$http){
   $scope.objects = [];
    $http.get("/getDoctors")
        .then(function (response) {$scope.objects = response.data.pending ;});
    //console.log(response.data);
});