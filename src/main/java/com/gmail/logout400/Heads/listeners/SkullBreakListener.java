package com.gmail.logout400.Heads.listeners;

import com.gmail.logout400.Heads.util.SimpleSkull;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SkullBreakListener implements Listener {

    private final Configuration config;

    public SkullBreakListener(Configuration config) {
        this.config = config;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!getCreativeDrop()) return;

        final Block block = event.getBlock();
        if (block.getType() == Material.SKULL) {
            final Skull skull = (Skull) block.getState();

            SkullType type = skull.getSkullType();
            switch (type) {
                case PLAYER:
                    String name = skull.getOwningPlayer().getName();
                    block.getWorld().dropItemNaturally(block.getLocation(), SimpleSkull.getNamedSkull(name));
                    break;
                default:
                    block.getWorld().dropItemNaturally(block.getLocation(), SimpleSkull.getSkull(type));
                    break;
            }

            block.setType(Material.AIR);
        }
    }

    public boolean getCreativeDrop() {
        return config.getBoolean("creative-drop");
    }
}
