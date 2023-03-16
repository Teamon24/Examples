package org.home.components.scopes.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopesController {

    final Messanger requestScopedMessanger;
    final Messanger sessionScopedMessanger;
    final Messanger applicationScopedBean;

    public ScopesController(
        @Qualifier("requestScopedMessanger")     Messanger requestScopedMessanger,
        @Qualifier("sessionScopedMessanger")     Messanger sessionScopedMessanger,
        @Qualifier("applicationScopedMessanger") Messanger applicationScopedBean)
    {
        this.requestScopedMessanger = requestScopedMessanger;
        this.sessionScopedMessanger = sessionScopedMessanger;
        this.applicationScopedBean  = applicationScopedBean;
    }

    @GetMapping("/scopes/request")
    public String getRequestScopeMessage() {
        return requestScopedMessanger.getMessage();
    }

    @GetMapping("/scopes/session")
    public String getSessionScopeMessage() {
        return sessionScopedMessanger.getMessage();
    }

    @GetMapping("/scopes/application")
    public String getApplicationScopeMessage() {
        return applicationScopedBean.getMessage();
    }
}