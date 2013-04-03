#set( $symbol_dollar = '$' )



function MainCtrl(${symbol_dollar}scope, HelloService) {
    ${symbol_dollar}scope.hellos = HelloService.query();
    ${symbol_dollar}scope.delete = function(hello) {
        ${symbol_dollar}('#delete' + hello.id).modal('hide');
        HelloService.delete({id:hello.id}, function(data){
            ${symbol_dollar}scope.hellos = HelloService.query();
        });
    }
}

function EditCtrl(${symbol_dollar}scope, ${symbol_dollar}routeParams, ${symbol_dollar}location, HelloService) {
    ${symbol_dollar}scope.hello = HelloService.get({id:${symbol_dollar}routeParams.id});
    ${symbol_dollar}scope.submitMessage = "Update hello";
    ${symbol_dollar}scope.saveHello = function(hello) {
        if (${symbol_dollar}scope.form.${symbol_dollar}invalid) {
            ${symbol_dollar}scope.form.name.${symbol_dollar}dirty = true;
        } else {
            HelloService.update(hello, function(data) {
                ${symbol_dollar}location.path('/main');
            });
        }
    };
}

function NewCtrl(${symbol_dollar}scope, ${symbol_dollar}location, HelloService) {
    ${symbol_dollar}scope.submitMessage = "Save hello";
    ${symbol_dollar}scope.saveHello = function(hello) {
        if (${symbol_dollar}scope.form.${symbol_dollar}invalid) {
            ${symbol_dollar}scope.form.name.${symbol_dollar}dirty = true;
        } else {
            HelloService.save(hello, function(data) {
                ${symbol_dollar}location.path('/main');
            });
        }
    };
}


