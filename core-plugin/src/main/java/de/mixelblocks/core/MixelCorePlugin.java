package de.mixelblocks.core;

import de.mixelblocks.core.configuration.Config;
import de.mixelblocks.core.configuration.ConfigImpl;
import de.mixelblocks.core.database.MongoDatabaseHandler;
import de.mixelblocks.core.database.RedisHandler;
import de.mixelblocks.core.listener.DefaultChatListener;
import de.mixelblocks.core.listener.DefaultPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelCorePlugin extends JavaPlugin {

    private static MixelCore apiImplementation;

    private Config whitelistConfig;
    private Config worldProtectConfig;
    private Config chatFormatConfig;

    private MongoDatabaseHandler databaseHandler;
    private RedisHandler redisHandler;

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        File whitelistConfigFile = new File(this.getDataFolder(), "configuration/whitelist.yml");
        if(!whitelistConfigFile.exists()) this.saveResource("configuration/whitelist.yml", false);
        whitelistConfig = new ConfigImpl(whitelistConfigFile);

        File chatFormatConfigFile = new File(this.getDataFolder(), "configuration/chatformat.yml");
        if(!chatFormatConfigFile.exists()) this.saveResource("configuration/chatformat.yml", false);
        chatFormatConfig = new ConfigImpl(chatFormatConfigFile);

        File worldProtectConfigFile = new File(this.getDataFolder(), "configuration/worldprotect.yml");
        if(!worldProtectConfigFile.exists()) this.saveResource("configuration/worldprotect.yml", false);
        worldProtectConfig = new ConfigImpl(worldProtectConfigFile);

        databaseHandler = new MongoDatabaseHandler(this.getConfig().getString("database.connectionString"),
                this.getConfig().getString("database.name", "core"));

        redisHandler = new RedisHandler(this.getConfig().getString("redis.connectionString"),
                this.getConfig().getString("redis.authentication", ""));

        // Register Core API as Bukkit ServiceProvider
        Bukkit.getServicesManager().register(MixelCore.class, new MixelCoreImpl(this), this, ServicePriority.High);
        RegisteredServiceProvider<MixelCore> apiProvider = Bukkit.getServicesManager().getRegistration(MixelCore.class);
        if(apiProvider.getProvider() == null) throw new RuntimeException("Mixel-Core has not properly registered its API");
        apiImplementation = apiProvider.getProvider();

        this.registration();

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

    private void registration() {
        Bukkit.getPluginManager().registerEvents(new DefaultPlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DefaultChatListener(this), this);

    }

    public MixelCore getApiImplementation() {
        return apiImplementation;
    }

    public static MixelCore api() {
        return apiImplementation;
    }

    public Config getWhitelistConfig() {
        return whitelistConfig;
    }

    public Config getChatFormatConfig() {
        return chatFormatConfig;
    }

    public Config getWorldProtectConfig() {
        return worldProtectConfig;
    }

    public RedisHandler getRedisHandler() {
        return redisHandler;
    }

    public RedisHandler redis() {
        return redisHandler;
    }

    public MongoDatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public MongoDatabaseHandler db() {
        return databaseHandler;
    }
}
