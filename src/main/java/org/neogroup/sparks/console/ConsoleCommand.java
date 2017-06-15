package org.neogroup.sparks.console;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to assign commands to a method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConsoleCommand {

    /**
     * Command names associated to the processor
     * @return array of strings
     */
    public String[] value();
}
