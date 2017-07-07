package com.gmail.logout400.Heads;

import com.gmail.logout400.Heads.commands.HeadsCommand;
import com.gmail.logout400.Heads.listeners.SkullBreakListener;
import com.gmail.logout400.Heads.listeners.SkullDropListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Heads extends JavaPlugin {

    Messages messages = new Messages();

    private void loadLanguageConfig() {
        messages.load(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig(); //Copies over config.yml, if non-existent
        loadLanguageConfig();

        HeadsCommand headsCommand = new HeadsCommand(this, messages);
        getCommand("head").setExecutor(headsCommand);
        getCommand("heads").setExecutor(headsCommand);

        getServer().getPluginManager().registerEvents(new SkullDropListener(getConfig()), this);
        getServer().getPluginManager().registerEvents(new SkullBreakListener(getConfig()), this);
    }

    public String getVersion() {
        return getDescription().getVersion();
    }

    public void reload() {
        super.reloadConfig();
        this.loadLanguageConfig();
    }

}
