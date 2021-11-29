package fpt.toeic.repository;

import fpt.toeic.domain.Category;

import fpt.toeic.domain.Topic;
import fpt.toeic.service.dto.CategoryDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query(value = "SELECT \n" +
        "    c.id,\n" +
        "    c.category_name as categoryName,\n" +
        "    c.status,\n" +
        "    to1.id AS topicId,\n" +
        "    to1.name1 AS name1,\n" +
        "    to1.id_type,\n" +
        "    to1.name2 AS name2,\n" +
        "    to1.id_part_topic,\n" +
        "    to1.name,\n" +
        "    c.creation_time AS stCreationTime,\n" +
        "    c.update_time AS stUpdateTime\n" +
        "FROM\n" +
        "    toeic_web.category AS c\n" +
        "        INNER JOIN\n" +
        "    ( SELECT t.id, t.code,t.name as name1, t.id_type, o.name as name2, t.id_part_topic, o1.name, t.creation_time as stCreationTime, t.update_time as stUpdateTime FROM toeic_web.topic t\n" +
        "        inner join objects o on t.id_type = o.id\n" +
        "        inner join objects o1 on t.id_part_topic = o1.id where 1 =1 and (:name is null or t.name like %:name% ESCAPE '&' ) \n" +
        "        and (:id_type is null or t.id_type = :id_type ) and (:id_part_topic is null or t.id_part_topic = :id_part_topic) ) AS to1 ON to1.id = c.topic_id   LIMIT :page,:page_size \n" +
        "\n", nativeQuery = true)
    List<Object[]> searchTopic(@Param("name") String name, @Param("id_type") Long id_type, @Param("id_part_topic") Long id_part_topic, @Param("page") int page, @Param("page_size") int page_size);

    @Query(value = "select count(ca.id) from toeic_web.category as ca inner join (    SELECT t.id FROM toeic_web.topic t\n" +
        "        inner join objects o on t.id_type = o.id\n" +
        "        inner join objects o1 on t.id_part_topic = o1.id where 1 =1 and (:name is null or t.name  like %:name% ESCAPE '&' ) \n" +
        "        and (:id_type is null or t.id_type = :id_type ) and (:id_part_topic is null or t.id_part_topic = :id_part_topic) ) as to1 on to1.id = ca.topic_id ", nativeQuery = true)
    Long countAllByNameAndIdTypeAndIdPartTopic(@Param("name") String name, @Param("id_type") Long id_type, @Param("id_part_topic") Long id_part_topic);

    Category findByCodeAndTopic (String code, Topic topic);

    Category findCategoryById (Long id);

    @Query(value = "SELECT * from toeic_web.category c WHERE c.topic_id = :id", nativeQuery = true)
    List<Category> findCategoryByTopicId(@Param("id") Long id);
}
