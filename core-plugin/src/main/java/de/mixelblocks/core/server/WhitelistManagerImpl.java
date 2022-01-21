package de.mixelblocks.core.server;

import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.player.CoreOfflinePlayer;
import de.mixelblocks.core.player.CorePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class WhitelistManagerImpl implements WhitelistManager {

    private final MixelCorePlugin plugin;

    public WhitelistManagerImpl(MixelCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setWhitelistEnabled() {

    }

    @Override
    public void setWhitelistDisabled() {

    }

    @Override
    public boolean isWhitelisted() {
        return false;
    }

    @Override
    public boolean isWhitelistedPlayer(Player player) {
        return false;
    }

    @Override
    public boolean isWhitelistedPlayer(CorePlayer player) {
        return false;
    }

    @Override
    public boolean isWhitelistedPlayer(CoreOfflinePlayer player) {
        return false;
    }

    @Override
    public boolean isWhitelistedPlayer(String uuid) {
        return false;
    }

    @Override
    public boolean isWhitelistedPlayer(UUID uuid) {
        return false;
    }

    @Override
    public void addWhitelistedPlayer(String uuid, boolean global) {

    }

    @Override
    public void addWhitelistedPlayer(String uuid, String server) {

    }

    @Override
    public void deleteWhitelistPlayer(String uuid) {

    }

    @Override
    public void deleteGlobalWhitelistPlayer(String uuid) {

    }

    @Override
    public void removeWhitelistedServerFromPlayer(String uuid, String server) {

    }

    @Override
    public boolean setWhitelistReason(String reasonText) {
        return false;
    }

    @Override
    public String getWhitelistReason() {
        return null;
    }

}
