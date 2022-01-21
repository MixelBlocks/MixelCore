package de.mixelblocks.core.commands;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CoreReloadCommand extends Command {

    private final MixelCorePlugin plugin;

    public CoreReloadCommand(MixelCorePlugin plugin) {
        super("corereload");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAA"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName()));

        aliases.add("crl");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {

        return false;
    }

}
