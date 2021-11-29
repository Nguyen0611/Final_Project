package fpt.toeic.repository;

import fpt.toeic.domain.Objects;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Objects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjectsRepository extends JpaRepository<Objects, Long>, JpaSpecificationExecutor<Objects> {
    @Query(value = "SELECT DISTINCT \n" +
        "            t1.id id, \n" +
        "            t1.code code, \n" +
        "            t1.name name, \n" +
        "            t1.status AS status, \n" +
        "        t1.type, \n" +
        "            t1.icon, \n" +
        "            t1.path, \n" +
        "            t1.parent_id AS parentId \n" +
        "        FROM \n" +
        "            (SELECT DISTINCT\n" +
        "    sm.id id,\n" +
        "    sm.code code,\n" +
        "    sm.name name,\n" +
        "    sm.status,\n" +
        "    sm.type,\n" +
        "    sm.icon,\n" +
        "    sm.path,\n" +
        "    sm.parent_id\n" +
        "FROM\n" +
        "    objects AS sm\n" +
        "        INNER JOIN\n" +
        "    role_object AS srm ON sm.id = srm.objects_id\n" +
        "        INNER JOIN\n" +
        "    (SELECT \n" +
        "        roles_id\n" +
        "    FROM\n" +
        "        users\n" +
        "    WHERE\n" +
        "        id = :idUser) role ON role.roles_id = srm.roles_id\n" +
        "WHERE\n" +
        "    sm.status = 1\n" +
        "        AND (sm.parent_id IN (SELECT \n" +
        "            id\n" +
        "        FROM\n" +
        "            objects\n" +
        "        WHERE\n" +
        "            status = 1)\n" +
        "        OR sm.parent_id = 0)\n" +
        "                    \n" +
        "                    \n" +
        "                    \n" +
        "                    \n" +
        "                    UNION SELECT \n" +
        "    sm.id id,\n" +
        "    sm.code code,\n" +
        "    sm.name name,\n" +
        "    sm.status,\n" +
        "    sm.type,\n" +
        "    sm.icon,\n" +
        "    sm.path,\n" +
        "    sm.parent_id\n" +
        "FROM\n" +
        "    objects AS sm\n" +
        "        LEFT JOIN\n" +
        "    role_object AS srm ON sm.id = srm.objects_id\n" +
        "WHERE\n" +
        "    sm.id IN (SELECT \n" +
        "            parent_id\n" +
        "        FROM\n" +
        "            (SELECT \n" +
        "                id,\n" +
        "                    parent_id,\n" +
        "                    CASE\n" +
        "                        WHEN\n" +
        "                            id IN (SELECT \n" +
        "                                    sm.id\n" +
        "                                FROM\n" +
        "                                    objects AS sm\n" +
        "                                INNER JOIN role_object AS srm ON sm.id = srm.objects_id\n" +
        "                                INNER JOIN (SELECT \n" +
        "                                    roles_id\n" +
        "                                FROM\n" +
        "                                    users\n" +
        "                                WHERE\n" +
        "                                    id = :idUser) role ON role.roles_id = srm.roles_id)\n" +
        "                        THEN\n" +
        "                            @idlist::=CONCAT(IFNULL(@idlist, ''), ',', parent_id)\n" +
        "                        WHEN FIND_IN_SET(id, @idlist) THEN @idlist::=CONCAT(@idlist, ',', parent_id)\n" +
        "                    END AS checkId\n" +
        "            FROM\n" +
        "                objects\n" +
        "            ORDER BY id DESC) T\n" +
        "        WHERE\n" +
        "            checkId IS NOT NULL)) AS t1 \n" +
        "        order by t1.id asc", nativeQuery = true)
    List<Object[]> getRoleAction(@Param("idUser") Long idUser);


    @Query(value = "SELECT DISTINCT \n" +
        "                    a1.id, a1.parent_id parenId, a1.name, a1.code, a1.checked \n" +
        "                FROM \n" +
        "                    (SELECT DISTINCT \n" +
        "                        objects.id, \n" +
        "                            objects.parent_id , \n" +
        "                            objects.name, \n" +
        "                            objects.id code, \n" +
        "                            CASE \n" +
        "                                WHEN role_object.objects_id IS NOT NULL THEN 1  \n" +
        "                                ELSE 0 \n" +
        "                            END checked \n" +
        "                    FROM \n" +
        "                        objects \n" +
        "                    LEFT JOIN object_action AS oa ON objects.id = oa.actions_id \n" +
        "                    LEFT JOIN actions ON actions.id = oa.actions_id \n" +
        "                    LEFT JOIN role_object ON objects.id = role_object.objects_id and role_object.roles_id = :id where objects.type = :type \n" +
        "                 UNION SELECT DISTINCT \n" +
        "                        NULL, \n" +
        "                            oa.objects_id, \n" +
        "                            actions.name, \n" +
        "                            CASE \n" +
        "                                WHEN oa.actions_id IS NOT NULL THEN CONCAT(  oa.objects_id, '/', oa.actions_id ) \n" +
        "                                ELSE actions.id \n" +
        "                            END code, \n" +
        "                            CASE \n" +
        "                                WHEN \n" +
        "                                    role_object.objects_id IS NOT NULL \n" +
        "                                        AND role_object.actions_id IS NOT NULL  \n" +
        "                                THEN \n" +
        "                                    1 \n" +
        "                                ELSE 0 \n" +
        "                            END checked \n" +
        "                    FROM \n" +
        "                        object_action AS oa \n" +
        "                    INNER JOIN actions ON actions.id = oa.actions_id \n" +
        "                    LEFT JOIN role_object ON (actions.id = role_object.actions_id  \n" +
        "                    AND role_object.objects_id = oa.objects_id and role_object.roles_id = :id) \n" +
        "                 ) AS a1", nativeQuery = true)
    List<Object[]> getAllObjRoleAction(@Param("id") Long id,@Param("type") Long type);

    Objects findByCode(String code);

    List<Objects> findAllByParentIdAndType (Long parant_id, Long type);
}
