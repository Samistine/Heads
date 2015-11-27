package com.gmail.logout400.Heads.listeners;

import com.gmail.logout400.Heads.Heads;
import com.gmail.logout400.Heads.SimpleSkull;
import java.util.Random;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkullDropListener implements Listener {

    private final Configuration config;

    public SkullDropListener() {
        this.config = Heads.INSTANCE.getConfig();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType entity = event.getEntityType();
        Random random = new Random();

        boolean mobdrop = config.getBoolean("mob-drop");
        boolean playerdrop = config.getBoolean("head-drop");
        if (entity == EntityType.PLAYER) {
            if (!playerdrop) {
                return;
            }
            int chance = config.getInt("player-chance");
            Player player = (Player) event.getEntity();
            if (random.nextInt(100) <= chance) {
                event.getDrops().add(SimpleSkull.getNamedSkull(player.getName()));
            }
        } else if (entity == EntityType.ZOMBIE) {
            if (!mobdrop) {
                return;
            }
            int chance = config.getInt("zombie-chance");
            if (random.nextInt(100) <= chance) {
                event.getDrops().add(SimpleSkull.getSkull("ZOMBIE"));
            }
        } else if (entity == EntityType.CREEPER) {
            if (!mobdrop) {
                return;
            }
            int chance = config.getInt("creeper-chance");
            if (random.nextInt(100) <= chance) {
                event.getDrops().add(SimpleSkull.getSkull("CREEPER"));
            }
        } else if (entity == EntityType.SKELETON) {
            if (!mobdrop) {
                return;
            }
            int chance = config.getInt("skeleton-chance");
            if (random.nextInt(100) <= chance) {
                event.getDrops().add(SimpleSkull.getSkull("SKELETON"));
            }
        }
    }
}
