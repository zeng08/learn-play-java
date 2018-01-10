package annotations;

import actions.VerboseAnnotationAction;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import play.mvc.With;

@With(VerboseAnnotationAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerboseAnnotation {
  boolean value() default true;
}
