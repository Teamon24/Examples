package dbms.sql;

import org.hibernate.SessionFactory;
import dbms.hibernate.SessionFactoryBuilder;

public class Demo {
    SessionFactory sessionFactory = new SessionFactoryBuilder()
        .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
        .build();

}
