app.controller('SignupController', ['$scope', '$http', '$cookies', function($scope, $http, $cookies) {
	this.postForm = function() {
		alert("Thanks for signing up, you should receive your verification email soon.");
		window.location.href = '/MASSware/';
		$http({
			method: 'POST',
			url: 'rest/signup',
			data: angular.toJson({
				"FName" : this.inputData.fname,
				"LName" : this.inputData.lname,
				"SID" : this.inputData.sid,
				"Major" : this.inputData.major,
				"Email" : this.inputData.email,
				"Password" : CryptoJS.MD5(this.inputData.password).toString(),
				"userType" : this.inputData.userType
			}, false),
			headers: {'Content-Type': 'text/plain'}
		})
		.success(function(data, status, headers, config) {
			console.log(data);
		})
		.error(function(data, status, headers, config) {
			$scope.errorMsg = "Incorrect sign up";
		})
	}
}]);
