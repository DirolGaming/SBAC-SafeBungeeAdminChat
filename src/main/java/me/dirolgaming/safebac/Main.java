package me.dirolgaming.safebac;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

public class Main extends Plugin {
    private Configuration config;

    @EventHandler
    public void onEnable(){
        setupConfig();
        loadConfig();
        MetricsLite metrics = new MetricsLite(this);
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_AC(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_ACT(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_HELPOP(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new Listener_CHAT(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_SAFEBAC(this));
        getLogger().info("SafeBAC version " + this.getDescription().getVersion() + " has been enabled.");
    }
    public void setupConfig()
    {
        try
        {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                Files.copy(this.getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
            }
            loadConfig();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try
        {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Configuration getConfig()
    {
        return config;
    }
}
