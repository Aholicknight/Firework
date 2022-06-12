package com.firework.client.Features.CommandsSystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface CommandManifest {
    public String label();

    public String usage() default "";

    public String description() default "";

    public String[] aliases() default {};
}
