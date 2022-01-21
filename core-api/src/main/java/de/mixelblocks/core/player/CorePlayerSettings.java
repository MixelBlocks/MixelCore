package de.mixelblocks.core.player;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface CorePlayerSettings {

    /**
     * Get the current core player
     * @return corePlayer
     */
    CorePlayer getCorePlayer();

    /**
     * check if the player is vanished
     * @return vanished
     */
    boolean vanished();

    /**
     * vanish player ( sez true to hide player and false to show player )
     * @param vanished
     * @return
     */
    boolean vanish(boolean vanished);

}
