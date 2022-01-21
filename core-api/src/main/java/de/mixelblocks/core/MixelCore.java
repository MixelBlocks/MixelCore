package de.mixelblocks.core;

import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.player.CoreOfflinePlayer;
import de.mixelblocks.core.player.CorePlayer;
import de.mixelblocks.core.player.CorePlayerManager;

import java.util.Map;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface MixelCore {

    /**
     * returns the instance of the proxy manager
     * @return proxyManager
     */
    ProxyManager proxyManager();

    /**
     * returns the instance of the core player manager
     * @return corePlayerManager
     */
    CorePlayerManager playerManager();

    /**
     * Resolve a CorePlayer by uuid ( returns null if offline )
     * @param uuid
     * @return corePlayer
     */
    default CorePlayer online(UUID uuid) {
        return this.playerManager().online(uuid);
    }

    /**
     * Resolve a OfflineCorePlayer by uuid
     * @param uuid
     * @return coreOfflinePlayer
     */
    default CoreOfflinePlayer offline(UUID uuid) {
        return this.playerManager().offline(uuid);
    }

    /**
     * resolve all cached instances of online CorePlayers
     * @return onlinePlayers
     */
    default Map<UUID, CorePlayer> allOnline() {
        return this.playerManager().allOnline();
    }

}
