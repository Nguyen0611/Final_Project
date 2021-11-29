package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RoleObject.class)
public abstract class RoleObject_ {

	public static volatile SingularAttribute<RoleObject, Roles> roles;
	public static volatile SingularAttribute<RoleObject, Objects> objects;
	public static volatile SingularAttribute<RoleObject, ZonedDateTime> updateTime;
	public static volatile SingularAttribute<RoleObject, Long> id;

	public static final String ROLES = "roles";
	public static final String OBJECTS = "objects";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";

}

