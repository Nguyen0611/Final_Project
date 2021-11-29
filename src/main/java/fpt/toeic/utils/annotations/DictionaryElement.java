package fpt.toeic.utils.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DictionaryElement {

    String param() default "";

    String tableMap() default "";

}
