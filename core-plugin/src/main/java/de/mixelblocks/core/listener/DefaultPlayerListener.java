package de.mixelblocks.core.listener;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.events.CorePlayerJoinEvent;
import de.mixelblocks.core.events.CorePlayerQuitEvent;
import de.mixelblocks.core.player.CoreOfflinePlayerImpl;
import de.mixelblocks.core.player.CorePlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class DefaultPlayerListener  implements Listener {

    private final MixelCorePlugin plugin;

    public DefaultPlayerListener(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer() == null) return;
        Bukkit.getPluginManager().callEvent(new CorePlayerJoinEvent(new CorePlayerImpl(new CoreOfflinePlayerImpl(event.getPlayer()))));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        Bukkit.getPluginManager().callEvent(new CorePlayerQuitEvent(new CorePlayerImpl(new CoreOfflinePlayerImpl(event.getPlayer()))));
    }

    @EventHandler
    public void onCorePlayerJoin(CorePlayerJoinEvent event) {
        if(event.getCorePlayer() == null) return;
        plugin.getApiImplementation().playerManager().allOnline().put(event.getCorePlayer().uuid(), event.getCorePlayer());
        event.getCorePlayer().loadPlayerData();
        event.getCorePlayer().setSynchronizedPlayer(true);
    }

    @EventHandler
    public void onCorePlayerQuit(CorePlayerQuitEvent event) {
        if(event.getCorePlayer() == null) return;
        event.getCorePlayer().setSynchronizedPlayer(false);
        event.getCorePlayer().savePlayerData();
        plugin.getApiImplementation().playerManager().allOnline().remove(event.getCorePlayer().uuid());
    }

}
