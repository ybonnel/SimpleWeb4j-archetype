#set( $symbol_dollar = '$' )

function MainCtrl(${symbol_dollar}scope, ${symbol_dollar}http, ${symbol_dollar}log) {
    ${symbol_dollar}http.get("/hello").success(function(data) {
        ${symbol_dollar}scope.hello = data.value;
    });
}
MainCtrl.$inject = [ '${symbol_dollar}scope', '${symbol_dollar}http', '${symbol_dollar}log' ];

angular.module('${artifactid}', []).config(['${symbol_dollar}routeProvider', function($routeProvider) {
    ${symbol_dollar}routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    ${symbol_dollar}routeProvider.otherwise({redirectTo: '/main'});
}]);