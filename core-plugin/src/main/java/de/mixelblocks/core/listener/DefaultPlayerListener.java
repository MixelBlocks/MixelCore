package de.mixelblocks.core.listener;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.events.CorePlayerJoinEvent;
import de.mixelblocks.core.events.CorePlayerQuitEvent;
import de.mixelblocks.core.events.AsyncPlayerChangedNameEvent;
import de.mixelblocks.core.player.CoreOfflinePlayerImpl;
import de.mixelblocks.core.player.CorePlayer;
import de.mixelblocks.core.player.CorePlayerImpl;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class DefaultPlayerListener implements Listener {

    private final MixelCorePlugin plugin;

    public DefaultPlayerListener(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL) // priority call middle
    public void joinMessage(PlayerJoinEvent event) {
        event.joinMessage(null);
    }

    @EventHandler(priority = EventPriority.NORMAL) // priority call middle
    public void quitMessage(PlayerQuitEvent event) {
        event.quitMessage(null);
    }

    @EventHandler(priority = EventPriority.NORMAL) // priority call middle
    public void deathMessage(PlayerDeathEvent event) {
        event.deathMessage(null);
    }

    @EventHandler(priority = EventPriority.LOWEST) // priority call first
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer() == null) return;
        CorePlayer corePlayer = new CorePlayerImpl(new CoreOfflinePlayerImpl(event.getPlayer()));
        new Thread(() -> {
            try {
                Document uuidDoc = plugin.db().getDocument("uuids", "uuid", corePlayer.uuid().toString());
                Document nameDoc = plugin.db().getDocument("uuids", "playerName", corePlayer.username());
                if(uuidDoc == null && nameDoc == null) {
                    Document insert = plugin.db().buildDocument(corePlayer.uuid().toString(), new Object[][] {
                            {
                                    "playerName", corePlayer.username()
                            },
                            {
                                    "uuid", corePlayer.uuid().toString()
                            }
                    });
                    plugin.db().insertDocument("uuids", insert);
                } else if(nameDoc == null && uuidDoc != null) {
                    String oldName = uuidDoc.getString("playerName");
                    Document insert = plugin.db().buildDocument(corePlayer.uuid().toString(), new Object[][] {
                            {
                                    "playerName", corePlayer.username()
                            },
                            {
                                    "uuid", corePlayer.uuid().toString()
                            }
                    });
                    plugin.db().replaceDocument("uuids", corePlayer.uuid().toString(), insert);
                    Bukkit.getPluginManager().callEvent(new AsyncPlayerChangedNameEvent(corePlayer, oldName));
                }
            } catch(Exception e) {
            }
        }, "UUID-Check").start();

        Bukkit.getPluginManager().callEvent(new CorePlayerJoinEvent(corePlayer));
    }

    @EventHandler(priority = EventPriority.HIGHEST) // priority call last
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        if(plugin.getApiImplementation().playerManager().allOnline().containsKey(event.getPlayer().getUniqueId()))
            Bukkit.getPluginManager().callEvent(new CorePlayerQuitEvent(plugin.getApiImplementation().playerManager().allOnline().get(event.getPlayer().getUniqueId())));
    }

    @EventHandler(priority = EventPriority.LOWEST) // priority call first
    public void onCorePlayerJoin(CorePlayerJoinEvent event) {
        if(event.getCorePlayer() == null) return;
        plugin.getApiImplementation().playerManager().allOnline().put(event.getCorePlayer().uuid(), event.getCorePlayer());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if(event.getCorePlayer().loadPlayerData()) event.getCorePlayer().setSynchronizedPlayer(true);
        }, 20L);
    }

    @EventHandler(priority = EventPriority.HIGHEST) // priority call last
    public void onCorePlayerQuit(CorePlayerQuitEvent event) {
        if(event.getCorePlayer() == null) return;
        event.getCorePlayer().setSynchronizedPlayer(false);
        if(event.getCorePlayer().savePlayerData()) event.getCorePlayer().setSynchronizedPlayer(true);
        plugin.getApiImplementation().playerManager().allOnline().remove(event.getCorePlayer().uuid());
    }

    // ============================================= SYNC CANCEL ============================================= \\
    @EventHandler
    public void syncCancel(PlayerCommandPreprocessEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerMoveEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerInteractEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(AsyncPlayerChatEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerPickupItemEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerDropItemEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerPickupArrowEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(PlayerPickupExperienceEvent event) {
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(event.getPlayer().getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    @EventHandler
    public void syncCancel(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        CorePlayer corePlayer = MixelCorePlugin.api().playerManager().online(player.getUniqueId());
        if(!corePlayer.isSynchronized()) event.setCancelled(true);
    }
    // ============================================= SYNC CANCEL ============================================= \\

}
