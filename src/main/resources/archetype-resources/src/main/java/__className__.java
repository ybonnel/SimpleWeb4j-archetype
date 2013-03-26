#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;
#if( ${withHibernate} == true)
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
#end

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.*;

/**
 * Main class.
 */
public class ${className} {

    /**
     * Object return by route.
     */
#if( ${withHibernate} == true)
    @Entity
#end
    public static class Hello {
#if( ${withHibernate} == true)
        @Id
        @GeneratedValue
        public Long id;
#end

        public String value;

#if( ${withHibernate} == true)
        public static SimpleEntityManager<Hello, Long> simpleEntityManager = new SimpleEntityManager<>(Hello.class);
#end
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


        // Insert datas
#if( ${withHibernate} == true)
        SimpleEntityManager.openSession().beginTransaction();
        Hello hello = new Hello();
        hello.value = "Hello World!";
        Hello.simpleEntityManager.save(hello);
        SimpleEntityManager.getCurrentSession().getTransaction().commit();
        SimpleEntityManager.closeSession();
#end

        // Declare the route "/hello" for GET method whith no param in request payload.
        get(new Route<Void, Hello>("/hello", Void.class) {
            @Override
            public Response<Hello> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
#if( ${withHibernate} == true)
                return new Response<>(Hello.simpleEntityManager.getAll().iterator().next());
#else
                Hello hello = new Hello();
                hello.value = "Hello World!";
                return new Response<>(hello);
#end
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
     * @return port to use
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
