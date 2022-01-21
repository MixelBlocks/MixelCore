package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GiveCommand extends Command {

    private final MixelCorePlugin plugin;

    public GiveCommand(MixelCorePlugin plugin) {
        super("give");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("");
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00"));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAA"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EE0000"));

        aliases.add("i");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {

        return false;
    }

}
