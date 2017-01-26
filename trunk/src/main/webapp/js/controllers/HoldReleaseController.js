app.controller('HoldReleaseController', ['$scope', '$http',  '$cookies', 'documentGetter', 'userInfo', function($scope, $http, $cookies, documentGetter, userInfo) {
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
	
	$scope.postForm = function(data) {
		var formData = {
			"UserType": "Student",
			"Email": $cookies.get('SpartaDocsUser'),
			"DocName" : "Computer Science Hold Release Form",
			"FileData" : angular.toJson(data)
		}
		var response = $http.post("rest/document/create", angular.toJson(formData));
		response.success(function(respData, status, headers, config) {
			window.location.href = '#/dashboard.html';
			alert("Your Hold Release Form has been created.");
		});
		response.error(function(respData, status, headers, config) {
			alert("Submitting form failed!");
		});
	};
	
	$scope.userData = userInfo.getAllUserInfo();

  $scope.emptyForm = 	{  
		   "name":"",
		   "id":"",
		   "semester":"",
		   "currentCourses":[  
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "grade":"",
		         "ge":"",
		         "units":""
		      }
		   ],
		   "nextSemesterCourses":[  
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      },
		      {  
		         "name":"",
		         "reqs":"",
		         "term":"",
		         "ge":"",
		         "units":""
		      }
		   ]
		};
  
  var docGetterPromise = documentGetter.getFormContents();
  docGetterPromise.then(function(result) {  
	  if(result.status === 500) {
		  $scope.data = $scope.emptyForm;
	  }
	  else {
		  $scope.data = result.data;
	  }
   });
}]);
