package de.mixelblocks.core.listener;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.permissions.PermissionManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class DefaultChatListener implements Listener {

    private final MixelCorePlugin plugin;

    public DefaultChatListener(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer() == null) return;
        String message = event.getMessage();

        if(event.getPlayer().hasPermission("core.chat.colorized")) {
            message = colorize(message);
        }

        if(event.getPlayer().hasPermission("core.chat.colorized.hex")) {
            message = translateHexColorCodes(message);
        }

        Player player = event.getPlayer();
        PermissionManager perms = plugin.getApiImplementation().permissionManager();
        String group = perms.getDefaultPlayerGroupId(player);

        String format = ((String) Objects.<String>requireNonNull(
                plugin.getChatFormatConfig().get().getString((plugin.getChatFormatConfig().get().getString("group-formats." + group) != null) ? ("group-formats." + group) : "chat-format")))
                .replace("{world}", player.getWorld().getName())
                .replace("{prefix}", perms.getPrefix(player))
                .replace("{prefixes}", perms.getPrefixes(player))
                .replace("{name}", player.getName())
                .replace("{suffix}", perms.getSuffix(player))
                .replace("{suffixes}", perms.getSuffixes(player))
                .replace("{username-color}", (perms.playerMeta(player).getMetaValue("username-color") != null) ? perms.playerMeta(player).getMetaValue("username-color") : ((perms.groupMeta(group).getMetaValue("username-color") != null) ? perms.groupMeta(group).getMetaValue("username-color") : ""))
                .replace("{message-color}", (perms.playerMeta(player).getMetaValue("message-color") != null) ? perms.playerMeta(player).getMetaValue("message-color") : ((perms.groupMeta(group).getMetaValue("message-color") != null) ? perms.groupMeta(group).getMetaValue("message-color") : ""));

        String prefix = translateHexColorCodes(colorize(perms.resolvePlayerGroupPrefix(event.getPlayer())));

        format = translateHexColorCodes(colorize(isPlaceholderAPIEnabled() ? PlaceholderAPI.setPlaceholders(player, format) : format));
        event.setFormat(format.replace("{message}", (player.hasPermission("core.chat.colorcodes") && player.hasPermission("core.chat.hexcodes")) ?
                translateHexColorCodes(colorize(message)) : (player.hasPermission("core.chat.colorcodes") ? colorize(message) : (player.hasPermission("core.chat.hexcodes") ?
                translateHexColorCodes(message) : message))).replace("%", "%%"));
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        char colorChar = '§';
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "§x§" + group
                    .charAt(0) + '§' + group.charAt(1) + '§' + group
                    .charAt(2) + '§' + group.charAt(3) + '§' + group
                    .charAt(4) + '§' + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }

    private boolean isPlaceholderAPIEnabled() {
        return plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

}
