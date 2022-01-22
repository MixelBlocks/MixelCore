package de.mixelblocks.core.hack;

public interface HackManager {

    /**
     * Register a Hacky solution and apply it over the hack API
     * @param hack
     * @return success
     */
    boolean register(Class<? extends Hack> registration, Hack hack);

    /**
     * Get the intance of a Hack
     * @param registration
     * @return hack
     */
    Hack getRegistered(Class<? extends Hack> registration);

    /**
     * unregister a Hack
     * @param registration the registered hack class
     * @return hack
     */
    Hack unregister(Class<? extends Hack> registration);

}
