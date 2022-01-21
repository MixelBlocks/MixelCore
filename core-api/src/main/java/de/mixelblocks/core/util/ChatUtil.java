package de.mixelblocks.core.util;

import de.mixelblocks.core.MixelCore;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
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
     * Colorize &#38;(colorCode) messages
     * @param message
     * @return messageColorized
     */
    public static String colorizeCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Colorize &#38;#(hexCode) messages
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
     * Colorize messages with &#38;#(hexCode) and &#38;(colorCode)
     * @param message
     * @return messageColorized
     */
    public static String colorizeHexAndCode(String message) {
        return colorizeCode(colorizeHex(message));
    }

    /**
     * @param message
     * @return
     */
    public static Component toComponent(String message)  {
        return Component.text(message);
    }

    /**
     * @param message
     * @return component
     */
    public static Component toColoredComponent(String message)  {
        return Component.text(colorizeHexAndCode(message));
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

    /**
     * Reply with a message to a sender
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replySender(CommandSender sender, String message) {
        sender.sendMessage(
                Component.text(
                        colorizeHexAndCode(message)
                )
        );
        return true;
    }

    /**
     * Reply with a component message to a sender
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replySender(CommandSender sender, Component message) {
        sender.sendMessage(message);
        return true;
    }

    /**
     * Reply with a actionbar popup to a player
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replyHologram(Player sender, String message) {
        sender.sendActionBar(
                Component.text(
                        colorizeHexAndCode(message)
                )
        );
        return true;
    }

    /**
     * reply with a title
     * @param sender
     * @param message ( The smaller shown text of the title )
     * @return success
     */
    public static boolean replyTitle(Player sender, String message) {
        sender.showTitle(Title.title(
                Component.text(
                        colorizeHexAndCode("")
                ),
                Component.text(
                        colorizeHexAndCode(message)
                )
        ));
        return true;
    }

    /**
     * reply with a title
     * @param sender
     * @param title ( The bigger shown text of the title )
     * @param message ( The smaller shown text of the title )
     * @return success
     */
    public static boolean replyTitle(Player sender, String title, String message) {
        sender.showTitle(Title.title(
                Component.text(
                        colorizeHexAndCode(title)
                ),
                Component.text(
                        colorizeHexAndCode(message)
                ),
                Title.Times.of(Duration.ofSeconds(2), Duration.ofSeconds(2), Duration.ofSeconds(2))
        ));
        return true;
    }

    /**
     * reply with a title
     * @param sender
     * @param title ( The bigger shown text of the title )
     * @param message ( The smaller shown text of the title )
     * @param fadeIn the time to show the title in seconds
     * @param stay the time to stay of title in seconds
     * @param fadeOut the time to hide the title in seconds
     * @return success
     */
    public static boolean replyTitle(Player sender, String title, String message, int fadeIn, int stay, int fadeOut) {
        sender.showTitle(Title.title(
                Component.text(
                        colorizeHexAndCode(title)
                ),
                Component.text(
                        colorizeHexAndCode(message)
                ),
                Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut))
        ));
        return true;
    }

    /**
     * reply with a title
     * @param sender
     * @param title ( The bigger shown text of the title )
     * @param message ( The smaller shown text of the title )
     * @param stay the time to stay of title in seconds
     * @return success
     */
    public static boolean replyTitle(Player sender, String title, String message, int stay) {
        sender.showTitle(Title.title(
                Component.text(
                        colorizeHexAndCode(title)
                ),
                Component.text(
                        colorizeHexAndCode(message)
                ),
                Title.Times.of(Duration.ofMillis(150), Duration.ofSeconds(stay), Duration.ofMillis(150))
        ));
        return true;
    }

}
