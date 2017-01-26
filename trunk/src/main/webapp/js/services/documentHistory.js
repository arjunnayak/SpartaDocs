app.factory('documentHistory', ['$http', '$cookies', function($http, $cookies) {
	return $http({
		method: 'GET',
		url: 'http://localhost:8080/MASSware/rest/document/retrieveall',
		params: { Email : $cookies.get("SpartaDocsUser") }
	})
	.success(function(data, status, headers, config) {
//		console.log("Successfully retreived all document history");
		var resp = angular.fromJson(data);
//		console.log(angular.toJson(resp));
		return angular.toJson(resp);
	})
	.error(function(data, status, headers, config) {
		console.log("error");
		return data;
	})
}]);
