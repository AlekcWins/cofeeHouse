package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.AuditItem;

public interface AuditLogRepository extends JpaRepository<AuditItem, Long> {
}