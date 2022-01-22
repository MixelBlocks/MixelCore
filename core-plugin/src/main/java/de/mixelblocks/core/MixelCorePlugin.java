package de.mixelblocks.core;

import de.mixelblocks.core.commands.CoreReloadCommand;
import de.mixelblocks.core.commands.bukkit.*;
import de.mixelblocks.core.configuration.Config;
import de.mixelblocks.core.configuration.ConfigImpl;
import de.mixelblocks.core.database.MongoDatabaseHandler;
import de.mixelblocks.core.database.RedisHandler;
import de.mixelblocks.core.hack.defaults.HackChatProtocol;
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

    public static final String
            PLUGIN_NAME = "&#00FF00M&#00EE00I&#00DD00X&#00CC00E&#00BB00L &#FF0000C&#EE0000O&#DD0000R&#CC0000E",
            PREFIX = "&r[&l" + PLUGIN_NAME + "&r]",
            prefix = PREFIX + " &e›&c› &r";

    private static MixelCorePlugin instance;
    public static MixelCorePlugin getInstance() {
        return instance;
    }

    private static MixelCore apiImplementation;

    private Config whitelistConfig;
    private Config worldProtectConfig;
    private Config chatFormatConfig;

    private MongoDatabaseHandler databaseHandler;
    private RedisHandler redisHandler;

    @Override
    public void onLoad() {
        instance = this;
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

        // install hacks
        apiImplementation.hacks().register(HackChatProtocol.class, new HackChatProtocol());

        // Core Commands
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new CoreReloadCommand(this));

        // Bukkit overwrites
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new EnchantCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new ExperienceCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new GamemodeCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new GiveCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new KickCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new PluginsCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new ReloadServerCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new StopCommand(this));
        Bukkit.getCommandMap().register(this.getName().toLowerCase(), new WhiteListCommand(this));

        // Default Listeners
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
