package fpt.toeic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QuestionAnswers.class)
public abstract class QuestionAnswers_ {

	public static volatile SingularAttribute<QuestionAnswers, Long> stt;
	public static volatile SingularAttribute<QuestionAnswers, String> answer;
	public static volatile SingularAttribute<QuestionAnswers, String> transscript;
	public static volatile SingularAttribute<QuestionAnswers, String> name;
	public static volatile SingularAttribute<QuestionAnswers, Long> id;
	public static volatile SingularAttribute<QuestionAnswers, Category> category;
	public static volatile SingularAttribute<QuestionAnswers, String> answerToChoose;
	public static volatile SingularAttribute<QuestionAnswers, Long> status;

	public static final String STT = "stt";
	public static final String ANSWER = "answer";
	public static final String TRANSSCRIPT = "transscript";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String ANSWER_TO_CHOOSE = "answerToChoose";
	public static final String STATUS = "status";

}

