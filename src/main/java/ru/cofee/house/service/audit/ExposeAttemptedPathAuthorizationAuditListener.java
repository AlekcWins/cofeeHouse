package ru.cofee.house.service.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.access.event.*;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExposeAttemptedPathAuthorizationAuditListener extends AbstractAuthorizationAuditListener {

    public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
    public static final String AUTHENTICATION_CREDENTIALS_NOT_FOUND_EVENT = "AUTHENTICATION_CREDENTIALS_NOT_FOUND_EVENT";

    public static final String AUTHORIZED_EVENT = "AUTHORIZED_EVENT";

    public static final String PUBLIC_INVOCATION_EVENT = "PUBLIC_INVOCATION_EVENT";

    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        logEvent(event);
    }

    private void logEvent(AbstractAuthorizationEvent event) {
        if (event instanceof AuthorizationFailureEvent) {
            AuthorizationFailureEvent event1 = (AuthorizationFailureEvent) event;
            Map<String, Object> data = new HashMap<>();
            if (event1.getSource() instanceof FilterInvocation){
                data.put("requestUrl", ((FilterInvocation) event1.getSource()).getRequestUrl());
                if (event1.getAuthentication().getDetails() != null) {
                    data.put("details", event1.getAuthentication().getDetails());
                }

                publish(new AuditEvent(event1.getAuthentication().getName(), AUTHORIZATION_FAILURE, data));
            }
        } else if (event instanceof AuthenticationCredentialsNotFoundEvent) {
            AuthenticationCredentialsNotFoundEvent event1 = (AuthenticationCredentialsNotFoundEvent) event;
            Map<String, Object> data = new HashMap<>();
            if (event1.getSource() instanceof FilterInvocation)
                data.put("requestUrl", ((FilterInvocation) event1.getSource()).getRequestUrl());
            publish(new AuditEvent("without user", AUTHENTICATION_CREDENTIALS_NOT_FOUND_EVENT, data));
        } else if (event instanceof AuthorizedEvent) {
            AuthorizedEvent event1 = (AuthorizedEvent) event;
            Map<String, Object> data = new HashMap<>();
            if (event1.getSource() instanceof FilterInvocation)
                data.put("requestUrl", ((FilterInvocation) event1.getSource()).getRequestUrl());

            if (event1.getAuthentication().getDetails() != null) {
                data.put("details", event1.getAuthentication().getDetails());
            }
            publish(new AuditEvent(event1.getAuthentication().getName(), AUTHORIZED_EVENT, data));

        } else if (event instanceof PublicInvocationEvent) {
            PublicInvocationEvent event1 = (PublicInvocationEvent) event;
            Map<String, Object> data = new HashMap<>();
            if (event1.getSource() instanceof FilterInvocation)
                data.put("requestUrl", ((FilterInvocation) event1.getSource()).getRequestUrl());

            publish(new AuditEvent("without user", PUBLIC_INVOCATION_EVENT, data));

        }

    }
}
