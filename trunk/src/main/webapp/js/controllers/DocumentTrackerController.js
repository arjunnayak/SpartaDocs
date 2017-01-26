app.controller('DocumentTrackerController', ['$scope', 'documentHistory', '$cookies', '$location', 'documentGetter', 'userInfo', function($scope, documentHistory, $cookies, $location, documentGetter, userInfo) {
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
	
	documentHistory.success(function(data) {
		$scope.data = data;
//		console.log(angular.toJson(data));
	});
	$scope.go = function(DocID, Date, DocName) {
		var userEmail = $cookies.get('SpartaDocsUser');
//		console.log(userEmail);
//		console.log(DocID);
//		console.log(Date);
		documentGetter.setParameters(DocID, Date);
		if(DocName.includes("Grad")) {
			$location.path("/graduation_form.html");
		}
		else {
			$location.path("/holdrelease_form.html");
		}

	};

	$scope.userData = userInfo.getAllUserInfo();
}]);
