package com.gmail.logout400.Heads;

import com.gmail.logout400.Heads.commands.HeadCommand;
import com.gmail.logout400.Heads.commands.HeadsCommand;
import com.gmail.logout400.Heads.listeners.HeadsUpdateListener;
import com.gmail.logout400.Heads.listeners.SkullBreakListener;
import com.gmail.logout400.Heads.listeners.SkullDropListener;
import com.gmail.logout400.Heads.util.PluginLogger;
import com.gmail.logout400.Heads.util.PluginVersion;
import com.gmail.logout400.Heads.util.SimpleConfig;
import com.gmail.logout400.Heads.util.SimpleConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Heads extends JavaPlugin {

    public static Heads INSTANCE;
    public PluginLogger logger;
    public PluginVersion version;
    public SimpleConfigManager manager;
    public SimpleConfig config;
    public SimpleConfig messages;

    @Override
    public void onEnable() {
        INSTANCE = this;
        boolean configUpdated = false;

        this.logger = new PluginLogger();
        this.manager = new SimpleConfigManager();

        this.manager.prepareFile("lang.yml", "lang.yml");
        this.manager.prepareFile("config.yml", "config.yml");

        this.config = this.manager.getNewConfig("config.yml");
        this.messages = this.manager.getNewConfig("lang.yml");
        if (!this.messages.contains("player-offline")) {
            this.messages.set("player-offline", "Player %player% not found !",
                    "Player not found");
            configUpdated = true;
        }
        if (!this.messages.contains("head-given")) {
            this.messages.set("head-given",
                    "You've just given %player% a head !",
                    "When player give head");
            configUpdated = true;
        }
        if (!this.messages.contains("head-received")) {
            this.messages.set("head-received",
                    "You've just received %owner% head !",
                    "When player receive head");
            configUpdated = true;
        }
        if (configUpdated) {
            this.messages.saveConfig();

            this.logger.infoSpacer();
            this.logger.info("Messages File updated ! (lang.yml)");
            this.logger.infoHeader();
        }
        getCommand("head").setExecutor(new HeadCommand());
        getCommand("heads").setExecutor(new HeadsCommand());

        getServer().getPluginManager().registerEvents(new SkullDropListener(), this);
        getServer().getPluginManager().registerEvents(new SkullBreakListener(), this);
        getServer().getPluginManager().registerEvents(new HeadsUpdateListener(), this);

        boolean updateCheck = this.config.getBoolean("update-check");
        this.version = new PluginVersion(
                "http://dev.bukkit.org/server-mods/heads/files.rss");
        if (updateCheck) {
            if (this.version.updateNeeded()) {
                String updateMsg = this.messages.getString("update-msg")
                        .replaceAll("%version%", this.version.getVersion());

                this.logger.infoSpacer();
                this.logger.info("Get it: " + this.version.getLink());
                this.logger.info(updateMsg);
                this.logger.infoHeader();
            }
        }
    }

    public String getVersion() {
        return getDescription().getVersion().toString();
    }
}
