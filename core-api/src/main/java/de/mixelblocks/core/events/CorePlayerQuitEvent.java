package de.mixelblocks.core.events;

import de.mixelblocks.core.player.CorePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class CorePlayerQuitEvent extends PlayerQuitEvent {

    private static final HandlerList handlers = new HandlerList();

    private CorePlayer corePlayer;

    /**
     * @param player
     */
    public CorePlayerQuitEvent(CorePlayer player) {
        super(player.online(), Component.empty(), QuitReason.DISCONNECTED);
        this.corePlayer = player;
    }

    /**
     * @return corePlayer
     */
    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
