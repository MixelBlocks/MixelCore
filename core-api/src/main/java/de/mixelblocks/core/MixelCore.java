package de.mixelblocks.core;

import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.economy.EconomySystem;
import de.mixelblocks.core.hack.HackManager;
import de.mixelblocks.core.permissions.PermissionManager;
import de.mixelblocks.core.player.CoreOfflinePlayer;
import de.mixelblocks.core.player.CorePlayer;
import de.mixelblocks.core.player.CorePlayerManager;
import de.mixelblocks.core.server.WhitelistManager;

import java.util.Map;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface MixelCore {

    /**
     * returns the hacky solution registration manager
     * @return hackManager
     */
    HackManager hacks();

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
     * returns the economy system provider instance
     * @return economySystem
     */
    EconomySystem economySystem();

    /**
     * returns the instance of the luckperms permission manager
     * @return permissionManager
     */
    PermissionManager permissionManager();

    /**
     * returns the instance of the whitelist manager
     * @return whitelistManager
     */
    WhitelistManager whitelistManager();

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
