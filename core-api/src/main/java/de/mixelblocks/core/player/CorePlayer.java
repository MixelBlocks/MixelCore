package de.mixelblocks.core.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface CorePlayer extends CoreOfflinePlayer {

    /**
     * get the player if online or null
     * @return player
     */
    default Player getPlayer() {
        return this.online();
    }

    /**
     * get the offline player
     * @return offlinePlayer
     */
    default OfflinePlayer getOfflinePlayer() {
        return this.offline();
    }

    /**
     * get the offline instance of the core player
     * @return offlineCorePlayer
     */
    default CoreOfflinePlayer offlineCorePlayer() {
        return this;
    }

    /**
     * synchronized state of the player
     * @return synchronized
     */
    boolean isSynchronized();

    /**
     * synchronized state of the player
     * @return synchronized
     */
    boolean synced();

    /**
     * if the player is synced set this to true ( Listeners will lock all interactions if this stay false )
     * @param synchronizedPlayer
     */
    void setSynchronizedPlayer(boolean synchronizedPlayer);

    /**
     * Save the inventory / ender chest / potion effects / game mode / experience to database
     * @return success
     */
    boolean savePlayerData();

    /**
     * load the inventory / ender chest / potion effects / game mode / experience from database and apply to player
     * @return success
     */
    boolean loadPlayerData();

    /**
     * Vanish system
     */
    void hidePlayer();

    /**
     * Vanish system
     */
    void showPlayer();

    /**
     * Send the player to another server
     * @param serverName
     */
    void connectServer(String serverName);

}
