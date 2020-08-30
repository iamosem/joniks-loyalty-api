package com.joniks.lotalty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joniks.lotalty.api.entity.AuditLog;

@Repository("auditLogRepository")
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
}
