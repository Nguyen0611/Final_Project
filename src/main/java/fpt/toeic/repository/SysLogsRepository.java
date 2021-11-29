package fpt.toeic.repository;

import fpt.toeic.domain.SysLogs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysLogsRepository extends JpaRepository<SysLogs, Long>, JpaSpecificationExecutor<SysLogs> {
}
