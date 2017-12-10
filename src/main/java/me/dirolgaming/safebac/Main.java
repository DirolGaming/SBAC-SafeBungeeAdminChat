package me.dirolgaming.safebac;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        getProxy().getScheduler().runAsync(this, () -> new MetricsLite(this));
        getProxy().getScheduler().runAsync(this, () -> checkupdate());
    }

    private void checkupdate() {
        if (getConfig().getBoolean("check-update")) {
            try {
                HttpURLConnection con = (HttpURLConnection) (new URL("http://www.spigotmc.org/api/general.php")).openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.getOutputStream().write("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=50530".getBytes("UTF-8"));
                String version = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
                con.disconnect();
                if (!version.equals(getDescription().getVersion())) {
                    getLogger().warning("There is an update available for SafeBAC. Please use the latest version at all times.");
                    getLogger().warning("Your version: " + getDescription().getVersion() + " | " + "New version: " + version);
                    getLogger().warning("Download it here: https://www.spigotmc.org/resources/sbac-safebungeeadminchat.50530/");
                }
            } catch (Exception ignored) {
            }
        }
    }
    public void setupConfig() {
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                Files.copy(this.getResourceAsStream("config.yml"), file.toPath());
            }
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
