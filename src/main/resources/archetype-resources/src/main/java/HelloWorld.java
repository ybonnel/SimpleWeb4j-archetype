#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.*;

/**
 * Main class.
 */
public class HelloWorld {

    /**
     * Object return by route.
     */
    public static class Hello {
        public String value = "Hello World";
    }

    /**
     * Start the server.
     * @param port http port to listen.
     * @param waitStop true to wait the stop.
     */
    public static void startServer(int port, boolean waitStop) {
        // Set the http port.
        setPort(port);
        // Set the path to static resources.
        setPublicResourcesPath("/${packageInPathFormat}/public");

        // Declare the route "/hello" for GET method whith no param in request payload.
        get(new Route<Void, Hello>("/hello", Void.class) {
            @Override
            public Response<Hello> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
                return new Response<>(new Hello());
            }
        });

        // Start the server.
        start(waitStop);
    }

    public static void main(String[] args) {
        // Default port 9999.
        // For main, we want to wait the stop.
        startServer(9999, true);
    }
}
