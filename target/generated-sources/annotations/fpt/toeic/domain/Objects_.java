package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Objects.class)
public abstract class Objects_ {

	public static volatile SingularAttribute<Objects, String> path;
	public static volatile SingularAttribute<Objects, String> code;
	public static volatile SingularAttribute<Objects, String> name;
	public static volatile SingularAttribute<Objects, String> icon;
	public static volatile SetAttribute<Objects, RoleObject> dsRoleObjects;
	public static volatile SingularAttribute<Objects, String> description;
	public static volatile SingularAttribute<Objects, ZonedDateTime> updateTime;
	public static volatile SingularAttribute<Objects, Long> id;
	public static volatile SingularAttribute<Objects, Long> type;
	public static volatile SingularAttribute<Objects, Long> parentId;
	public static volatile SingularAttribute<Objects, Long> status;

	public static final String PATH = "path";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String ICON = "icon";
	public static final String DS_ROLE_OBJECTS = "dsRoleObjects";
	public static final String DESCRIPTION = "description";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String PARENT_ID = "parentId";
	public static final String STATUS = "status";

}

