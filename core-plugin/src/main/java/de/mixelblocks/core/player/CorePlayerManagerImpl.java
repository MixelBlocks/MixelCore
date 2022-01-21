package de.mixelblocks.core.player;

import de.mixelblocks.core.MixelCorePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CorePlayerManagerImpl implements CorePlayerManager {

    private final MixelCorePlugin plugin;

    private final Map<UUID, CorePlayer> onlinePlayers;

    public CorePlayerManagerImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
        this.onlinePlayers = new HashMap<>();
    }

    @Override
    public Map<UUID, CorePlayer> allOnline() {
        return onlinePlayers;
    }

    @Override
    public CorePlayer online(UUID uuid) {
        if(allOnline().containsKey(uuid)) return allOnline().get(uuid);
        return null;
    }

    @Override
    public CoreOfflinePlayer offline(UUID uuid) {
        return new CoreOfflinePlayerImpl(uuid);
    }

    public MixelCorePlugin getPlugin() {
        return plugin;
    }

}
