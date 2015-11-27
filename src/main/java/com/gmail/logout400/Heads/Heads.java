package com.gmail.logout400.Heads;

import com.gmail.logout400.Heads.commands.HeadCommand;
import com.gmail.logout400.Heads.commands.HeadsCommand;
import com.gmail.logout400.Heads.listeners.SkullBreakListener;
import com.gmail.logout400.Heads.listeners.SkullDropListener;
import com.gmail.logout400.Heads.util.PluginLogger;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Heads extends JavaPlugin {

    public static Heads INSTANCE;
    public PluginLogger logger = new PluginLogger();

    protected YamlConfiguration yLang;

    private void loadLanguageConfig() {
        File fLang = new File(getDataFolder(), "lang.yml");
        if (!fLang.exists()) {
            saveResource("lang.yml", false);
        }
        yLang = YamlConfiguration.loadConfiguration(fLang);
    }

    public void reload() {
        reloadConfig();
        loadLanguageConfig();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig(); //Copies over config.yml, if non-existent
        loadLanguageConfig();

        INSTANCE = this;

        getCommand("head").setExecutor(new HeadCommand());
        getCommand("heads").setExecutor(new HeadsCommand());

        getServer().getPluginManager().registerEvents(new SkullDropListener(), this);
        getServer().getPluginManager().registerEvents(new SkullBreakListener(), this);
    }

    public String getVersion() {
        return getDescription().getVersion();
    }
}
