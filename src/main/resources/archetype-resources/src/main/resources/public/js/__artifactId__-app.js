#set( $symbol_dollar = '$' )

angular.module('${artifactId}',
        ['${artifactId}-services'])
    .config(['${symbol_dollar}routeProvider',function(${symbol_dollar}routeProvider) {
        ${symbol_dollar}routeProvider
            .when('/main', {
                templateUrl: 'partial/main.html',
                controller: MainCtrl
            })
            .when('/edit/:id', {
                templateUrl: 'partial/editOrNew.html',
                controller: EditCtrl
            })
            .when('/new', {
                templateUrl: 'partial/editOrNew.html',
                controller: NewCtrl
            })
            .otherwise({
                redirectTo: '/main'
            })
    }]);