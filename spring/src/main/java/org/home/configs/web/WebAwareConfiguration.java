package org.home.configs.web;

import org.home.components.scopes.web.Messanger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ComponentScan(basePackages = "org.home.components.scopes.web")
public class WebAwareConfiguration {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Messanger requestScopedMessanger() {
        return messanger("SCOPE_REQUEST");
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Messanger sessionScopedMessanger() {
        return messanger("SCOPE_SESSION");
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Messanger applicationScopedMessanger() {
        return messanger("SCOPE_APPLICATION");
    }

    @Bean
    @Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Messanger websocketScopedMessanger() {
        return messanger("SCOPE_WEBSOCKET");
    }

    private Messanger messanger(String scope) {
        Messanger messanger = new Messanger();
        messanger.setMessage(String.format("%s: " + scope, messanger));
        return messanger;
    }
}
