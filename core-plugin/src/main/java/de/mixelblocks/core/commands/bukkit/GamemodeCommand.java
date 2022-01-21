package de.mixelblocks.core.commands.bukkit;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.util.ChatUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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
        this.setUsage(ChatUtil.colorizeHexAndCode(MixelCorePlugin.prefix + "&r&c/" + this.getName() + "[game mode] [<SpielerName>]"));

        aliases.add("gm");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtil.replySender(sender, MixelCorePlugin.prefix + "&cYou can only run this command as a player.");
            return true;
        }

        Player player = (Player) sender;

        GameMode targetMode;

        Player target = player;

        if(args.length == 0) {

            Component header = ChatUtil.toColoredComponent("&8==========" + MixelCorePlugin.PREFIX + "&9==========");
            Component gm0 = ChatUtil.toColoredComponent("&7-&r &6› &6SURVIVAL");
            Component gm1 = ChatUtil.toColoredComponent("&7-&r &6› &6CREATIVE");
            Component gm2 = ChatUtil.toColoredComponent("&7-&r &6› &6ADVENTURE");
            Component gm3 = ChatUtil.toColoredComponent("&7-&r &6› &6SPECTATOR");
            Component footer = ChatUtil.toColoredComponent("&8==========" + MixelCorePlugin.PREFIX + "&9==========");

            gm0.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 0"));
            gm1.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 1"));
            gm2.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 2"));
            gm3.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/gm 3"));

            gm0.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, ChatUtil.toColoredComponent(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6SURVIVAL")));
            gm1.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, ChatUtil.toColoredComponent(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6CREATIVE")));
            gm2.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, ChatUtil.toColoredComponent(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6ADVENTURE")));
            gm3.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, ChatUtil.toColoredComponent(MixelCorePlugin.prefix + "&aKlick! Setzt deinen Spielmodus auf: &6SPECTATOR")));

            Component fullComponent = Component.join(
                    Component.newline(),
                    header,gm0,gm1,gm2,gm3,footer
            );

            ChatUtil.replySender(sender, fullComponent);

        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "survival": case "s": case "0": targetMode = GameMode.SURVIVAL; break;
                case "creative": case "c": case "1": targetMode = GameMode.CREATIVE; break;
                case "adventure": case "a": case "2": targetMode = GameMode.ADVENTURE; break;
                case "spectator": case "spec": case "3": targetMode = GameMode.SPECTATOR; break;
                default: ChatUtil.replySender(sender, MixelCorePlugin.prefix + "&cDer angegebene Gamemode ist nicht verfügbar."); return true;
            }
            target.setGameMode(targetMode);
            ChatUtil.replySender(target, MixelCorePlugin.prefix + "&aDein Spielmodus wurde auf &6" + targetMode.name() + " &agesetzt.");
        }

        if (args.length == 2) {
            if(!sender.hasPermission(this.getPermission() + ".others")) return true;
            switch (args[0].toLowerCase()) {
                case "survival": case "s": case "0": targetMode = GameMode.SURVIVAL; break;
                case "creative": case "c": case "1": targetMode = GameMode.CREATIVE; break;
                case "adventure": case "a": case "2": targetMode = GameMode.ADVENTURE; break;
                case "spectator": case "spec": case "3": targetMode = GameMode.SPECTATOR; break;
                default: ChatUtil.replySender(sender, MixelCorePlugin.prefix + "&cDer angegebene Gamemode ist nicht verfügbar."); return true;
            }
            target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                ChatUtil.replySender(sender, MixelCorePlugin.prefix + "&cDer angegebene Spieler ist nicht online.");
                return true;
            }
            target.setGameMode(targetMode);
            ChatUtil.replySender(target, MixelCorePlugin.prefix + "&aDein Spielmodus wurde auf &6" + targetMode.name() + " &agesetzt.");
            ChatUtil.replySender(player, MixelCorePlugin.prefix + "&aDu hast den Spielmodus von: &6" + target.getName() + " &aauf &6" + targetMode.name() + " &agesetzt.");
        }

        ChatUtil.replySender(sender, this.getUsage());

        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> available = new ArrayList<>();
            available.add("survival");
            available.add("0");
            available.add("s");
            available.add("creative");
            available.add("1");
            available.add("c");
            available.add("adventure");
            available.add("2");
            available.add("a");
            available.add("spectator");
            available.add("3");
            available.add("spec");
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
