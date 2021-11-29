package fpt.toeic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FileUpload.class)
public abstract class FileUpload_ {

	public static volatile SingularAttribute<FileUpload, String> path;
	public static volatile SingularAttribute<FileUpload, Long> id;
	public static volatile SingularAttribute<FileUpload, Category> category;
	public static volatile SingularAttribute<FileUpload, String> typeFile;

	public static final String PATH = "path";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String TYPE_FILE = "typeFile";

}

