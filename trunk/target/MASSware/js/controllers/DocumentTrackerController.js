app.controller('DocumentTrackerController', ['$scope', 'documentHistory', '$cookies', '$location', 'documentGetter', 'userInfo', function($scope, documentHistory, $cookies, $location, documentGetter, userInfo) {
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
