package de.mixelblocks.core.economy;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.player.CoreOfflinePlayerImpl;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class EconomySystemImpl implements EconomySystem {

    private final MixelCorePlugin plugin;

    public EconomySystemImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String currencyName() {
        return null;
    }

    @Override
    public String currency() {
        return null;
    }

    @Override
    public EconomyPlayerData playerData(UUID uuid) {
        return new CoreOfflinePlayerImpl(uuid).economy();
    }
}
