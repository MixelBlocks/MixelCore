package de.mixelblocks.core;

import de.mixelblocks.core.bungee.ProxyManager;
import de.mixelblocks.core.bungee.ProxyManagerImpl;
import de.mixelblocks.core.economy.EconomySystem;
import de.mixelblocks.core.economy.EconomySystemImpl;
import de.mixelblocks.core.hack.HackManager;
import de.mixelblocks.core.hack.HackManagerImpl;
import de.mixelblocks.core.permissions.PermissionManager;
import de.mixelblocks.core.permissions.PermissionManagerImpl;
import de.mixelblocks.core.player.CorePlayerManager;
import de.mixelblocks.core.player.CorePlayerManagerImpl;
import de.mixelblocks.core.server.WhitelistManager;
import de.mixelblocks.core.server.WhitelistManagerImpl;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelCoreImpl implements MixelCore {

    private final MixelCorePlugin plugin;

    private final HackManager hackManager;

    private final ProxyManager proxyManager;
    private final CorePlayerManager playerManager;
    private final PermissionManager permissionManager;
    private final WhitelistManager whitelistManager;
    private final EconomySystem economySystem;


    public MixelCoreImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
        this.hackManager = new HackManagerImpl();
        this.proxyManager = new ProxyManagerImpl(plugin);
        this.playerManager = new CorePlayerManagerImpl(plugin);
        this.whitelistManager = new WhitelistManagerImpl(plugin);
        this.permissionManager = new PermissionManagerImpl(plugin);
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

    @Override
    public PermissionManager permissionManager() {
        return permissionManager;
    }

    @Override
    public WhitelistManager whitelistManager() {
        return whitelistManager;
    }

    @Override
    public HackManager hacks() {
        return hackManager;
    }

}
