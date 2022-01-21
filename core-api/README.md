## MixelCore API things

---

### Implementing the api via maven

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>de.mixelblocks.myplugin</groupId>
    <artifactId>my-plugin-using-mixel-api</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.build.targetEncoding>UTF-8</project.build.targetEncoding>
        <maven.compiler.source>16</maven.compiler.source>
        <maven.compiler.target>16</maven.compiler.target>
    </properties>
    
    <repositories>
        <repository>
            <id>nexus-snapshots</id>
            <url>https://repo.mixelblocks.de/repository/maven-snapshots/</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>de.mixelblocks.core</groupId>
            <artifactId>core-api</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    
</project>

```

---

### Make mixel-core a dependency in your plugin.yml

```yaml

# MixelCore plugin loads at STARTUP 
depend:
  - MixelCore

```

---

### Resolve the api in your project

```java

package de.mixelblocks.myplugin;

import de.mixelblocks.core.MixelCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPluginMain extends JavaPlugin {

    private MixelCore mixelCoreAPI;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<MixelCore> registeredService = Bukkit.getServicesManager().getRegistration(MixelCore.class);
        if (registeredService.getProvider() != null) {
            this.mixelCoreAPI = registeredService.getProvider();
        }
    }

    public MixelCore getMixelCoreAPI() {
        return mixelCoreAPI;
    }

}

```

---
Copyright (c) 2022 | [LuciferMorningstarDev](https://github.com/LuciferMorningstarDev)