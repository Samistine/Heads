package com.gmail.logout400.Heads.listeners;

import com.gmail.logout400.Heads.Heads;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.ChatColor.*;

public class HeadsUpdateListener implements Listener {

    private final Heads plugin;

    public HeadsUpdateListener() {
        this.plugin = Heads.INSTANCE;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.config.getBoolean("update-check")) {
            return;
        }
        if (!plugin.version.updateNeeded()) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission("heads.update")) {
            return;
        }
        String updateMsg = plugin.messages.getString("update-msg").replaceAll("%version%", YELLOW + plugin.version.getVersion() + GRAY + BOLD);

        player.sendMessage(YELLOW + "[Heads] " + GRAY + BOLD + updateMsg);
        player.sendMessage(YELLOW + "[Heads] " + GRAY + plugin.version.getLink().replaceAll("http://", ""));
    }
}
