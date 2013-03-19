#set( $symbol_dollar = '$' )

/**
 * Declare MainCtrl, this controller does a GET on "/hello" and put the result in scope.
 */
function MainCtrl(${symbol_dollar}scope, ${symbol_dollar}http) {
    ${symbol_dollar}http.get("/hello").success(function(data) {
        ${symbol_dollar}scope.hello = data.value;
    });
}
MainCtrl.$inject = [ '${symbol_dollar}scope', '${symbol_dollar}http' ];

/**
 * Declare the routes.
 * Route /main (#/main in browser) use the controller MainCtrl with template main.html
 */
angular.module('${artifactId}', []).config(['${symbol_dollar}routeProvider', function($routeProvider) {
    ${symbol_dollar}routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    ${symbol_dollar}routeProvider.otherwise({redirectTo: '/main'});
}]);