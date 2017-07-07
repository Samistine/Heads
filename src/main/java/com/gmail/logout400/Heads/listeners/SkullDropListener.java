package com.gmail.logout400.Heads.listeners;

import com.gmail.logout400.Heads.util.SimpleSkull;
import java.util.Random;
import org.bukkit.SkullType;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkullDropListener implements Listener {

    private final Configuration config;
    private final Random random;

    public SkullDropListener(Configuration config) {
        this.config = config;
        this.random = new Random();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        boolean mobdrop = config.getBoolean("mob-drop");
        boolean playerdrop = config.getBoolean("head-drop");

        EntityType entity = event.getEntityType();

        if (entity == EntityType.PLAYER) {
            if (!playerdrop) return;

            int chance = config.getInt("player-chance");
            Player player = (Player) event.getEntity();
            if (random.nextInt(100) <= chance) {
                event.getDrops().add(SimpleSkull.getNamedSkull(player.getName()));
            }
        } else if (mobdrop) {
            switch (entity) {
                case ZOMBIE: {
                    int chance = config.getInt("zombie-chance");
                    if (random.nextInt(100) <= chance) {
                        event.getDrops().add(SimpleSkull.getSkull(SkullType.ZOMBIE));
                    }
                    break;
                }
                case CREEPER: {
                    int chance = config.getInt("creeper-chance");
                    if (random.nextInt(100) <= chance) {
                        event.getDrops().add(SimpleSkull.getSkull(SkullType.CREEPER));
                    }
                    break;
                }
                case SKELETON: {
                    int chance = config.getInt("skeleton-chance");
                    if (random.nextInt(100) <= chance) {
                        event.getDrops().add(SimpleSkull.getSkull(SkullType.SKELETON));
                    }
                    break;
                }
            }
        }
    }
}
