app.controller('GraduationFormController', ['$scope', '$http',  '$cookies', 'documentGetter', 'userInfo', function($scope, $http, $cookies, documentGetter, userInfo) {
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
		console.log("here");
		var formData = {
			"UserType": "Student",
			"Email": $cookies.get('SpartaDocsUser'),
			"DocName" : "Computer Science Graduation Form",
			"FileData" : angular.toJson(data)
		}
		var response = $http.post("rest/document/create", angular.toJson(formData));
		response.success(function(respData, status, headers, config) {
			window.location.href = '#/dashboard.html';
			alert("grad form success");
		});
		response.error(function(respData, status, headers, config) {
			alert("Submitting form failed!");
		});
//		console.log(angular.toJson($scope.data));
	};


	$scope.userData = userInfo.getAllUserInfo();

	
  $scope.emptyForm = 	{
    name: '',
    id: '',
    semester: '',
    address: '',
    gradDate: '',
    major: '',
    minor: '',
    majorBegan: '',
    requiredCourses: [
      {
        "name": "CS 46A",
        "college": "SJSU",
        "units": "4",
        "grade": ""
      },
      {
        "name": "CS 46B",
        "college": "SJSU",
        "units": "4",
        "grade": ""
      },
      {
        "name": "CS 47",
        "college": "SJSU",
        "units": "4",
        "grade": ""
      },
      {
        "name": "CS 146",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 147",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 149",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 151",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 152",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 154",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "CS 160",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      }
    ],
    majorElectives: [
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      },
      {
        "name": "",
        "college": "SJSU",
        "units": "3",
        "grade": ""
      }
    ],
    majorSupport:
    [
    {
      "name": "MATH 30/30P",
      "college": "SJSU",
      "units": "",
      "grade": ""
    },
    {
      "name": "MATH 31",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "MATH 42",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "MATH 129A",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "MATH (32 | 142 | 161A)",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "Science Elective 1",
      "college": "SJSU",
      "units": "",
      "grade": ""
    },
    {
      "name": "Science Elective 2",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "CS 100W",
      "college": "SJSU",
      "units": "3",
      "grade": ""
    },
    {
      "name": "PHIL 134",
      "college": "SJSU",
      "units": "3",
      "grade": ""
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