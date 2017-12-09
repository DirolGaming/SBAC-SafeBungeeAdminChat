package me.dirolgaming.safebac;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class Main extends Plugin {
    final Set<ProxiedPlayer> actlist = Collections.newSetFromMap(new WeakHashMap<>());

    private Path configurationFile;
    private Configuration config;

    @Override
    public void onEnable() {
        configurationFile = Paths.get(getDataFolder().toPath().toAbsolutePath().toString(), "config.yml");

        setupConfig();
        loadConfig();

        getProxy().getPluginManager().registerCommand(this, new Command_AC(this));
        getProxy().getPluginManager().registerCommand(this, new Command_ACT(this));
        getProxy().getPluginManager().registerCommand(this, new Command_HELPOP(this));
        getProxy().getPluginManager().registerListener(this, new Listener_CHAT(this));
        getProxy().getPluginManager().registerCommand(this, new Command_SAFEBAC(this));
        getLogger().info("SafeBAC version " + this.getDescription().getVersion() + " has been enabled.");

        getProxy().getScheduler().runAsync(this, () ->
            new MetricsLite(this)
        );
    }

    private void setupConfig() {
        try {
            if(Files.notExists(configurationFile.getParent()))
                Files.createDirectories(configurationFile.getParent());

            if (!Files.notExists(configurationFile))
                Files.copy(this.getResourceAsStream("config.yml"), configurationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configurationFile.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), configurationFile.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
