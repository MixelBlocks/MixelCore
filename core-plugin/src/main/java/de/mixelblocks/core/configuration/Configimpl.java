package de.mixelblocks.core.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.IOException;

public class Configimpl implements Config {

    private Config.ConfigType configType;

    private FileConfiguration fileConfig;
    private File configFile;

    public Configimpl(File configFile) throws OperationNotSupportedException {
        this.configFile = configFile;
        this.configType = Config.ConfigType.YAML;
        reloadConfig();
    }

    public Configimpl(File configFile, Config.ConfigType configType) throws OperationNotSupportedException {
        this.configFile = configFile;
        this.configType = configType;
        reloadConfig();
    }

    @Override
    public void reloadConfig() throws OperationNotSupportedException {
        switch(configType) {
            case YAML:
                this.fileConfig = YamlConfiguration.loadConfiguration(configFile);
                break;
            case JSON:
                throw new OperationNotSupportedException("JSON Configutrations are not supported yet!");
        }
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
