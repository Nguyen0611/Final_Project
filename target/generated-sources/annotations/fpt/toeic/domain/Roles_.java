package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Roles.class)
public abstract class Roles_ {

	public static volatile SingularAttribute<Roles, String> isLink;
	public static volatile SingularAttribute<Roles, String> code;
	public static volatile SingularAttribute<Roles, String> name;
	public static volatile SetAttribute<Roles, RoleObject> dsRoleObjects;
	public static volatile SingularAttribute<Roles, String> description;
	public static volatile SetAttribute<Roles, Users> dsUsers;
	public static volatile SingularAttribute<Roles, ZonedDateTime> updateTime;
	public static volatile SingularAttribute<Roles, Long> id;
	public static volatile SingularAttribute<Roles, Long> type;
	public static volatile SingularAttribute<Roles, Long> status;

	public static final String IS_LINK = "isLink";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DS_ROLE_OBJECTS = "dsRoleObjects";
	public static final String DESCRIPTION = "description";
	public static final String DS_USERS = "dsUsers";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String STATUS = "status";

}

