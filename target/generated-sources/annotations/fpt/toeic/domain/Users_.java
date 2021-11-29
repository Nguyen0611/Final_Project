package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> creator;
	public static volatile SingularAttribute<Users, String> address;
	public static volatile SingularAttribute<Users, String> mail;
	public static volatile SingularAttribute<Users, ZonedDateTime> resetDate;
	public static volatile SingularAttribute<Users, ZonedDateTime> creationTime;
	public static volatile SingularAttribute<Users, String> pass;
	public static volatile SingularAttribute<Users, Roles> roles;
	public static volatile SingularAttribute<Users, String> fullName;
	public static volatile SingularAttribute<Users, ZonedDateTime> dateOfBirth;
	public static volatile SingularAttribute<Users, String> resetKey;
	public static volatile SingularAttribute<Users, String> phone;
	public static volatile SingularAttribute<Users, String> name;
	public static volatile SingularAttribute<Users, String> pathUrl;
	public static volatile SingularAttribute<Users, Long> id;
	public static volatile SingularAttribute<Users, Long> status;

	public static final String CREATOR = "creator";
	public static final String ADDRESS = "address";
	public static final String MAIL = "mail";
	public static final String RESET_DATE = "resetDate";
	public static final String CREATION_TIME = "creationTime";
	public static final String PASS = "pass";
	public static final String ROLES = "roles";
	public static final String FULL_NAME = "fullName";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String RESET_KEY = "resetKey";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String PATH_URL = "pathUrl";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

