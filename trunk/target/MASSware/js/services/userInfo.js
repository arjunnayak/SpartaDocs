app.service('userInfo', ['$cookies', function($cookies) {

	var getEmail = function() {
		return $cookies.get("SpartaDocsUser");
	};
	
	var getFName = function() {
		return $cookies.get("SpartaDocsFName");
	};
	
	var getLName = function() {
		return $cookies.get("SpartaDocsLName");
	};
	
	var getAllUserInfo = function () {
		var allInfo = {};
		allInfo["email"]=this.getEmail();
		allInfo["FName"]=this.getFName();
		allInfo["LName"]=this.getLName();
		
		return allInfo;
	};

	return {
		getEmail: getEmail,
		getFName: getFName,
		getLName: getLName,
		getAllUserInfo: getAllUserInfo
	};

}]);