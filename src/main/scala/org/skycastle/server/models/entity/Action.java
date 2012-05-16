package org.skycastle.server.models.entity;

import java.lang.annotation.*;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented()
public @interface Action {
    String value() default "";
}
