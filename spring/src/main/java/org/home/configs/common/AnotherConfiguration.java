package org.home.configs.common;

import org.home.components.scopes.common.CommandManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class AnotherConfiguration {

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public CommandManager prototypeCommandManager() {
        return new CommandManager() {};
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public CommandManager singletonCommandManager() {
        return new CommandManager() {};
    }
}
