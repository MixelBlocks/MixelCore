package de.mixelblocks.core;

import de.mixelblocks.core.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class MixelCorePlugin extends JavaPlugin {

    private MixelCore apiImplementation;

    private Config whitelistConfig;
    private Config worldProtectConfig;
    private Config chatFormatConfig;

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        // Register Core API as Bukkit ServiceProvider
        Bukkit.getServicesManager().register(MixelCore.class, new MixelCoreImpl(this), this, ServicePriority.High);
        RegisteredServiceProvider<MixelCore> apiProvider = Bukkit.getServicesManager().getRegistration(MixelCore.class);
        if(apiProvider.getProvider() == null) throw new RuntimeException("Mixel-Core has not properly registered its API");
        this.apiImplementation = apiProvider.getProvider();

    }

    @Override
    public void onDisable() {
    }

    public MixelCore getApiImplementation() {
        return apiImplementation;
    }

}
