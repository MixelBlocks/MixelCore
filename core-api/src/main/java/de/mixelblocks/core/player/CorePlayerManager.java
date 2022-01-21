package de.mixelblocks.core.player;

import java.util.Map;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface CorePlayerManager {

    /**
     * resolve all cached instances of online CorePlayers
     * @return onlinePlayers
     */
    Map<UUID, CorePlayer> allOnline();

    /**
     * Resolve a CorePlayer by uuid ( returns null if offline )
     * @param uuid
     * @return corePlayer
     */
    CorePlayer online(UUID uuid);

    /**
     * Resolve a OfflineCorePlayer by uuid
     * @param uuid
     * @return coreOfflinePlayer
     */
    CoreOfflinePlayer offline(UUID uuid);

}
