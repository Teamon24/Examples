package org.home.configs.common;

import org.home.components.scopes.common.SomeRepositoryImpl;
import org.home.components.scopes.common.SomeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Классы с аннотацией @Configuration указывают на то, что они являются источниками определения бинов, public-методов с аннотацией @Bean.
 *
 */
@Configuration
@ComponentScan("org.home.components.scopes.common")
@Import(AnotherConfiguration.class)
public class CommonConfiguration {

    @Bean(name = "manuallyCreatedSomeRepository")
    public SomeRepositoryImpl getManuallyCreatedCourseRepository() {
        return new SomeRepositoryImpl();
    }

    @Bean(name = "manuallyCreatedSomeService")
    public SomeServiceImpl getManuallyCreatedCourseService() {
        return new SomeServiceImpl(getManuallyCreatedCourseRepository());
    }
}