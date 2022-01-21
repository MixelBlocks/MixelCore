package de.mixelblocks.core.player;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.economy.EconomyPlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CorePlayerImpl implements CorePlayer {

    private final CoreOfflinePlayer offlinePlayer;

    private boolean synchronizedPlayer;

    public CorePlayerImpl(CoreOfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    @Override
    public OfflinePlayer offline() {
        return offlinePlayer.offline();
    }

    @Override
    public Player online() {
        return offlinePlayer.online();
    }

    @Override
    public CorePlayerSettings settings() {
        return offlinePlayer.settings();
    }

    @Override
    public EconomyPlayerData economy() {
        return offlinePlayer.economy();
    }

    @Override
    public UUID uuid() {
        return offlinePlayer.uuid();
    }

    @Override
    public String username() {
        return offlinePlayer.username();
    }

    @Override
    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }

    @Override
    public boolean isOn() {
        return offlinePlayer.isOn();
    }

    @Override
    public boolean isSynchronized() {
        return synchronizedPlayer;
    }

    @Override
    public boolean synced() {
        return synchronizedPlayer;
    }

    @Override
    public void setSynchronizedPlayer(boolean synchronizedPlayer) {
        this.synchronizedPlayer = synchronizedPlayer;
    }

    @Override
    public boolean savePlayerData() {
        return true;
    }

    @Override
    public boolean loadPlayerData() {
        return true;
    }

    @Override
    public void hidePlayer() {

    }

    @Override
    public void showPlayer() {

    }

    @Override
    public void connectServer(String serverName) {
        MixelCorePlugin.api().proxyManager().connect(this, serverName);
    }

}
