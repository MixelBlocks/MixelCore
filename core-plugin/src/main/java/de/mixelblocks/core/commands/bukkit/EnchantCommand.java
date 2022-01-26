package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantCommand extends Command {

    private final MixelCorePlugin plugin;

    public EnchantCommand(MixelCorePlugin plugin) {
        super("enchant");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAA"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName()));

        aliases.add("ench");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cYou can only run this command as a player.");
            return true;
        }

        if(args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);
        }

        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cDer command ist noch nicht implementiert.");

        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();

        switch(args.length) {
            case 1: {
                List<String> available = new ArrayList<>();
                if(sender.hasPermission("core.command.enchant.others")
                        || sender.hasPermission("core.command.enchant.*")) {
                    for(Player online : Bukkit.getOnlinePlayers()) available.add(online.getName());
                } else if(sender instanceof Player) {
                    Player player = (Player) sender;
                    completions.add(player.getName());
                }
                StringUtil.copyPartialMatches(args[0], available, completions);
                break;
            }

            case 2: {
                List<String> available = new ArrayList<>();
                for(Enchantment enchantment : Enchantment.values()) {
                    if(sender.hasPermission("core.command.enchant.all")
                            || sender.hasPermission("core.command.enchant.*")) {
                        available.add(enchantment.getKey().namespace() + ":" + enchantment.getKey().value());
                        continue;
                    }
                    if(sender instanceof Player) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target == null) {
                            completions.add("Spieler ungültig!");
                            break;
                        }
                        ItemStack item = target.getInventory().getItemInMainHand();
                        if(item == null || item.getType() == Material.AIR || !item.getType().isItem()) {
                            completions.add("Item ungültig!");
                            break;
                        }
                    }
                }
                StringUtil.copyPartialMatches(args[1], available, completions);
                break;
            }

            case 3: {
                List<String> available = new ArrayList<>();
                Enchantment choosenEnchantment = Enchantment.getByKey(NamespacedKey.fromString(args[1]));
                if(choosenEnchantment == null) {
                    available.add("Enchantment ungültig!");
                    break;
                }
                int maxLevel = sender.hasPermission("core.command.enchant.max")
                        || sender.hasPermission("core.command.enchant.*")
                        ? 32767 : choosenEnchantment.getMaxLevel();
                for(int i = 1; i < maxLevel + 1; i++) available.add(Integer.toString(i));
                break;
            }

            default:break;
        }

        Collections.sort(completions);
        return completions;
    }
}
