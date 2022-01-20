package dbms.hibernate.entity.mapping;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(
        generator = "prod-generator")
    @GenericGenerator(
        name = "prod-generator",
        parameters = @Parameter(name = "prefix", value = "prod"),
        strategy = "dbms.hibernate.entity.mapping.generator.ProductIdGenerator")
    private String id;
}