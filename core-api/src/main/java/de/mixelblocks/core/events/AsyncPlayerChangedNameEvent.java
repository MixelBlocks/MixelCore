package de.mixelblocks.core.events;

import de.mixelblocks.core.player.CorePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class AsyncPlayerChangedNameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private CorePlayer player;
    private String oldName;

    /**
     * @param player
     * @param oldName
     */
    public AsyncPlayerChangedNameEvent(CorePlayer player, String oldName) {
        this.player = player;
        this.oldName = oldName;
    }

    /**
     * get the player which is meant by changed name in the event
     * @return player
     */
    public CorePlayer getPlayer() {
        return player;
    }

    /**
     * get the old name
     * @return oldName
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * get the new name ( current player name )
     * @return newName
     */
    public String getNewName() {
        return player.username();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
