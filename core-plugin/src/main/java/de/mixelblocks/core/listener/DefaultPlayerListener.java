package de.mixelblocks.core.listener;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.events.CorePlayerJoinEvent;
import de.mixelblocks.core.events.CorePlayerQuitEvent;
import de.mixelblocks.core.events.AsyncPlayerChangedNameEvent;
import de.mixelblocks.core.player.CoreOfflinePlayerImpl;
import de.mixelblocks.core.player.CorePlayer;
import de.mixelblocks.core.player.CorePlayerImpl;
import org.bson.Document;
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

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        if(plugin.getApiImplementation().playerManager().allOnline().containsKey(event.getPlayer().getUniqueId()))
            Bukkit.getPluginManager().callEvent(new CorePlayerQuitEvent(plugin.getApiImplementation().playerManager().allOnline().get(event.getPlayer().getUniqueId())));
    }

    @EventHandler
    public void onCorePlayerJoin(CorePlayerJoinEvent event) {
        if(event.getCorePlayer() == null) return;
        plugin.getApiImplementation().playerManager().allOnline().put(event.getCorePlayer().uuid(), event.getCorePlayer());
        if(event.getCorePlayer().loadPlayerData()) event.getCorePlayer().setSynchronizedPlayer(true);
    }

    @EventHandler
    public void onCorePlayerQuit(CorePlayerQuitEvent event) {
        if(event.getCorePlayer() == null) return;
        event.getCorePlayer().setSynchronizedPlayer(false);
        if(event.getCorePlayer().savePlayerData()) event.getCorePlayer().setSynchronizedPlayer(true);
        plugin.getApiImplementation().playerManager().allOnline().remove(event.getCorePlayer().uuid());
    }

}
