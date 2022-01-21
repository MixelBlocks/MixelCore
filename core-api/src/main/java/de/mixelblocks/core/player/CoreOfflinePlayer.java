package de.mixelblocks.core.player;

import de.mixelblocks.core.economy.EconomyPlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface CoreOfflinePlayer {

    /**
     * Resolve offline player
     * @return offlinePlayer
     */
    OfflinePlayer offline();

    /**
     * Resolve online player
     * @return player ( returns null when offline )
     */
    Player online();

    /**
     * Resolve player settings
     * @return settings
     */
    CorePlayerSettings settings();

    /**
     * Resolve players economy data
     * @return economyData
     */
    EconomyPlayerData economy();

    /**
     * resolve player uuid
     * @return uuid
     */
    UUID uuid();

    /**
     * Get the players current name
     * @return name
     */
    String username();

    /**
     * Check if the player is currently online at the server
     * @return online
     */
    boolean isOnline();
    /**
     * Check if the player is currently online at the server
     * @return online
     */
    boolean isOn();

}
