#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.junit.Assert.assertEquals;

public class HelloWorldTest {

    private int port;

    @Before
    public void setup() {
        port = Integer.getInteger("test.http.port", 9999);
        HelloWorld.startServer(port, false);
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void testHelloWorldService() {
        assertEquals("{${symbol_escape}"value${symbol_escape}":${symbol_escape}"Hello World${symbol_escape}"}", HttpRequest.get("http://localhost:" + port + "/hello").body());
    }

}
