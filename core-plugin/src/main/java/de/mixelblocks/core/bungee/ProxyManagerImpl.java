package de.mixelblocks.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.player.CorePlayer;
import org.bukkit.entity.Player;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ProxyManagerImpl implements ProxyManager {

    private final MixelCorePlugin plugin;

    public ProxyManagerImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void connect(CorePlayer player, String server) {
        if(player.online() == null) return;
        connect(player.online(), server);
    }

    @Override
    public void connect(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            plugin.getLogger().info("[BungeeAPI - ServerConnector] §aOKay. Trying send Player §6" + player.getName() + " §ato server: §e" + server);
        } catch(Exception except) {
        }
    }

}
