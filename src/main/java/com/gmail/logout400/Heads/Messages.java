package com.gmail.logout400.Heads;

import java.io.File;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Samuel
 */
public final class Messages {

    /*Head*/
    public String headAdded;
    public String headGiven;
    public String commandUse;
    public String headReceived;
    public String playerOffline;

    /*Heads*/
    public String unknownCommand;
    public String pluginVersion;
    public String reloaded;
    public String skullInfo;
    public String notSkull;

    /*Universal*/
    public String permissions;
    public String consoleSender;

    protected Messages() {
    }

    protected void load(Heads plugin) {
        File file = new File(plugin.getDataFolder(), "lang.yml");
        if (!file.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);

        this.headAdded = c.getString("head-added");
        this.headGiven = c.getString("head-given");
        this.commandUse = c.getString("command-usage");
        this.headReceived = c.getString("head-received");
        this.playerOffline = c.getString("player-offline");
        this.unknownCommand = c.getString("unknown-cmd");
        this.pluginVersion = c.getString("plugin-version");
        this.reloaded = c.getString("reloaded");
        this.skullInfo = c.getString("skull-info");
        this.notSkull = c.getString("not-skull");
        this.permissions = c.getString("permissions");
        this.consoleSender = c.getString("console-msg");
    }

}
