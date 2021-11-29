package fpt.toeic.service.mapper;

import fpt.toeic.domain.Category;
import fpt.toeic.domain.QuestionAnswers;
import fpt.toeic.service.dto.QuestionAnswersDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-07T21:54:41+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
@Component
public class QuestionAnswersMapperImpl implements QuestionAnswersMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<QuestionAnswers> toEntity(List<QuestionAnswersDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<QuestionAnswers> list = new ArrayList<QuestionAnswers>( dtoList.size() );
        for ( QuestionAnswersDTO questionAnswersDTO : dtoList ) {
            list.add( toEntity( questionAnswersDTO ) );
        }

        return list;
    }

    @Override
    public List<QuestionAnswersDTO> toDto(List<QuestionAnswers> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<QuestionAnswersDTO> list = new ArrayList<QuestionAnswersDTO>( entityList.size() );
        for ( QuestionAnswers questionAnswers : entityList ) {
            list.add( toDto( questionAnswers ) );
        }

        return list;
    }

    @Override
    public QuestionAnswersDTO toDto(QuestionAnswers questionAnswers) {
        if ( questionAnswers == null ) {
            return null;
        }

        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();

        questionAnswersDTO.setCategoryId( questionAnswersCategoryId( questionAnswers ) );
        questionAnswersDTO.setId( questionAnswers.getId() );
        questionAnswersDTO.setName( questionAnswers.getName() );
        questionAnswersDTO.setAnswer( questionAnswers.getAnswer() );
        questionAnswersDTO.setStatus( questionAnswers.getStatus() );
        questionAnswersDTO.setStt( questionAnswers.getStt() );
        questionAnswersDTO.setAnswerToChoose( questionAnswers.getAnswerToChoose() );
        questionAnswersDTO.setTransscript( questionAnswers.getTransscript() );

        return questionAnswersDTO;
    }

    @Override
    public QuestionAnswers toEntity(QuestionAnswersDTO questionAnswersDTO) {
        if ( questionAnswersDTO == null ) {
            return null;
        }

        QuestionAnswers questionAnswers = new QuestionAnswers();

        questionAnswers.setCategory( categoryMapper.fromId( questionAnswersDTO.getCategoryId() ) );
        questionAnswers.setId( questionAnswersDTO.getId() );
        questionAnswers.setName( questionAnswersDTO.getName() );
        questionAnswers.setAnswer( questionAnswersDTO.getAnswer() );
        questionAnswers.setStatus( questionAnswersDTO.getStatus() );
        questionAnswers.setStt( questionAnswersDTO.getStt() );
        questionAnswers.setAnswerToChoose( questionAnswersDTO.getAnswerToChoose() );
        questionAnswers.setTransscript( questionAnswersDTO.getTransscript() );

        return questionAnswers;
    }

    private Long questionAnswersCategoryId(QuestionAnswers questionAnswers) {
        if ( questionAnswers == null ) {
            return null;
        }
        Category category = questionAnswers.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
