package me.emmano.scopesapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Scope {

    String baseActivityName() default "BaseScopedActivity";

    Class<?>[] retrofitServices();

    Class<?> restAdapterModule() default Void.class;

    boolean butterKnife() default false;

}
