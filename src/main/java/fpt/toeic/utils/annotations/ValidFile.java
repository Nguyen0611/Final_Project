package fpt.toeic.utils.annotations;

import fpt.toeic.utils.FileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {

    String message() default "Invalid file format";

    String[] filesAllow() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

