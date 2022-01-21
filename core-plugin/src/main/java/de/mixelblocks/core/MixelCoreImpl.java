package de.mixelblocks.core;

import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.bungee.ProxyManagerImpl;
import de.mixelblocks.core.economy.EconomySystem;
import de.mixelblocks.core.economy.EconomySystemImpl;
import de.mixelblocks.core.player.CorePlayerManager;
import de.mixelblocks.core.player.CorePlayerManagerImpl;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelCoreImpl implements MixelCore {

    private final MixelCorePlugin plugin;
    private final ProxyManager proxyManager;
    private final CorePlayerManager playerManager;
    private final EconomySystem economySystem;

    public MixelCoreImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
        this.proxyManager = new ProxyManagerImpl(plugin);
        this.playerManager = new CorePlayerManagerImpl(plugin);
        this.economySystem = new EconomySystemImpl(plugin);
    }

    public MixelCorePlugin getPlugin() {
        return plugin;
    }

    @Override
    public ProxyManager proxyManager() {
        return proxyManager;
    }

    @Override
    public CorePlayerManager playerManager() {
        return playerManager;
    }

    @Override
    public EconomySystem economySystem() {
        return economySystem;
    }
}
