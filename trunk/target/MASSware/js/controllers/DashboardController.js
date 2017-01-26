app.controller('DashboardController', ['$scope', '$cookies', 'userInfo', function($scope, $cookies, userInfo) {
	console.log("Dashboard controller loaded");
	$scope.logout = function() {
		console.log("before logout " + $cookies.get("SpartaDocsUser"));
		$cookies.remove("SpartaDocsUser");
		$cookies.remove("SpartaDocsFName");
		$cookies.remove("SpartaDocsLName");
		if ($cookies.get("SpartaDocsUser") == null) {
			window.location.href = "#/login.html";
		} else {
			alert("logout failed");
		}
	};
	
	
	$scope.userData = userInfo.getAllUserInfo();
}]);
