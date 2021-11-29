package fpt.toeic.repository;

import fpt.toeic.domain.RoleObject;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RoleObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleObjectRepository extends JpaRepository<RoleObject, Long>, JpaSpecificationExecutor<RoleObject> {
}
