#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

#if( ${withHibernate} == true)
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
#end
public class Hello {

#if( ${withHibernate} == true)
    @Id
    @GeneratedValue
#end
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

#if( ${withHibernate} == true)
    public static SimpleEntityManager<Hello, Long> simpleEntityManager = new SimpleEntityManager<>(Hello.class);
#end
}