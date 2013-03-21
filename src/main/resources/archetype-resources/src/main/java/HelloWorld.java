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

    /**
     * Get the port.
     * <ul>
     *     <li>Heroku : System.getenv("PORT")</li>
     *     <li>Cloudbees : System.getProperty("app.port")</li>
     *     <li>default : 9999</li>
     * </ul>
     * @return
     */
    private static int getPort() {
        // Heroku
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }

        // Cloudbees
        String cloudbeesPort = System.getProperty("app.port");
        if (cloudbeesPort != null) {
            return Integer.parseInt(cloudbeesPort);
        }

        // Default port;
        return 9999;
    }

    public static void main(String[] args) {
        // For main, we want to wait the stop.
        startServer(getPort(), true);
    }
}
