#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import fr.ybonnel.simpleweb4j.handlers.resource.RestResource;

import java.util.Collection;
#if( ${withHibernate} != true)
import java.util.HashMap;
import java.util.Map;
#end

public class HelloResource extends RestResource<Hello> {

#if( ${withHibernate} != true)
    protected static Map<Long, Hello> entities = new HashMap<>();
#end

    public HelloResource(String route) {
        super(route, Hello.class);
    }

    @Override
    public Hello getById(String id) {
#if( ${withHibernate} == true)
        return Hello.simpleEntityManager.getById(Long.parseLong(id));
#else
        return entities.get(Long.parseLong(id));
#end
    }

    @Override
    public Collection<Hello> getAll() {
#if( ${withHibernate} == true)
        return Hello.simpleEntityManager.getAll();
#else
        return entities.values();
#end
    }

    @Override
    public void update(String id, Hello resource) {
        resource.setId(Long.parseLong(id));
#if( ${withHibernate} == true)
        Hello.simpleEntityManager.update(resource);
#else
        entities.put(Long.parseLong(id), resource);
#end
    }

    @Override
    public void create(Hello resource) {
#if( ${withHibernate} == true)
        Hello.simpleEntityManager.save(resource);
#else
        long maxId = 0;
        for (Hello hello : getAll()) {
            if (hello.getId() > maxId) {
                maxId = hello.getId();
            }
        }
        maxId++;
        resource.setId(maxId);
        entities.put(maxId, resource);
#end
    }

    @Override
    public void delete(String id) {
#if( ${withHibernate} == true)
        Hello.simpleEntityManager.delete(Long.parseLong(id));
#else
        entities.remove(Long.parseLong(id));
#end
    }

}