package com.firework.client.Features.Modules;

import com.firework.client.Features.Modules.Module;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface ModuleArgs {
    public String name();

    public Module.Category category();

    public String subCategory() default "null";
}
