package de.mixelblocks.core;

import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.player.CorePlayerManager;

public class MixelCoreImpl implements MixelCore {

    private final MixelCorePlugin plugin;

    public MixelCoreImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    public MixelCorePlugin getPlugin() {
        return plugin;
    }

    @Override
    public ProxyManager proxyManager() {
        return null;
    }

    @Override
    public CorePlayerManager playerManager() {
        return null;
    }
}
