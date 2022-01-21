package de.mixelblocks.core.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static access to all methods ( you cannot instantiate an object )
 *
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ChatUtil {

    private ChatUtil() {} // prevent instantiation

    /**
     * Colorize &(colorCode) messages
     * @param message
     * @return messageColorized
     */
    public static String colorizeCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Colorize &#(hexCode) messages
     * @param message
     * @return messageColorized
     */
    public static String colorizeHex(String message) {
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

    /**
     * Colorize messages with &#(hexCode) and &(colorCode)
     * @param message
     * @return messageColorized
     */
    public static String colorizeHexAndCode(String message) {
        return colorizeCode(colorizeHex(message));
    }

    /**
     * Translate placeholders of placeholder api ( if not enabled placeholders will not be translated )
     * @param player
     * @param message
     * @return messageReplacement
     */
    public static String replacePlaceholders(Player player, String message) {
        return isPlaceholderAPIEnabled() ? PlaceholderAPI.setPlaceholders(player, message) : message;
    }

    /**
     * Check if placeholder api is enabled
     * @return enabled
     */
    public static boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

}
