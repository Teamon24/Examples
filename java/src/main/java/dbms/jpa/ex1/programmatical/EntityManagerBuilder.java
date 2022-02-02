package dbms.jpa.ex1.programmatical;

import dbms.jpa.ex1.UserEntity;
import dbms.jpa.ex1.programmatical.driver.DriverType;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManagerBuilder {

    private DriverType type;
    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;
    private String schema;
    private List<String> managedClassesNames;

    public EntityManagerBuilder type(DriverType type) {
        this.type = type;
        return this;
    }

    public EntityManagerBuilder host(String host) {
        this.host = host;
        return this;
    }

    public EntityManagerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public EntityManagerBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public EntityManagerBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public EntityManagerBuilder password(String password) {
        this.password = password;
        return this;
    }

    public EntityManagerBuilder schema(String schema) {
        this.schema = schema;
        return this;
    }

    public EntityManagerBuilder managedClassesNames(String ... classes) {
        this.managedClassesNames = Arrays.asList(classes);
        return this;
    }

    public EntityManager build() {


        return EntityManagers.createEntityManager(
            this.type == null ? DriverType.POSTGRES : this.type,
            this.host == null ? "localhost" : this.host,
            this.port == 0 ? 8080 : this.port,
            this.databaseName,
            this.userName,
            this.password == null ? "" : this.password,
            this.schema == null ? "" : this.schema,
            this.managedClassesNames
        );
    }
}
