package com.firework.client.Features.CommandsSystem;

import com.firework.client.Features.CommandsSystem.CommandManager;
import com.firework.client.Features.CommandsSystem.CommandManifest;

public class Command {
    String label;
    String[] aliases;
    public static String prefix = CommandManager.prefix;

    public Command() {
        if (this.getClass().isAnnotationPresent(CommandManifest.class)) {
            CommandManifest manifest = this.getClass().getAnnotation(CommandManifest.class);
            this.label = manifest.label();
            this.aliases = manifest.aliases();
        }
    }

    public void execute(String[] args) {
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getAliases() {
        return this.aliases;
    }
}
