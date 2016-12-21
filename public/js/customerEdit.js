/**
 * Created by ARYA on 12/21/2016.
 */
/**
 * Created by ARYA on 12/20/2016.
 */
var app = angular.module('edit', []);
app.controller('editapp', function($scope, $http) {

    $scope.hideErrorPassword = true;
    $scope.hideErrorNationalId = true;
    $scope.hideErrorFirstName = true;
    $scope.hideErrorLastName = true;

    $scope.canSend = false;

    $scope.send = function () {

        $scope.user = new Object()
        $scope.user.password = $scope.password;
        $scope.user.confirmPassword = $scope.confirmPassword;
        $scope.user.mobileNumber = $scope.mobileNumber;
        $scope.user.nationalId = $scope.nationalId;
        $scope.user.firstName = $scope.firstName;
        $scope.user.lastName = $scope.lastName;

        //console.log($scope.user);


        $scope.hideError = true;
        $scope.hideErrorPassword = true;
        $scope.hideErrorNationalId = true;
        $scope.hideErrorFirstName = true;
        $scope.hideErrorLastName = true;

        if ($scope.confirmPassword != $scope.password) {
            alert("Passwords Do not Match!")
        }
        else $scope.canSend = true;

        if (canSend) {
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

                            if (response.data.firstName != null) {
                                $scope.hideErrorFirstName = false;
                                $scope.ErrorvalueFirstName = response.data.firstName[0];
                            }

                            if (response.data.lastName != null) {
                                $scope.hideErrorLastName = false;
                                $scope.ErrorvalueLastName = response.data.lastName[0];
                            }
                        }
                    },
                    function (response) { // optional
                        // failed
                    });
        }
    }

});