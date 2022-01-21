package de.mixelblocks.core.bungee;

import de.mixelblocks.core.player.CorePlayer;
import org.bukkit.entity.Player;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface ProxyManager {

    /**
     * Send a player to another server
     * @param player the player to send
     * @param server the server where the player should join
     */
    void connect(CorePlayer player, String server);

    /**
     * Send a player to another server
     * @param player the player to send
     * @param server the server where the player should join
     */
    void connect(Player player, String server);

}
