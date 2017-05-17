package org.neogroup.sparks.console.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to assign commands to a processor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessCommands {

    /**
     * Command names associated to the processor
     * @return array of strings
     */
    public String[] value() default {};
}
