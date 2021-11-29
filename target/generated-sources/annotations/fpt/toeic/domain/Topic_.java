package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Topic.class)
public abstract class Topic_ {

	public static volatile SetAttribute<Topic, Category> dsCategories;
	public static volatile SingularAttribute<Topic, String> code;
	public static volatile SingularAttribute<Topic, Long> idType;
	public static volatile SingularAttribute<Topic, ZonedDateTime> creationTime;
	public static volatile SingularAttribute<Topic, String> name;
	public static volatile SingularAttribute<Topic, ZonedDateTime> updateTime;
	public static volatile SingularAttribute<Topic, Long> id;
	public static volatile SingularAttribute<Topic, Long> idPartTopic;

	public static final String DS_CATEGORIES = "dsCategories";
	public static final String CODE = "code";
	public static final String ID_TYPE = "idType";
	public static final String CREATION_TIME = "creationTime";
	public static final String NAME = "name";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";
	public static final String ID_PART_TOPIC = "idPartTopic";

}

