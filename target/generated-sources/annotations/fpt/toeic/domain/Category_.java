package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile SingularAttribute<Category, String> code;
	public static volatile SingularAttribute<Category, ZonedDateTime> creationTime;
	public static volatile SetAttribute<Category, FileUpload> dsFileUploads;
	public static volatile SingularAttribute<Category, Topic> topic;
	public static volatile SingularAttribute<Category, ZonedDateTime> updateTime;
	public static volatile SingularAttribute<Category, Long> id;
	public static volatile SingularAttribute<Category, String> categoryName;
	public static volatile SetAttribute<Category, QuestionAnswers> dsQuestionAnswers;
	public static volatile SingularAttribute<Category, Long> status;

	public static final String CODE = "code";
	public static final String CREATION_TIME = "creationTime";
	public static final String DS_FILE_UPLOADS = "dsFileUploads";
	public static final String TOPIC = "topic";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String DS_QUESTION_ANSWERS = "dsQuestionAnswers";
	public static final String STATUS = "status";

}

