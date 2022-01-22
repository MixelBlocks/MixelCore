package de.mixelblocks.core.hack;

/**
 * Hacky solutions implements this interface to add things
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface Hack {

    /**
     * Gets called if {@link Hacky} annotation is present and use is set to true
     * Example: @Hacky(use = true) class MyHack extends Hack { ... }
     */
    void activate();

    /**
     * unregister gets called when you unregister a hack
     */
    void unregister();

}
