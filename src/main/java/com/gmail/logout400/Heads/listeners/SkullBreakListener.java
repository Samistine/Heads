package com.gmail.logout400.Heads.listeners;

import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.util.SimpleSkull;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
        if (!getCreativeDrop()) {
            return;
        }
        Block block = event.getBlock();
        if (block.getType() == Material.SKULL) {
            World world = block.getWorld();
            String type = SimpleSkull.getSkullBlockOwner(block);
            if (type.equalsIgnoreCase("STEVE")) {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getSkull("PLAYER"));
            } else if (type.equalsIgnoreCase("ZOMBIE")) {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getSkull("ZOMBIE"));
            } else if (type.equalsIgnoreCase("WITHER")) {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getSkull("WITHER"));
            } else if (type.equalsIgnoreCase("CREEPER")) {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getSkull("CREEPER"));
            } else if (type.equalsIgnoreCase("SKELETON")) {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getSkull("SKELETON"));
            } else {
                world.dropItemNaturally(block.getLocation(), SimpleSkull.getNamedSkull(type));
            }
            block.setType(Material.AIR);
        }
    }

    public boolean getCreativeDrop() {
        return config.getBoolean("creative-drop");
    }
}
