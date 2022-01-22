package de.mixelblocks.core.listener;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.permissions.PermissionManager;
import de.mixelblocks.core.util.ChatUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.ChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

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
            message = ChatUtil.colorizeCode(message);
        }

        if(event.getPlayer().hasPermission("core.chat.colorized.hex")) {
            message = ChatUtil.colorizeHex(message);
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

        String prefix = ChatUtil.colorizeHexAndCode(perms.resolvePlayerGroupPrefix(event.getPlayer()));

        format = ChatUtil.colorizeHexAndCode(ChatUtil.isPlaceholderAPIEnabled() ? ChatUtil.replacePlaceholders(player, format) : format);
        event.setFormat(format.replace("{message}", (player.hasPermission("core.chat.colorcodes") && player.hasPermission("core.chat.hexcodes")) ?
                ChatUtil.colorizeHexAndCode(message) : (player.hasPermission("core.chat.colorcodes") ? ChatUtil.colorizeCode(message) : (player.hasPermission("core.chat.hexcodes") ?
                ChatUtil.colorizeHex(message) : message))).replace("%", "%%"));
    }

}
