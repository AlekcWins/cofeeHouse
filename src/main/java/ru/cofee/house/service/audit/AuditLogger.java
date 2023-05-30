package ru.cofee.house.service.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditLogger {

    @EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {

        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();


        String principal = auditEvent.getPrincipal();
        String auditEventType = auditEvent.getType();


        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        String remoteAddress = details.getRemoteAddress();
        String sessionId = details.getSessionId();
        Object requestUrl = auditEvent.getData().get("requestUrl");

        log.info(String.format("Principal %s %s", principal, auditEventType));
        log.info(String.format("Remote IP address: %s Session Id: %s", remoteAddress, sessionId));
        if (requestUrl != null)
            log.info(String.format("Request Url: %s  %s", requestUrl, ""));
    }

}