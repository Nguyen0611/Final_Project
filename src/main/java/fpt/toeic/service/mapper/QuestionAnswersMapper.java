package fpt.toeic.service.mapper;


import fpt.toeic.domain.*;
import fpt.toeic.service.dto.QuestionAnswersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionAnswers} and its DTO {@link QuestionAnswersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface QuestionAnswersMapper extends EntityMapper<QuestionAnswersDTO, QuestionAnswers> {

    @Mapping(source = "category.id", target = "categoryId")
    QuestionAnswersDTO toDto(QuestionAnswers questionAnswers);

    @Mapping(source = "categoryId", target = "category")
    QuestionAnswers toEntity(QuestionAnswersDTO questionAnswersDTO);

    default QuestionAnswers fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setId(id);
        return questionAnswers;
    }
}
