package de.mixelblocks.core.server;

import de.mixelblocks.core.player.CoreOfflinePlayer;
import de.mixelblocks.core.player.CorePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface WhitelistManager {

    /**
     * Enable the servers whitelist
     */
    void setWhitelistEnabled();

    /**
     * Disable the servers whitelist
     */
    void setWhitelistDisabled();


    /**
     * Check if the servers whitelist is active
     * @return isWhitelisted
     */
    boolean isWhitelisted();

    /**
     * Check if a given Player is whitelisted
     * @param player
     * @return isWhitelisted
     */
    boolean isWhitelistedPlayer(Player player);

    /**
     * Check if a given Player is whitelisted
     * @param player
     * @return isWhitelisted
     */
    boolean isWhitelistedPlayer(CorePlayer player);

    /**
     * Check if a given Player is whitelisted
     * @param player
     * @return isWhitelisted
     */
    boolean isWhitelistedPlayer(CoreOfflinePlayer player);

    /**
     * Check if a given uuid string is whitelisted
     * @param uuid
     * @return isWhitelisted
     */
    boolean isWhitelistedPlayer(String uuid);

    /**
     * Check if a given uuid is whitelisted
     * @param uuid
     * @return isWhitelisted
     */
    boolean isWhitelistedPlayer(UUID uuid);

    /**
     * Add a player uuid to the whitelist
     * @param uuid
     * @param global should the player be able to join ALL whitelisted servers
     */
    void addWhitelistedPlayer(String uuid, boolean global);

    /**
     * Add a player uuid to the whitelist
     * @param uuid
     * @param server the server name where the player should be able to join
     */
    void addWhitelistedPlayer(String uuid, String server);

    /**
     * Complete deletion of a player from the whitelists
     * @param uuid
     */
    void deleteWhitelistPlayer(String uuid);

    /**
     * removes the possibility for a player to join global
     * @param uuid
     */
    void deleteGlobalWhitelistPlayer(String uuid);

    /**
     * remove a server from the whitelisted servers list ( Sets global to false )
     * @param uuid
     * @param server
     */
    void removeWhitelistedServerFromPlayer(String uuid, String server);

    /**
     * Change the displayed reason text for the whitelist.
     * @param reasonText
     * @return success
     */
    boolean setWhitelistReason(String reasonText);

    /**
     * Get the set reason for the whitelist.
     * @return whitelistReason
     */
    String getWhitelistReason();

}
