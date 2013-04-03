#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
#if( ${withHibernate} == true)
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;
#end
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ${className}Test {

    private int port;

    @Before
    public void setup() {
        port = Integer.getInteger("test.http.port", 9999);
        ${className}.startServer(port, false);
#if( ${withHibernate} == true)
        SimpleEntityManager.openSession().beginTransaction();
        for (Hello hello : Hello.simpleEntityManager.getAll()) {
            Hello.simpleEntityManager.delete(hello.getId());
        }
        SimpleEntityManager.getCurrentSession().getTransaction().commit();
        SimpleEntityManager.closeSession();
#else
        HelloResource.entities.clear();
#end
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void should_return_no_hello() {
        List<Hello> hellos = new Gson().fromJson(HttpRequest.get("http://localhost:" + port + "/hello").body(), new TypeToken<List<Class>>(){}.getType());
        assertTrue(hellos.isEmpty());
    }

    public Long insertHello() {
        Hello hello = new Hello();
        hello.setName("name");
        Gson gson = new Gson();
        assertEquals(201, HttpRequest.post("http://localhost:" + port + "/hello").send(gson.toJson(hello)).code());

        List<Hello> hellos = gson.fromJson(HttpRequest.get("http://localhost:" + port + "/hello").body(), new TypeToken<List<Hello>>(){}.getType());
        assertEquals(1, hellos.size());
        assertEquals("name", hellos.get(0).getName());
        return hellos.get(0).getId();
    }

    @Test
    public void should_create_and_get_by_id() {
        long id = insertHello();

        Hello newHello = new Gson().fromJson(HttpRequest.get("http://localhost:" + port + "/hello/" + id).body(), Hello.class);
        assertNotNull(newHello);
        assertEquals(id, newHello.getId().longValue());
        assertEquals("name", newHello.getName());
    }

    @Test
    public void should_update() {
        long id = insertHello();
        Gson gson = new Gson();

        Hello newHello = new Hello();
        newHello.setName("newName");

        assertEquals(204, HttpRequest.put("http://localhost:" + port + "/hello/" + id).send(gson.toJson(newHello)).code());

        List<Hello> hellos = gson.fromJson(HttpRequest.get("http://localhost:" + port + "/hello").body(), new TypeToken<List<Hello>>(){}.getType());
        assertEquals(1, hellos.size());
        assertEquals("newName", hellos.get(0).getName());
    }

    @Test
    public void should_delete() {
        long id = insertHello();
        Gson gson = new Gson();

        assertEquals(204, HttpRequest.delete("http://localhost:" + port + "/hello/" + id).code());
        List<Hello> hellos = gson.fromJson(HttpRequest.get("http://localhost:" + port + "/hello").body(), new TypeToken<List<Hello>>(){}.getType());
        assertTrue(hellos.isEmpty());
    }

}
