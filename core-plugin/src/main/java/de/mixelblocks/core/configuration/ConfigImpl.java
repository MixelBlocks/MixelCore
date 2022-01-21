package de.mixelblocks.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ConfigImpl implements Config {

    private FileConfiguration fileConfig;
    private File configFile;

    public ConfigImpl(File configFile) {
        this.configFile = configFile;
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        this.fileConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    @Override
    public void save() throws IOException {
        fileConfig.save(configFile);
    }

    @Override
    public FileConfiguration get() {
        return this.fileConfig;
    }

}
