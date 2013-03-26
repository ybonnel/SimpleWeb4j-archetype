#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.junit.Assert.assertEquals;

public class ${className}Test {

    private int port;

    @Before
    public void setup() {
        port = Integer.getInteger("test.http.port", 9999);
        ${className}.startServer(port, false);
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void testHelloWorldService() {
        ${className}.Hello hello = new Gson().fromJson(HttpRequest.get("http://localhost:" + port + "/hello").body(), ${className}.Hello.class);
        assertEquals("Hello World!", hello.value);
    }

}
