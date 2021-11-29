package fpt.toeic.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysLogs.class)
public abstract class SysLogs_ {

	public static volatile SingularAttribute<SysLogs, String> userImpact;
	public static volatile SingularAttribute<SysLogs, String> codeAction;
	public static volatile SingularAttribute<SysLogs, String> ip;
	public static volatile SingularAttribute<SysLogs, Long> id;
	public static volatile SingularAttribute<SysLogs, ZonedDateTime> endTime;
	public static volatile SingularAttribute<SysLogs, String> nameClient;
	public static volatile SingularAttribute<SysLogs, String> content;
	public static volatile SingularAttribute<SysLogs, ZonedDateTime> impactTime;

	public static final String USER_IMPACT = "userImpact";
	public static final String CODE_ACTION = "codeAction";
	public static final String IP = "ip";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String NAME_CLIENT = "nameClient";
	public static final String CONTENT = "content";
	public static final String IMPACT_TIME = "impactTime";

}

