create table audit_logs
(
    id        SERIAL PRIMARY KEY NOT NULL,
    timestamp TIMESTAMP          NOT NULL,
    type      VARCHAR(1024)      NOT NULL,
    principal VARCHAR(1024),
    data      jsonb
);
