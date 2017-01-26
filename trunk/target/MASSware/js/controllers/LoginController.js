app.controller('LoginController', ['$scope', '$http', '$cookies', function($scope, $http, $cookies) {
	this.postForm = function() {
		$http({
			method: 'POST',
			url: 'rest/login',
			data: angular.toJson({
				"email" : this.inputData.email,
				"password" : CryptoJS.MD5(this.inputData.password).toString(),
				"userType" : this.inputData.userType
			}, false),
			headers: {'Content-Type': 'text/plain'}
		})
		.success(function(data, status, headers, config) {
			var resp = angular.fromJson(data);
			$cookies.put('SpartaDocsUser', resp.Email);
			$cookies.put('SpartaDocsFName', resp.FName);
			$cookies.put('SpartaDocsLName', resp.LName);
			console.log(resp);
			window.location.href = '#/dashboard.html';
		})
		.error(function(data, status, headers, config) {
			$scope.errorMsg = "Incorrect login";
			console.log(data);
		})
	}
}]);
