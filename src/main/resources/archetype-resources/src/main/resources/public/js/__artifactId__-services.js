#set( $symbol_dollar = '$' )

var services = angular.module('${artifactId}-services', ['ngResource']);


services.factory('HelloService', function(${symbol_dollar}resource) {
    return ${symbol_dollar}resource('/hello/:id',
        {id: "@id"},
        {update: {method:'PUT'}}
    );
});