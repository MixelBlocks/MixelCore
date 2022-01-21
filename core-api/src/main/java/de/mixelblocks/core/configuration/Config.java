package de.mixelblocks.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface Config {

    /**
     * To specify the Configuration load Type
     */
    enum ConfigType {
        /**
         * YamlConfiguration.loadConfiguration
         */
        YAML,
        /**
         * Not implemented YET
         */
        JSON;
    }

    /**
     * Reload the configuration
     */
    void reloadConfig() throws OperationNotSupportedException;

    /**
     * tries to save the config to the given file
     * @throws IOException
     */
    void save() throws IOException;

    /**
     * Returns the Bukkit FileConfiguration object
     * @return configuration
     */
    FileConfiguration get();

}
