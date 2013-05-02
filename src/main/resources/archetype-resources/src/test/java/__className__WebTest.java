#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

#if( ${withHibernate} == true)
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;
#end
import fr.ybonnel.simpleweb4j.test.SimpleWeb4jTest;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.fest.assertions.Assertions.assertThat;

public class ${className}WebTest extends SimpleWeb4jTest {

    @Before
    public void setup() {
        ${className}.startServer(getPort(), false);
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
        goTo("/");
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void should_not_have_hello() {
        assertThat(find("tbody tr")).isEmpty();
    }

    private void insertHello(String nameOfHello) {
        click("#addHello");
        fill("#name").with(nameOfHello);
        click("#submit");
    }

    @Test
    public void can_insert_hello() {
        insertHello("name");
        FluentList<FluentWebElement> trInTbody = find("tbody tr");
        assertThat(trInTbody).hasSize(1);
        FluentWebElement oneHello = trInTbody.get(0);
        assertThat(oneHello.findFirst("td").getText()).contains("name");
    }

    @Test
    public void can_update_hello() {
        insertHello("name");
        click("a.icon-edit");
        clear("#name");
        fill("#name").with("newName");
        click("#submit");
        FluentList<FluentWebElement> trInTbody = find("tbody tr");
        assertThat(trInTbody).hasSize(1);
        FluentWebElement oneHello = trInTbody.get(0);
        assertThat(oneHello.findFirst("td").getText()).contains("newName");
    }

    @Test
    public void can_delete_hello() {
        insertHello("name");
        click("a.icon-remove");
        click("#remove");
        assertThat(find("tbody tr")).isEmpty();
    }

}
