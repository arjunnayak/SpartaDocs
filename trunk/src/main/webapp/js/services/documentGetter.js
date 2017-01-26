app.service('documentGetter', ['$http', '$cookies', function($http, $cookies) {
	var parameters = {};

	var setParameters = function(DocID, Date) {
		parameters["DocID"]=DocID;
		parameters["Date"]=Date;
	};

	var getParameters = function(){
		return parameters;
	};

	var getFormContents = function () {
		var email = $cookies.get('SpartaDocsUser');
		var docID = parameters.DocID;
		var date = parameters.Date;
		
		// reset variables so they don't get used multiple times
		parameters["DocID"]=null;
		parameters["Date"]=null;
		
		var URL = 'http://localhost:8080/MASSware/rest/document/retrieveversion?Email='+ email + '&DocID='+ docID + '&Date='+ date;
		return $http({
			method: 'GET',
			url: URL,
			params: { Email : $cookies.get("SpartaDocsUser") }
		})
		.then(function(data, status, headers, config) {
			return angular.fromJson(data);
		})
		.catch(function(data){
//			console.log("got an error in initial processing",e);
//			throw e; // rethrow to not marked as handled, 
			return data;
		})

	}

	return {
		setParameters: setParameters,
		getParameters: getParameters,
		getFormContents: getFormContents
	};

}]);