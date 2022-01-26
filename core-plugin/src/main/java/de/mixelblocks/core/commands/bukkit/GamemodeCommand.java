package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand extends Command {

    private final MixelCorePlugin plugin;

    public GamemodeCommand(MixelCorePlugin plugin) {
        super("gamemode");

        this.plugin = plugin;
        ArrayList<String> aliases = new ArrayList<>();

        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setPermissionMessage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#00EE00Dir fehlt die Berechtigung: &6" + this.getPermission()));

        this.setDescription(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&#EECCAA"));
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName() + " [game mode] [<SpielerName>]"));

        aliases.add("gm");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cYou can only run this command as a player.");
            return true;
        }

        Player player = (Player) sender;

        GameMode targetMode;

        Player target = player;

        if(args.length == 0) {

            TextComponent gm0 = new TextComponent(ChatUtil.colorizeHexAndCode("&7-&r &6› &6SURVIVAL"));
            TextComponent gm1 = new TextComponent(ChatUtil.colorizeHexAndCode("&7-&r &6› &6CREATIVE"));
            TextComponent gm2 = new TextComponent(ChatUtil.colorizeHexAndCode("&7-&r &6› &6ADVENTURE"));
            TextComponent gm3 = new TextComponent(ChatUtil.colorizeHexAndCode("&7-&r &6› &6SPECTATOR"));

            gm0.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 0"));
            gm1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 1"));
            gm2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 2"));
            gm3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 3"));

            gm0.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6SURVIVAL")).create()));
            gm1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6CREATIVE")).create()));
            gm2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6ADVENTURE")).create()));
            gm3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6SPECTATOR")).create()));

            sender.sendMessage(ChatUtil.colorizeHexAndCode("&8========== " + MixelCorePlugin.PREFIX + " &8=========="));
            boolean hasPerm = false;
            if(!sender.hasPermission(this.getPermission() + ".survival")
                    && !sender.hasPermission(this.getPermission() + ".*")) {
                sender.sendMessage(gm0);
                hasPerm = true;
            }
            if(!sender.hasPermission(this.getPermission() + ".creative")
                    && !sender.hasPermission(this.getPermission() + ".*")) {
                sender.sendMessage(gm1);
                hasPerm = true;
            }
            if(!sender.hasPermission(this.getPermission() + ".adventure")
                    && !sender.hasPermission(this.getPermission() + ".*")) {
                sender.sendMessage(gm2);
                hasPerm = true;
            }
            if(!sender.hasPermission(this.getPermission() + ".spectator")
                    && !sender.hasPermission(this.getPermission() + ".*")) {
                sender.sendMessage(gm3);
                hasPerm = true;
            }
            if(!hasPerm) sender.sendMessage(ChatUtil.colorizeHexAndCode("&C&LDu kannst keinen GameMode auswählen da dir das Recht dazu fehlt."));
            sender.sendMessage(ChatUtil.colorizeHexAndCode("&8========== " + MixelCorePlugin.PREFIX + " &8=========="));

            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "survival": case "s": case "0":
                    if(!sender.hasPermission(this.getPermission() + ".survival")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".survival");
                        return true;
                    }
                    targetMode = GameMode.SURVIVAL;
                    break;
                case "creative": case "c": case "1":
                    if(!sender.hasPermission(this.getPermission() + ".creative")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".creative");
                        return true;
                    }
                    targetMode = GameMode.CREATIVE;
                    break;
                case "adventure": case "a": case "2":
                    if(!sender.hasPermission(this.getPermission() + ".adventure")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".adventure");
                        return true;
                    }
                    targetMode = GameMode.ADVENTURE;
                    break;
                case "spectator": case "spec": case "3":
                    if(!sender.hasPermission(this.getPermission() + ".spectator")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".spectator");
                        return true;
                    }
                    targetMode = GameMode.SPECTATOR;
                    break;
                default: ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cDer angegebene Gamemode ist nicht verfügbar."); return true;
            }
            target.setGameMode(targetMode);
            ChatUtil.replySenderComponent(target, MixelCorePlugin.prefix + "&aDein Spielmodus wurde auf &6" + targetMode.name() + " &agesetzt.");
            return true;
        }

        if (args.length == 2) {
            if(!sender.hasPermission(this.getPermission() + ".others")
                    && !sender.hasPermission(this.getPermission() + ".*"))
                return true;
            switch (args[0].toLowerCase()) {
                case "survival": case "s": case "0":
                    if(!sender.hasPermission(this.getPermission() + ".others.survival")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".others.survival");
                        return true;
                    }
                    targetMode = GameMode.SURVIVAL;
                    break;
                case "creative": case "c": case "1":
                    if(!sender.hasPermission(this.getPermission() + ".others.creative")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".others.creative");
                        return true;
                    }
                    targetMode = GameMode.CREATIVE;
                    break;
                case "adventure": case "a": case "2":
                    if(!sender.hasPermission(this.getPermission() + ".others.adventure")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".others.adventure");
                        return true;
                    }
                    targetMode = GameMode.ADVENTURE;
                    break;
                case "spectator": case "spec": case "3":
                    if(!sender.hasPermission(this.getPermission() + ".others.spectator")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix
                                + "&cDir fehlt die Berechtigung: &6" + this.getPermission() + ".others.spectator");
                        return true;
                    }
                    targetMode = GameMode.SPECTATOR;
                    break;
                default: ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cDer angegebene Gamemode ist nicht verfügbar."); return true;
            }
            target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                ChatUtil.replySenderComponent(sender, MixelCorePlugin.prefix + "&cDer angegebene Spieler ist nicht online.");
                return true;
            }
            target.setGameMode(targetMode);
            ChatUtil.replySenderComponent(target, MixelCorePlugin.prefix + "&aDein Spielmodus wurde auf &6" + targetMode.name() + " &agesetzt.");
            ChatUtil.replySenderComponent(player, MixelCorePlugin.prefix + "&aDu hast den Spielmodus von: &6" + target.getName() + " &aauf &6" + targetMode.name() + " &agesetzt.");
            return true;
        }

        ChatUtil.replySenderComponent(sender, this.getUsage());

        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> available = new ArrayList<>();
            if(sender.hasPermission(this.getPermission() + ".survival")
                    || sender.hasPermission(this.getPermission() + ".*")) {
                available.add("survival");
                available.add("0");
                available.add("s");
            }
            if(sender.hasPermission(this.getPermission() + ".creative")
                    || sender.hasPermission(this.getPermission() + ".*")) {
                available.add("creative");
                available.add("1");
                available.add("c");
            }
            if(sender.hasPermission(this.getPermission() + ".adventure")
                    || sender.hasPermission(this.getPermission() + ".*")) {
                available.add("adventure");
                available.add("2");
                available.add("a");
            }
            if(sender.hasPermission(this.getPermission() + ".spectator")
                    || sender.hasPermission(this.getPermission() + ".*")) {
                available.add("spectator");
                available.add("3");
                available.add("spec");
            }

            final List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], available, completions);
            Collections.sort(completions);
            return completions;
        }
        if(args.length == 2) {
            if(!sender.hasPermission(this.getPermission() + ".others")) return null;
            List<String> available = new ArrayList<>();
            for(Player all : Bukkit.getOnlinePlayers()) {
                available.add(all.getName());
            }
            final List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[1], available, completions);
            Collections.sort(completions);
            return completions;
        }
        return null;
    }

}
