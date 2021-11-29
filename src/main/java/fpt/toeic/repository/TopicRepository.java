package fpt.toeic.repository;

import fpt.toeic.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Topic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    @Query(value = " SELECT t.id, t.code,t.name as name1, t.id_type, o.name as name2, t.id_part_topic, o1.name, t.creation_time as stCreationTime, t.update_time as stUpdateTime FROM toeic_web.topic t\n" +
        "inner join objects o on t.id_type = o.id\n" +
        "inner join objects o1 on t.id_part_topic = o1.id where 1 =1 and (:name is null or t.name like %:name% ESCAPE '&' ) " +
        "and (:id_type is null or t.id_type = :id_type ) and (:id_part_topic is null or t.id_part_topic = :id_part_topic)  LIMIT :page,:page_size", nativeQuery = true)
    List<Object[]> searchTopic(@Param("name") String name, @Param("id_type") Long id_type, @Param("id_part_topic") Long id_part_topic, @Param("page") int page, @Param("page_size") int page_size);

    @Query(value = " SELECT count(*) FROM toeic_web.topic t\n" +
        "inner join objects o on t.id_type = o.id\n" +
        "inner join objects o1 on t.id_part_topic = o1.id where 1 =1 and (:name is null or t.name  like %:name% ESCAPE '&' ) " +
        "and (:id_type is null or t.id_type = :id_type ) and (:id_part_topic is null or t.id_part_topic = :id_part_topic) ", nativeQuery = true)
    Long countAllByNameAndIdTypeAndIdPartTopic(@Param("name") String name, @Param("id_type") Long id_type, @Param("id_part_topic") Long id_part_topic);

    List<Topic> findAllByIdTypeAndIdPartTopic (Long idType, Long IdPartTopic);

    @Query(value = " SELECT t.id, t.code,t.name as name1, t.id_type, o.name as name2, t.id_part_topic, o1.name, t.creation_time as stCreationTime, t.update_time as stUpdateTime FROM toeic_web.topic t\n" +
        "inner join objects o on t.id_type = o.id\n" +
        "inner join objects o1 on t.id_part_topic = o1.id where 1=1 and (:id_part_topic is null or t.id_part_topic = :id_part_topic) ", nativeQuery = true)
    List<Topic> getTopicByIdPart(@Param("id_part_topic") Long id_part_topic);
}
