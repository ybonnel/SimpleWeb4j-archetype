#set( $symbol_dollar = '$' )

function MainCtrl($scope, $http, $log) {
    $http.get("/hello").success(function(data) {
        $scope.hello = data.value;
    });
}
MainCtrl.$inject = [ '${symbol_dollar}scope', '${symbol_dollar}http', '${symbol_dollar}log' ];

angular.module('${artifactid}', []).config(['${symbol_dollar}routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    $routeProvider.otherwise({redirectTo: '/main'});
}]);