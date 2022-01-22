package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StopCommand extends Command {

    private final MixelCorePlugin plugin;

    public StopCommand(MixelCorePlugin plugin) {
        super("stop");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAADieser Command ist deaktiviert."));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName()));

        aliases.add("shutdown");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(sender.hasPermission(this.getPermission() + ".force")) {

            if(args[0] != null && args[0].toLowerCase() == "--force") {
                Bukkit.getServer().shutdown();
                return true;
            }

        }
        sender.sendMessage(
                ChatUtil.colorizeHexAndCode(
                        MixelCorePlugin.prefix + "&#FF0000Dieser Command ist aus Sicherheitsgründen deaktiviert.")
        );
        return false;
    }

}
