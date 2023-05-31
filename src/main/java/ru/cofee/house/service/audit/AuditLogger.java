package ru.cofee.house.service.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.cofee.house.model.AuditItem;
import ru.cofee.house.repository.AuditLogRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuditLogger {
    private final AuditLogRepository repository;

    public AuditLogger(AuditLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();

        String principal = auditEvent.getPrincipal();
        Instant timestamp = auditEvent.getTimestamp();
        String auditEventType = auditEvent.getType();

        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        String remoteAddress = details.getRemoteAddress();
        String sessionId = details.getSessionId();
        Object requestUrl = auditEvent.getData().get("requestUrl");

        createDataAuditItem(principal, timestamp, auditEventType, remoteAddress, sessionId, requestUrl);
    }
    @EventListener
    public void onFailure(LogoutSuccessEvent event) {
        String principal = event.getAuthentication().getName();
        Instant timestamp = Instant.ofEpochMilli(event.getTimestamp());
        String auditEventType = "AUTHENTICATION_LOGOUT_SUCCESS_EVENT";

        WebAuthenticationDetails details = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        String remoteAddress = details.getRemoteAddress();
        String sessionId = details.getSessionId();
        Object requestUrl = "";

        createDataAuditItem(principal, timestamp, auditEventType, remoteAddress, sessionId, requestUrl);
    }

    private void createDataAuditItem(String principal, Instant timestamp, String auditEventType, String remoteAddress, String sessionId, Object requestUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("requestUrl", requestUrl);
        Map<String, Object> detailsData = new HashMap<>();
        detailsData.put("sessionId", sessionId);
        detailsData.put("remoteAddress", remoteAddress);
        data.put("details", detailsData);

        repository.save(AuditItem.builder()
                .timestamp(timestamp)
                .type(auditEventType)
                .principal(principal)
                .data(data)
                .build());

        log.info(String.format("Principal %s %s", principal, auditEventType));
        log.info(String.format("Remote IP address: %s Session Id: %s", remoteAddress, sessionId));
        if (requestUrl != null)
            log.info(String.format("Request Url: %s  %s", requestUrl, ""));
    }


}