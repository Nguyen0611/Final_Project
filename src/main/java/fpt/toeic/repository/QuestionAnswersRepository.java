package fpt.toeic.repository;

import com.google.common.base.Optional;
import fpt.toeic.domain.Category;
import fpt.toeic.domain.QuestionAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the QuestionAnswers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, Long>, JpaSpecificationExecutor<QuestionAnswers> {

    @Modifying
    @Query(value = "delete from question_answers where question_answers.category_id = :id", nativeQuery = true)
    void deleteByCategory(@Param("id") Long id);

    List<QuestionAnswers> findAllByCategory(Category category);

    @Query(value = "SELECT  qa.id, qa.answer,GROUP_CONCAT(answer_to_choose SEPARATOR '#--#') as answer_to_choose, qa.name, qa.status, qa.transscript, qa.category_id, qa.stt FROM toeic_web.question_answers qa\n" +
        "where category_id in (select distinct id from toeic_web.category where topic_id = :category_id) and status = 1\n" +
        "group by category_id, stt\n" +
        "ORDER BY RAND()\n" +
        "LIMIT 5", nativeQuery = true)
    List<QuestionAnswers> findAllByCategoryClient(@Param("category_id") Long category_id);

    List<QuestionAnswers> findByStatusAndCategoryOrderByAnswerToChoose(Long status,Category category);

}
