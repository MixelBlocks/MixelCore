package de.mixelblocks.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Static access to all methods ( you cannot instantiate an object )
 *
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelCoreAPI {

    private MixelCoreAPI() {} // prevent instantiation

    /**
     * Static access to the MixelCoreAPI
     * @return mixelCoreApi
     */
    public static MixelCore get() {
        RegisteredServiceProvider<MixelCore> registeredService = Bukkit.getServicesManager().getRegistration(MixelCore.class);
        return registeredService.getProvider();
    }

}
