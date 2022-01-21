package de.mixelblocks.core.player;

import de.mixelblocks.core.economy.EconomyPlayerData;
import de.mixelblocks.core.economy.EconomyPlayerDataImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CoreOfflinePlayerImpl implements CoreOfflinePlayer {

    private final OfflinePlayer bukkitPlayer;

    private final CorePlayerSettings settings;
    private final EconomyPlayerData economyData;

    public CoreOfflinePlayerImpl(OfflinePlayer bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.settings = new CorePlayerSettingsImpl(this);
        this.economyData = new EconomyPlayerDataImpl(this);
    }

    public CoreOfflinePlayerImpl(UUID uuid) {
        this.bukkitPlayer = Bukkit.getOfflinePlayer(uuid);
        this.settings = new CorePlayerSettingsImpl(this);
        this.economyData = new EconomyPlayerDataImpl(this);
    }

    @Override
    public OfflinePlayer offline() {
        return bukkitPlayer;
    }

    @Override
    public Player online() {
        return bukkitPlayer.isOnline() ? bukkitPlayer.getPlayer() : null;
    }

    @Override
    public CorePlayerSettings settings() {
        return settings;
    }

    @Override
    public EconomyPlayerData economy() {
        return economyData;
    }

    @Override
    public UUID uuid() {
        return bukkitPlayer.getUniqueId();
    }

    @Override
    public String username() {
        return bukkitPlayer.getName();
    }

    @Override
    public boolean isOnline() {
        return bukkitPlayer.isOnline();
    }

    @Override
    public boolean isOn() {
        return isOnline();
    }
}
