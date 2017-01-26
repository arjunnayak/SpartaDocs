var app = angular.module('SpartaDocs', ['ngRoute', 'ngCookies', 'angularCSS']);

app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		controller: "LoginController",
		templateUrl: "views/login.html",
		css: "css/login.css"
	})
	.when("/dashboard.html", {
		controller: "DashboardController",
		templateUrl: "views/dashboard.html",
		css: ["css/style.css", "css/bootstrap.min.css"]
	})
	.when("/signup.html", {
		controller: "SignupController",
		templateUrl: "views/signup.html"
	})
	.when("/documentTracker.html", {
		controller: "DocumentTrackerController",
		templateUrl: "views/documentTracker.html",
		css: ["css/bootstrap.min.css", "css/font-awesome.min.css", "css/style.css"]
	})
	.when("/holdrelease_form.html", {
		controller: "HoldReleaseController",
		templateUrl: "views/holdrelease_form.html",
		css: ["css/bootstrap.min.css", "css/font-awesome.min.css", "css/holdreleaseform.css", "css/style.css"]
	})
	.when("/graduation_form.html", {
		controller: "GraduationFormController",
		templateUrl: "views/graduation_form.html",
		css: ["css/bootstrap.min.css", "css/font-awesome.min.css", "css/graduationform.css", "css/style.css"]
	})
	.when("/graduationform.html", {
		controller: "GraduationFormController",
		templateUrl: "views/graduationform.html",
		css: "css/gradform.css"
	})
	.otherwise({
		redirectTo: "/"
	});
});