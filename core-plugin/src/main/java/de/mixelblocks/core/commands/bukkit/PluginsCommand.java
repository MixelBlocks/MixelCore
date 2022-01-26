package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PluginsCommand extends Command {

    private final MixelCorePlugin plugin;

    public PluginsCommand(MixelCorePlugin plugin) {
        super("plugins");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAA"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName()));

        aliases.add("pl");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cDer command ist deaktiviert.");
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();

        switch(args.length) {
            default: break;
        }

        Collections.sort(completions);
        return completions;
    }

}
