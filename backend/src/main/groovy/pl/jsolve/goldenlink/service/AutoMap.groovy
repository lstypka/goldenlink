package pl.jsolve.goldenlink.service
import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.METHOD
import static java.lang.annotation.RetentionPolicy.RUNTIME

@Retention(RUNTIME)
@Target(METHOD)
@interface AutoMap {
    Class<?> argument() default Object
    Class<?> result()
}