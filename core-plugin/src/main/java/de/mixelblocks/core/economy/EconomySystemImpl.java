package de.mixelblocks.core.economy;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.database.MongoDatabaseHandler;
import de.mixelblocks.core.player.CoreOfflinePlayerImpl;

import java.util.HashMap;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class EconomySystemImpl implements EconomySystem {

    private final MixelCorePlugin plugin;
    private final MongoDatabaseHandler databaseHandler;

    private final HashMap<UUID, EconomyPlayerData> economyPlayerDatas;

    public EconomySystemImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
        this.databaseHandler = new MongoDatabaseHandler(plugin.getConfig().getString("database.connectionString"), "economy");
        this.economyPlayerDatas = new HashMap<>();
    }

    @Override
    public String currencyName() {
        return plugin.getConfig().getString("economy.currency", "Mixels");
    }

    @Override
    public String currency() {
        return plugin.getConfig().getString("economy.short", "ꪑ᥊");
    }

    @Override
    public EconomyPlayerData playerData(UUID uuid) {
        if(!economyPlayerDatas.containsKey(uuid)) {
            economyPlayerDatas.put(uuid, new CoreOfflinePlayerImpl(uuid).economy());
        }
        return economyPlayerDatas.get(uuid);
    }

    @Override
    public MongoDatabaseHandler db() {
        return databaseHandler;
    }

}
