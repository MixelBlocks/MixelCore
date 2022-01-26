package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand extends Command {

    private final MixelCorePlugin plugin;

    public GiveCommand(MixelCorePlugin plugin) {
        super("give");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAAEnchante ein Item bis zu level 32767"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName()));

        aliases.add("i");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtil.replySenderComponent(sender,
                    MixelCorePlugin.prefix + "&cYou can only run this command as a player."
            );
            return true;
        }

        if(!sender.hasPermission("*"))
            ChatUtil.replySenderComponent(sender,
                    MixelCorePlugin.prefix + "&cDer command ist deaktiviert."
            );
        else {

            // TODO:

            ChatUtil.replySenderComponent(sender,
                    MixelCorePlugin.prefix + "&cDer command ist noch nicht implementiert."
            );
        }

        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();

        switch(args.length) {

            case 1: {
                if(sender.hasPermission("*")) {
                    List<String> available = new ArrayList<>();
                    for(Material material : Material.values()) available.add(material.getKey().namespace() + ":" + material.getKey().value());
                    StringUtil.copyPartialMatches(args[0], available, completions);
                }
                break;
            }

            case 2: {
                if(sender.hasPermission("*")) {
                    List<String> available = new ArrayList<>();
                    Material chosenMaterial = Material.getMaterial(NamespacedKey.fromString(args[0]).getKey().toUpperCase());
                    ItemStack item = new ItemStack(chosenMaterial);
                    if(item != null) {
                        available.add("1");
                        available.add(Integer.toString(item.getMaxStackSize()));
                    } else available.add("Item ungültig!");
                    StringUtil.copyPartialMatches(args[0], available, completions);
                }
                break;
            }

            default: break;
        }

        Collections.sort(completions);
        return completions;
    }
}
