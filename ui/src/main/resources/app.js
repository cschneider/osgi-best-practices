(function() {
    var app = angular.module('myApp', []);
    var tasksBase = "/tasks";
    app.factory("Post", function($resource) {
	return $resource(tasksBase);
    });
    app.controller('TaskController', function($scope, $http) {
        $scope.task = {};
    	$scope.reload = function() {
            $http.get(tasksBase)
            .success(function (response) {
                $scope.tasks = response;
            });
         };
    
         $scope.put = function() {
            var toSend = $scope.task;
            $http.put(tasksBase + "/" + $scope.task.id, toSend)
            .success(function (response) {$scope.reload();});
	    
            this.task = {};
        };

        $scope.delete = function(id) {
            $http.delete(tasksBase + "/"+ id)
            .success(function (response) {$scope.reload();});
            $scope.task = {};
        }

        $scope.select = function(id) {
            $scope.task = $scope.tasks[id];
        }

        $scope.newTask = function(id) {
            $scope.task = {};
        }
        $scope.reload();
    });
})();
