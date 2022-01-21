package de.mixelblocks.core.player;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CorePlayerSettingsImpl implements CorePlayerSettings {

    private final CorePlayer player;
    private final CoreOfflinePlayer offlinePlayer;

    public CorePlayerSettingsImpl(CorePlayer player) {
        this.player = player;
        this.offlinePlayer = player;
    }

    public CorePlayerSettingsImpl(CoreOfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.player = null;
    }

    @Override
    public CorePlayer getCorePlayer() {
        return player;
    }

    @Override
    public boolean vanished() {
        return false;
    }

    @Override
    public boolean vanish(boolean vanished) {
        return false;
    }
}
